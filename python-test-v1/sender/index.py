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

current_config = None
class_names=['!', '(', ')', '+', ',', '-', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '=', '[', ']', 'a', 'alpha', 'ascii_124', 'b', 'beta', 'c', 'cos', 'd', 'delta', 'div', 'e', 'exists', 'f', 'forall', 'forward_slash', 'g', 'gamma', 'geq', 'gt', 'h', 'i', 'in', 'infty', 'int', 'j', 'k', 'l', 'lambda', 'ldots', 'leq', 'lim', 'log', 'lt', 'm', 'mu', 'n', 'neq', 'o', 'p', 'phi', 'pi', 'pm', 'prime', 'q', 'r', 'rightarrow', 's', 'sigma', 'sin', 'sqrt', 'sum', 't', 'tan', 'theta', 'u', 'v', 'w', 'x', 'y', 'z', '{', '}']
model_path = '/app/myvol/models'
data_path='/app/myvol/current'
storage_path='/app/myvol/storage'
lock = threading.Lock()

try:
    client = Client()
except IoFogException as e:
    print(e)
    
def list_file(path):
    files = []
    for file in os.listdir(path):
        temppath=os.path.join(path, file)
        fullpath=os.path.join(storage_path, file)
        shutil.move(temppath, storage_path)
        files.append(fullpath)
    return files
    

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
    
def fun(filepath):

    try:
    
        model = tf.keras.models.load_model(os.path.join(model_path,'model_v3.hdf5'))

        img = cv2.imread(filepath)
        
        img = cv2.resize(img, (45,45), interpolation = cv2.INTER_AREA)
        img = img.reshape(1, 45, 45, 3)

        pred = model.predict(img)
        res = class_names[np.argmax(pred)]
        
        file = open("/app/myvol/res.txt", "a")
        file.write(res)
        file.close()
        
        msg=IoMessage()
        msg.infotype='application/json'
        msg.infoformat='text/utf-8'
        msg.contentdata=json.dumps({"result":res})
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


update_config()
client.establish_message_ws_connection(MessageListener())
client.establish_control_ws_connection(ControlListener())

while True:
    files = list_file(data_path)
    for file in files:
        fun(file)