"""""""""""""""""""""
!!!!!!!!!!!!!!!!!!!!!!
!!!PROOF OF CONCEPT!!!
!!!!!!!!!!!!!!!!!!!!!!
"""""""""""""""""""""

import time
import os

#defining local time for console output
def clock ():
	return str(time.asctime(time.localtime(time.time())))

#number of times program has been run is checked
event_count = open('times_run.cfg', 'r')
run_event = list(tuple(event_count))[0]

########
#STARTUP
########


print ("Starting Tool...\n-------------------------\n-------------------------")

#print program Version in debug log
print (str(clock()) + str(" || Version: " + str(''.join(map(str, (list(str((list(open('version.cfg').readlines())[4])))[::-1][:5][::-1]))))))
time.sleep (0.06)

#print the number of times program has been run to debug log
print (str(clock()) + str(" || Console: Updating Runtime Logs..."))
print (str(clock()) + str(" || Console: Run Event: " + str(run_event)))
open('times_run.cfg', 'w+').write(str(int(''.join(map(str, run_event))) + 1))
time.sleep (0.08)

#code to make a logfile goes here
print (str(clock()) + str(" || Console: " + "Generating Logfile..."))
time.sleep (0.46)
print (str(clock()) + str(" || Console: " + "Now running..."))
time.sleep (2)
os.system('clear')

#########
#\STARTUP
#########

class character:
  
  def __init__(self):
    pass
  
  #print all members of party
  def party_list(self):
    with open('characters.cfg') as f:
      party_size = str(sum(1 for _ in f))
    print ("The party has " + party_size + " adventurers")
    with open('characters.cfg', 'r') as f:
      print (f.read())
  
  #PSUDOCODE
  def check_status(character):
    with open('character.cfg') as f:
      print ("character hp, xp, level, stats, concious/unconcious, inventory, weight, etc")
  #/PSUDOCODE
  
  class create:
    
    #initialize class with the following traits. User picks these when class is called.
    def __init__(self, name, hair, eyes, age):
      self.name = name
      self.hair = hair
      self.eyes = eyes
      self.age = age
    
    def fighter(self, subclass):
      #create and store character in a config file (proof of concept)
      createornotcreate = input(str("Create a " + str(self.age) + " year old " + str(subclass) + " fighter named " + str(self.name) + " with " + str(self.eyes) + " eyes and " + str(self.hair) + " hair?" + " Enter 'y' or 'n'"))
      if createornotcreate == "y":
        charlist = open('characters.cfg', 'w+').write(str(self.age) + " year old " + str(subclass) + " fighter named " + str(self.name) + " with " + str(self.eyes) + " eyes and " + str(self.hair) + " hair")
        print ("Character has been created.")
      else: print ("OK")
  
  class combat:
    
    def __init__(self, turn):
      self.turn = turn #keeping track of turns for effects that rely on xyz happens next turn
    
    def character_damage(self, quant, target):
      pass #open character.cfg, subtract damage, if hp <= 0, combat.unconcious
    
    def enemy_damage(self, quant, target):
      pass #handle on the fly, define enemy variables at start of combat using enemy.cfg as a monster compendium, subtract damage
    
    def character_heal(self, quant, target):
      pass #open character.cfg, add hp, if hp > maxhp, hp = maxhp
    
    def enemy_heal(self, quant, target):
      pass #heal enemy hp
    
    def character_effect(self, effect, target):
      pass
      
    def enemy_effect(self, effect, target):
      pass
      
    def character_unconcious(self, target):
      pass
      
    def enemy_unconcious(self, target):
      pass
      
    #character action types defined in character.cfg
    
#proof of concept character creation
character.create("Lauren Ipsum", "Brown", "Brown", 34).fighter("Great Weapons Master")

#SAMPLE COMBAT
print ("As character(s) move through the [Randomly generated text], suddenly a Blob and an Enemy Name [Random text based on random text 1]")
print ("ROOOOOOOOLLLLL FOR INITIATIVE!!!!") #Or something like that.
#determine turn order
in_combat = True
turn = 1
participants = [("Enemy Name", "hp", "xp", "other stuff", "loot", "etc"), ("Blob", 15, 20, "xyz")]
while in_combat:
  #all participants take actions (movement, battle map/grid ui????)
  #enemy_damage("Blob", 5)
  #print ("Blob takes 5 damage. It looks slightly hurt as it [RANDOM TEXT]")
  continue