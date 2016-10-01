import os 
from PIL import Image

#run this script in the folder where there images are actually stored

os.system = ('mkdir imgfolder')
size = input(int())
imgz = os.listdir('.') #"." since it will access the folder and its files within itself
for i in imgz :
	im = Image.open(i)
	new_img = im.resize((size,size))
    new_img.save('imgfolder/'+str(i),'png')