import socket
import threading
import time

HOST = 'ec2-18-222-122-64.us-east-2.compute.amazonaws.com'  # The server's hostname or IP address
PORT = 8000             # The port used by the server


# Extremely basic send-recieve client
with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
    def getMessage(task=None):
        # Serve forever
        while True:
            # Empty bytes buffer
            data = b''
            # Get many messages
            while True:
                # time.sleep(1)
                # Keep reading in from buffer until buffer is empty.
                buffer = s.recv(1024)
                if len(buffer) == 0:
                    break
                else:
                    data += buffer
                    if(len(buffer) != 1024):
                        break
            msg = ""
            # Attempt to convert bytestream to string
            try:
                msg = data.decode()
            except Exception as e:
                if task != None:
                    print("Error decoding input during", task)
                else:
                    print("Error decoding input")
                print(e)
            # In case connection was terminated
            if len(msg) == 0:
                quit(0)
            # Print the message
            print(msg)

    # Connect to server
    s.connect((HOST, PORT))
    
    # Start receiver thread so this doesn't end up like the last online quiz...
    listener = threading.Thread(target=getMessage)
    listener.daemon = True
    listener.start()

    # Capture user input on main thread
    while True:
        s.sendall(input().encode())