#include "rooms.h"

typedef struct player Player;

//Setters
void set_player_name(Player *player, char *name);

void set_inventory(Player *player, Item *inventory);

void set_room(Player *player, Room *room);

void add_to_inventory(Player *player, Item *new_item);

//Getters
char *player_name(Player *player);

Item *inventory(Player *player);

Room *current_room(Player *player);


//                                             -- Command Functions --

//Prints the room description, room items, and inventory pertaining to the player
void look(Player *player);

//Moves the player in the desired direction, if there exists an exit in that direction
// -- returns success/failure signal (if successful, then 1, else 0)
int go(Player *player, char *direction);

//Places an item from the room the player is currently in, if the item exists, into the player's inventory
// -- returns success/failure signal (if successful, then 1, else 0)
int take(Player *player, char *item_name);

//Discards the given item from the player's inventory, if the item exists and is used in the correct room
// -- returns the discarded item, if it exists
Item *use(Player *player, Room *desired_room, char *item_name);

//Places an item from the player's inventory, if the item exists, into the room the player is currently in
// -- returns success/failure signal (if successful, then 1, else 0)
// -- inverse of take()
int drop(Player *player, char *item_name);

//Constructor
Player *player(char *name, Item *inventory, Room *room);