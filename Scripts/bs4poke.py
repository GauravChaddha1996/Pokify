import os
import json
import urllib
from bs4 import BeautifulSoup
from pprint import pprint

with open('catchemall.json') as data_file:    
    data = json.load(data_file)
bu = "http://www.pokemon.com/us/pokedex/"
desc = ""
html_data = """ """
for i in range (0,150):
	lol = (data['results'][i]['name'])
	url = bu+lol
	r = urllib.urlopen(url)
	html_data = os.system('wget '+url)
	soup = BeautifulSoup(r,"lxml")
	desc= soup.find("meta",{"name" : "description"})['content']
	print desc
