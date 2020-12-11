import items

on_hand = []
stored = []
lost = []
capacity = 100 #lb

def weight_carrying(): #returns net weight being carried
	carrying = 0
	for i in range(len(on_hand)):
		carrying += float(on_hand[i][2])
	return (carrying)

def weight_capacity(): #returns the maximum capacity for carrying of the character
	return (float(capacity)) #lb

def list_carrying(): #returns an organized table of all the items being carried with a column for name, description, and weight
	print ("Name" + (" " * 16) + "Description" + (" " * 39) + "Weight\n") #uses remaining?
	for item in on_hand:
		print (item[0] + (" " * (20 - len(item[0]))) + item[1] + (" " * (50 - len(item[1]))) + str(item[2]))

def add_item(name): #adds an item to on_hand with arguments (name, description, weight, use) with "use" being either 'static', 'single_use', 'reusable', or '[limited_reusable, int(reuses)] from item_dict in items.py
	li = items.item_dict[name] #items.py referenced here
	on_hand.append(li)
	if weight_carrying() > capacity:
		#over capacity message
		on_hand.remove(li)

def use_item(name): #uses the effects of an item
	for item in on_hand:
			if item[0] == name:
				if item[3] == "Static": #passive
					#cannot use this item in this way message
					pass
				elif item[3] == "Reusable":
					#benefit from effects of item here
					pass
				elif item[3] == ["Limited_Reuses", int(item[3][1])]:
					#benefit frim effects of item here
					item[3][1] -= 1
					if item[3][1] == 0:
						transfer(on_hand, name, lost)
				break

def delete_item(origin, name): #deletes an item from on_hand, lost, or stored 
	for item in origin:
		if item[0] == name:
			origin.remove(item)
			break

def transfer(origin, name, destination): #moves an item beteen stored, lost, and on_hand
	for item in origin:
		if item[0] == name:
			destination.append(item)
			if destination == on_hand and weight_carrying() > capacity:
				#over capacity message
				on_hand.remove(item)
			break