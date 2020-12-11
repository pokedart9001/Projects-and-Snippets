#include "items.h"

typedef struct room Room;

//Setters
void set_room_description(Room *room, char *description);

void set_room_items(Room *room, Item *items);

void add_to_room(Room *room, Item *new_item);

void set_north(Room *room, Room *north);

void set_south(Room *room, Room *south);

void set_east(Room *room, Room *east);

void set_west(Room *room, Room *west);

void set_up(Room *room, Room *up);

void set_down(Room *room, Room *down);

//Getters
char *room_description(Room *room);

Item *room_items(Room *room);

Room *north(Room *room);

Room *south(Room *room);

Room *east(Room *room);

Room *west(Room *room);

Room *up(Room *room);

Room *down(Room *room);

//Constructor
Room *room(char *description, Item* items, Room *north, Room *south, Room *east,
											Room *west, Room *up, Room *down);