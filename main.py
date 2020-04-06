from gtts import gTTS
from pygame import mixer

import os
import msvcrt
import time

from first import data

    




if __name__ == "__main__":
	i=0       

	while (True):
		mixer.init()
		
		if msvcrt.kbhit():
		 	data=msvcrt.getch().decode('utf-8')
		 	if data=='\r':
		 		break
		if('hello.mp3' in os.listdir()):
			mixer.music.load('hello.mp3')
			mixer.music.play()
			while(mixer.music.get_busy()):
				pass
			mixer.music.load("a.mp3")
			os.remove('hello.mp3')

		
		#
		if (i==0):
			tts=gTTS(u'यो गारो छ जस्तो छ','ne', slow=False)
			i=1
			tts.save('hello.mp3')
		




