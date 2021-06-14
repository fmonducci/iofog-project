/*
 * *******************************************************************************
 *   Copyright (c) 2018 Edgeworx, Inc.
 *
 *   This program and the accompanying materials are made available under the
 *   terms of the Eclipse Public License v. 2.0 which is available at
 *   http://www.eclipse.org/legal/epl-2.0
 *
 *   SPDX-License-Identifier: EPL-2.0
 * *******************************************************************************
 */

const http = require('http')
const ioFogClient = require('@iofog/nodejs-sdk')

const PORT = 80


let lastMessage = {}

ioFogClient.init('iofog', 54321, null, () => {
  ioFogClient.wsControlConnection(
    {
      'onNewConfigSignal': () => { },
      'onError': (error) => {
        console.error('There was an error with Control WebSocket connection to ioFog: ', error)
      },
    }
  )

  ioFogClient.wsMessageConnection(() => { }, {
    'onMessages': (messages) => {
      if (messages && messages.length) {
        lastMessage = messages[0]
      }
    },
    'onMessageReceipt': (messageId, timestamp) => { },
    'onError': (error) => {
      console.error('There was an error with Message WebSocket connection to ioFog: ', error)
    }
  })
})

function preparePage(data){
	
	
	
	var out="<table class=\"center\">";
	
	
	
	out+="<tr><td>Agent</td><td>"+data["agent"]+"</td></tr>";
	out+="<tr><td>Image</td><td><img src=\'" + 'data:image/jpg;base64,' + data["image"] + "\' width=\"33%\"/></td></tr>";
	out+="<tr><td>Prediction</td><td>"+data["prediction"]+" ("+data["accuracy"]+")</td></tr>";
	out+="</table>"
	
	return out;
}


var server = http.createServer(
    function handleRequest(request, response) {
		
		
		
		if (lastMessage && lastMessage.contentdata) {
			var base64 = lastMessage.contentdata;
			data = (new Buffer(base64, 'base64')).toString('utf8')
			data=JSON.parse(data)
			var htmlOut = "<html><head><title>Predictions</title></head><body>" + preparePage(data) + "</body></html>";
			response.writeHead(200, {'Content-Type':'text/html'});
			response.end(htmlOut);
		}
		else{
			var htmlOut = "<html><head><title>Predictions</title></head><body>No content yet!</body></html>";
			response.writeHead(200, {'Content-Type':'text/html'});
			response.end(htmlOut);
		}
		
		
        
		
		
    }
);

server.listen(PORT, () => { })