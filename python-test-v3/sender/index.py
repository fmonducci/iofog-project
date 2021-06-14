from iofog.microservices.client import Client
from iofog.microservices.exception import IoFogException
from iofog.microservices.iomessage import IoMessage
from iofog.microservices.listener import *

import json
import numpy as np
import tensorflow as tf
import cv2
import os
import threading
import shutil
from datetime import datetime
import requests
import base64
import time

current_config = None
lock = threading.Lock()

interpreter = None
input_details = None
output_details = None

model_path = os.environ['MODELS_PATH']
data_path = os.environ['DATA_PATH']
storage_path = os.environ['STORAGE_PATH']
model_storage_path = os.environ['MODELS_STORAGE_PATH']
url = os.environ['MANAGER_STORE']
labels_path = os.environ['LABELS_PATH']

file_count = 0



try:
    client = Client()
except IoFogException as e:
    print(e)
    
with open(labels_path) as f:
    labels = f.read().splitlines()
    
#lists files in path
def list_file(path):
    files = []
    for file in os.listdir(path):
        current_path=os.path.join(path, file)
        files.append(current_path)
    return files
    
#get latest file according to modification time
def get_last(models):
    m_time = os.stat(models[0]).st_mtime
    newer = models[0]
    for model in models:
        file_time = os.stat(model).st_mtime
        if m_time is None or m_time<file_time:
            m_time = file_time
            newer = model
    return newer

#moves and renames file
def move(current, storage):
    file_name, file_extension = os.path.splitext(current)
    lock.acquire()
    global file_count
    dest = os.path.join(storage,str(file_count)+file_extension)
    file_count = file_count + 1
    lock.release()
    shutil.move(current, dest)

def update_config():
    attempt_limit = 5
    config = None

    while attempt_limit > 0:
        try:
            config = client.get_config()
            break
        except IoFogException as ex:
            attempt_limit -= 1
            print(ex)
        
    if attempt_limit == 0:
        print('Config update failed :(')
        return

    lock.acquire()
    global current_config
    current_config = config
    lock.release()
    
def update_model():
    try:
        lock.acquire()
        global interpreter
        global input_details
        global output_details
        models = list_file(model_path)
        
        if models:
            
            model_name = get_last(models)
            # Load the TFLite model and allocate tensors.
            interpreter = tf.lite.Interpreter(model_path=model_name)
            interpreter.allocate_tensors()

            # Get input and output tensors.
            input_details = interpreter.get_input_details()
            output_details = interpreter.get_output_details()
              
            file = open("/app/myvol/model_log.txt", "a")
            now = datetime.now()
            file.write("{}, using: {}\n".format(now.strftime("%d/%m/%Y %H:%M:%S"), model_name))
            file.close()
                
        lock.release()
        
        for m in models:
            move(m,model_storage_path)
        
    except IOError as e:
        print(e)
    
def get_base64_encoded_image(image_path):
    with open(image_path, "rb") as img_file:
        return base64.b64encode(img_file.read()).decode('utf-8')

def predict(file_path):

    try:
        
        if interpreter is not None:
            
            #image load and prediction
            img = cv2.imread(file_path)
            jstr = get_base64_encoded_image(file_path)
            
            
            
            img = cv2.resize(img,dsize=(224,224), interpolation = cv2.INTER_CUBIC)
            input_data = tf.expand_dims(img, 0)
            interpreter.set_tensor(input_details[0]['index'], input_data)
            interpreter.invoke()
            output_data = interpreter.get_tensor(output_details[0]['index'])
            res = labels[np.argmax(output_data)]
            percentages = output_data/output_data.sum()*100
            
            
            
            #moves image to storage
            move(file_path, storage_path)
            
            data = json.dumps({'agent':os.environ['AGENT_NAME'],'image':jstr,'prediction':res,'accuracy':"{:.2f}".format(round(np.max(percentages), 2))+"%"})
            
            #prepare ioMesagge and send result
            msg=IoMessage()
            msg.infotype='application/json'
            msg.infoformat='text/utf-8'
            msg.contentdata=data
            msg.contextdata=""

            client.post_message_via_socket(msg)
            
            
            
            x = requests.post(url, json = data)
            
        
    except IoFogException as e:
        print(e)




class ControlListener(IoFogControlWsListener):
    def on_control_signal(self):
        update_config()


class MessageListener(IoFogMessageWsListener):
    def on_receipt(self, message_id, timestamp):
        print ('Receipt: {} {}'.format(message_id, timestamp))
        
    def on_message(self, msg):
        print ('Received message')
        update_model()

#setup
update_config()
update_model()
client.establish_message_ws_connection(MessageListener())
client.establish_control_ws_connection(ControlListener())


#lists file in path
#if any, calls predict
while True:
    time.sleep(3)
    files = list_file(data_path)
    
    total_start = time.time()
    for file in files:
        predict(file)
    
    
    
    