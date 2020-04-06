import serial


class FaceFinder(object):

    def __init__(self):
        # Set up the serial connection to interface with the Arduino.
        self.arduino = serial.Serial('COM26', 9600,timeout=3)
        

    def signal_arduino(self, command):    
        self.arduino.write(command)
        print(command)

    def loop(self):
        pass
    def endStream(self):
        self.arduino.close()

if __name__ == "__main__":
    f = FaceFinder()
    
    f.signal_arduino("Hi this is it")
    f.endStream()
