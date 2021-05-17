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


current_config = None
lock = threading.Lock()

model = None
class_names=['!', '(', ')', '+', ',', '-', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '=', '[', ']', 'a', 'alpha', 'ascii_124', 'b', 'beta', 'c', 'cos', 'd', 'delta', 'div', 'e', 'exists', 'f', 'forall', 'forward_slash', 'g', 'gamma', 'geq', 'gt', 'h', 'i', 'in', 'infty', 'int', 'j', 'k', 'l', 'lambda', 'ldots', 'leq', 'lim', 'log', 'lt', 'm', 'mu', 'n', 'neq', 'o', 'p', 'phi', 'pi', 'pm', 'prime', 'q', 'r', 'rightarrow', 's', 'sigma', 'sin', 'sqrt', 'sum', 't', 'tan', 'theta', 'u', 'v', 'w', 'x', 'y', 'z', '{', '}']

model_path = '/app/myvol/models'
data_path='/app/myvol/current'
storage_path='/app/myvol/storage'
model_storage_path='/app/myvol/storage/models'

file_count = 0



try:
    client = Client()
except IoFogException as e:
    print(e)
    
#lists files in path
def list_file(path):
    files = []
    for file in os.listdir(path):
        current_path=os.path.join(path, file)
        files.append(current_path)
    return files
    
#get latest file according to modification time
def get_last(models):
    time = os.stat(models[0]).st_mtime
    newer = models[0]
    for model in models:
        file_time = os.stat(model).st_mtime
        if time is None or time<file_time:
            time = file_time
            newer = model
    return newer

#moves and renames file
def move(current, storage):
    file_name, file_extension = os.path.splitext(current)
    lock.acquire()
    global file_count
    dest = os.path.join(storage,str(file_count)+file_extension)
    print(dest)
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
        global model
        models = list_file(model_path)
        
        if models:
            
            model_name = get_last(models)
            model = tf.keras.models.load_model(model_name)
              
            file = open("/app/myvol/model_log.txt", "a")
            now = datetime.now()
            file.write("{}, using: {}\n".format(now.strftime("%d/%m/%Y %H:%M:%S"), model_name))
            file.close()
                
        lock.release()
        
        for m in models:
            move(m,model_storage_path)
        
    except IOError as e:
        print(e)
    
def predict(file_path):

    try:
        
        if model is not None:

            #image load and prediction
            img = cv2.imread(file_path)
            
            img = cv2.resize(img, (45,45), interpolation = cv2.INTER_AREA)
            img = img.reshape(1, 45, 45, 3)

            pred = model.predict(img)
            res = class_names[np.argmax(pred)]
            
            #moves image to storage
            move(file_path, storage_path)
            
            #append result to result file
            file = open("/app/myvol/res.txt", "a")
            file.write('{}\n'.format(res))
            file.close()
            
            #prepare ioMesagge and send result
            msg=IoMessage()
            msg.infotype='application/json'
            msg.infoformat='text/utf-8'
            msg.contentdata=json.dumps({"res":res})
            msg.contextdata=""

            client.post_message_via_socket(msg)
        
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


update_config()
update_model()
client.establish_message_ws_connection(MessageListener())
client.establish_control_ws_connection(ControlListener())

#lists file in path
#if any, calls predict
while True:
    files = list_file(data_path)
    for file in files:
        predict(file)