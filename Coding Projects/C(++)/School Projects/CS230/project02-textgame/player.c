#include <stdio.h>
#include <string.h>
#include "player.h"

struct player {
	char *name;
	Item *inventory;
	Room *current_room;
};

void set_player_name(Player *player, char *name) {
	player->name = name;
}

void set_inventory(Player *player, Item *inventory) {
	player->inventory = inventory;
}

void set_room(Player *player, Room *room) {
	player->current_room = room;
}

void add_to_inventory(Player *player, Item *new_item) {
	set_inventory(player, item(item_name(new_item), item_description(new_item), player->inventory));
}

char *player_name(Player *player) {
	return player->name;
}

Item *inventory(Player *player) {
	return player->inventory;
}

Room *current_room(Player *player) {
	return player->current_room;
}

void look(Player *player) {
	Room *room = current_room(player);

	//Print the room description
	printf("%s\n\n", room_description(room));

	//Print the items in the room
	printf(room_items(room) == NULL ? "There are no objects in this room.\n" : "You see ");
	for (Item *node = room_items(room); node != NULL; node = item_next(node)) {
		printf("%s", item_description(node));
		if (item_next(node) == NULL)
			printf(".\n");
		else if (item_next(item_next(node)) == NULL)
			printf(node == room_items(room) ? " and " : ", and ");
		else
			printf(", ");
	}

	//Print the player's inventory
	printf(inventory(player) == NULL ? "You don't have anything on you at the moment." : "You have ");
	for (Item *node = inventory(player); node != NULL; node = item_next(node)) {
		printf("a %s", item_name(node));
		if (item_next(node) == NULL)
			printf(".");
		else if (item_next(item_next(node)) == NULL)
			printf(node == inventory(player) ? " and " : ", and ");
		else
			printf(", ");
	}
}

int go(Player *player, char *direction) {
	if (strncmp(direction, "north", 6) == 0 && north(current_room(player)) != NULL) {
		set_room(player, north(current_room(player)));
		return 1;
	}

	if (strncmp(direction, "south", 6) == 0 && south(current_room(player)) != NULL) {
		set_room(player, south(current_room(player)));
		return 1;
	}

	if (strncmp(direction, "east", 5) == 0 && east(current_room(player)) != NULL) {
		set_room(player, east(current_room(player)));
		return 1;
	}

	if (strncmp(direction, "west", 5) == 0 && west(current_room(player)) != NULL) {
		set_room(player, west(current_room(player)));
		return 1;
	}

	if (strncmp(direction, "up", 3) == 0 && up(current_room(player)) != NULL) {
		set_room(player, up(current_room(player)));
		return 1;
	}

	if (strncmp(direction, "down", 5) == 0 && down(current_room(player)) != NULL) {
		set_room(player, down(current_room(player)));
		return 1;
	}

	return 0;
}

int take(Player *player, char *item_name) {
	Item *items = room_items(current_room(player));
	Item *new_item = item_take(&items, item_name);
	if (new_item != NULL) {
		set_room_items(current_room(player), items);
		add_to_inventory(player, new_item);
		return 1;
	}
	return 0;
}

Item *use(Player *player, Room *desired_room, char *item_name) {
	return current_room(player) == desired_room ? item_take(&(player->inventory), item_name) : NULL;
}

int drop(Player *player, char *item_name) {
	Item *dropped_item = use(player, current_room(player), item_name);
	if (dropped_item != NULL) {
		add_to_room(current_room(player), dropped_item);
		return 1;
	}
	return 0;
}

Player *player(char *name, Item *inventory, Room *room) {
	Player *out = (Player *) malloc(sizeof(Player));
	if (out == NULL) {
		fprintf(stderr, "player malloc failed\n");
		exit(EXIT_FAILURE);
	}

	set_player_name(out, name);
	set_inventory(out, inventory);
	set_room(out, room);
	return out;
}