from iofog.microservices.client import Client
from iofog.microservices.exception import IoFogException
from iofog.microservices.iomessage import IoMessage
from iofog.microservices.listener import *

import threading
import os
import shutil
from apscheduler.schedulers.blocking import BlockingScheduler

lock = threading.Lock()
current_config = None
path = os.environ['MODELS_PATH']


try:
    client = Client()
except IoFogException as e:
    print(e)



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
    
def send_update():

    try:
        msg=IoMessage()
        msg.infotype='application/json'
        msg.infoformat='text/utf-8'
        msg.contentdata='update model'
        msg.contextdata=""

        client.post_message_via_socket(msg)
        
        print("Update sent")
        
    except IoFogException as ex:
        print(ex)
        
def check_file():
    onlyfiles = [f for f in os.listdir(path) if os.path.isfile(os.path.join(path, f))]
    if onlyfiles:
        send_update()

class ControlListener(IoFogControlWsListener):
    def on_control_signal(self):
        update_config()


class MessageListener(IoFogMessageWsListener):
    def on_receipt(self, message_id, timestamp):
        print ('Receipt: {} {}'.format(message_id, timestamp))


update_config()
client.establish_message_ws_connection(MessageListener())
client.establish_control_ws_connection(ControlListener())

scheduler = BlockingScheduler()
scheduler.add_job(check_file, 'interval', seconds=30)
scheduler.start()
