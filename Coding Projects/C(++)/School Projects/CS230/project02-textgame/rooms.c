#include <stdio.h>
#include "rooms.h"

struct room {
	char *description;
	Item *items;
	Room *north;
	Room *south;
	Room *east;
	Room *west;
	Room *up;
	Room *down;
};

void set_room_description(Room *room, char *description) {
	room->description = description;
}

void set_room_items(Room *room, Item *items) {
	room->items = items;
}

void add_to_room(Room *room, Item *new_item) {
	set_room_items(room, item(item_name(new_item), item_description(new_item), room->items));
}

void set_north(Room *room, Room *north) {
	room->north = north;
	if (north != NULL)
		north->south = room;
}

void set_south(Room *room, Room *south) {
	room->south = south;
	if (south != NULL)
		south->north = room;
}

void set_east(Room *room, Room *east) {
	room->east = east;
	if (east != NULL)
		east->west = room;
}

void set_west(Room *room, Room *west) {
	room->west = west;
	if (west != NULL)
		west->east = room;
}

void set_up(Room *room, Room *up) {
	room->up = up;
	if (up != NULL)
		up->down = room;
}

void set_down(Room *room, Room *down) {
	room->down = down;
	if (down != NULL)
		down->up = room;
}

char *room_description(Room *room) {
	return room->description;
}

Item *room_items(Room *room) {
	return room->items;
}

Room *north(Room *room) {
	return room->north;
}

Room *south(Room *room) {
	return room->south;
}

Room *east(Room *room) {
	return room->east;
}

Room *west(Room *room) {
	return room->west;
}

Room *up(Room *room) {
	return room->up;
}

Room *down(Room *room) {
	return room->down;
}

Room *room(char *description, Item* items, Room *north, Room *south, Room *east,
											Room *west, Room *up, Room *down) {
	Room *out = (Room *) malloc(sizeof(Room));
	if (out == NULL) {
		fprintf(stderr, "room malloc failed\n");
		exit(EXIT_FAILURE);
	}

	set_room_description(out, description);
	set_room_items(out, items);
	set_north(out, north);
	set_south(out, south);
	set_east(out, east);
	set_west(out, west);
	set_up(out, up);
	set_down(out, down);
	return out;
}