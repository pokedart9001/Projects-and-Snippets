import discord
#import asyncio
import logging

import time
#import sys

#logging
logging.basicConfig(level=logging.INFO)

#############################
#GLOBAL VARIABLES START HERE#
#############################

client = discord.Client()

servers = client.servers

strike_dict = {"@superganondorfguy" : 1, "@joe" : 3}
role_dict = {"@superganondorfguy" : ["HEAD ADMIN", "ADMIN", "MODERATOR"], "@admin" : "ADMIN", "@sysadmin" : "ADMIN", "@netadmin" : ["ADMIN", "MODERATOR"], "@joe" : "USER", "@pi" : "MODERATOR"}
hist_li = ["User: '@admin' posted Message: 'Am bored. Send help'"]

###########################
#GLOBAL VARIABLES END HERE#
###########################

#class Censor_Bot():
def get_message(message):
	return message

def get_user(user):
	return user

def get_role(user):
  if user in role_dict:
    if role_dict[user] == str(role_dict[user]):
      roles_ = role_dict[user]
    else:
      roles_ = ', '.join(map(str, role_dict[user]))
  else: roles_ = "ERROR 01"
  return roles_

def strike_count(user):
	if user in strike_dict:
		return strike_dict[user]
	else:
		return 0

def censor():
	
	message = get_message(discord.message)
	user = get_user(discord.user)
	
	censor_list = ["censored word"]
	
	for i in censor_list:
		if i.lower() in message.lower():
			print (str(time.asctime(time.localtime(time.time()))) + "|| Message: '" + str(message) + "' posted by User: '" + str(user) + "' deleted")
			end_state = 1
			mot = i
			break
		if i not in message:
			end_state = 0
	if end_state == 0:
		print ("Message does not contain profanity")
	if end_state == 1:
		reason = "Triggered censor ('" + mot + "')"
		if user in strike_dict:
			print (str(time.asctime(time.localtime(time.time()))) + "|| User: '" + user + "' awarded 1 strike for Reason: " + reason)
			strike_dict.update({user : int(int(strike_dict[user]) + 1)})
			print (str(time.asctime(time.localtime(time.time()))) + "|| User: '" + user + "' now has " + str(strike_count(user)) + " strikes")
			if strike_count(user) == 5:
				reason = "Achieved 5 total strikes"
				print (str(time.asctime(time.localtime(time.time()))) + "|| User: '" + user + "' has been kicked for Reason: " + reason)
		else:
			print (str(time.asctime(time.localtime(time.time()))) + "|| User: '" + user + "' awarded 1 strike for Reason: " + reason)
			strike_dict.update({user : 1})
			print (str(time.asctime(time.localtime(time.time()))) + "|| User: '" + user + "' now has 1 strike")
    
def privelage_check (user):
  if get_role(user) == "ERROR 01": return "ERROR 01: NO SUCH USER"
  if "HEAD ADMIN".lower() in str(get_role(get_user(user))).lower() or "ADMIN".lower() in str(get_role(get_user(user))).lower():
    return "TOP PRIVELAGE"
  elif "MODERATOR".lower() in str(get_role(get_user(user))).lower():
    return "ESCALATED PRIVELAGE"
  else: return "NO PRIVELAGE"


def append_history(usr, msg):
  hist_li.append ("User: '" + str(usr) + "' posted Message: '" + str(msg) + "'")

def print_history():
  for i in hist_li:
    print (i)