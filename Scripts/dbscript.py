import subprocess
import os
import json
import requesocks as requests

session = requests.session()
session.proxies = {'http': 'socks5://127.0.0.1:1080',
                   'https': 'socks5://127.0.0.1:1080'}

bs = "http://pokeapi.co/api/v2/pokemon/"

nf = open("name.txt", "w")
hf = open("height.txt", "w")
wf = open("weight.txt", "w")
bef = open("base_experience.txt", "w")
tf = open("type.txt", "w")


for i in range(1, 151):
	print i
	resp = session.get(bs + str(i) + "/")
	js = resp.text
	js = json.loads(js)
	json.dump(js, open(str(i) + '.json', 'w'))
	nf.write(str("<item>" + str(js["name"]) + "</item>\n"))
	hf.write(str("<item>" + str(js["height"]) + "</item>\n"))
	wf.write(str("<item>" + str(js["weight"]) + "</item>\n"))
	bef.write(str("<item>" + str(js["base_experience"]) + "</item>\n"))
	tf.write(str("<item>" + str(js["types"][0]["type"]["name"]) + "</item>\n"))