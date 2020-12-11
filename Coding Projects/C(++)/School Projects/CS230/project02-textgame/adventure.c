#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include "player.h"

char *input(int length, int lower) {
	char *buffer = (char *) malloc(length * sizeof(char *));
	if (buffer == NULL) {
		fprintf(stderr, "buffer malloc failed\n");
		exit(EXIT_FAILURE);
	}

	getline(&buffer, &length, stdin);
	buffer[strcspn(buffer, "\n")] = 0;
	if (lower) {
		for(int i = 0; buffer[i]; i++) {
			buffer[i] = tolower(buffer[i]);
		}
	}
	return buffer;
}

void help() {
	printf("Commands:\n-- look\n-- go [DIRECTION]\n-- take [ITEM]\n-- use [ITEM]\n-- drop [ITEM]");
}

int main() {
	Item *flashlight = item("flashlight", "an old, clearly outdated, but still functional *flashlight*", NULL);
	Item *rope = item("rope", "a piece of climbing *rope*", NULL);
	Item *ladder = item("ladder", "a rusty expandable *ladder* (how convenient)", NULL);
	Item *key = item("key", "a reflective bronze *key*, eroded from the passage of time", NULL);
	Item *card = item("card", "the item you've spent this whole game looking for: that one limited-edition trading *card* that's selling for like $2000 online right now. So that's where it's been hiding..", NULL);

	Room *start = room(
		"You find yourself in a dusty old house, which has been rumored to contain some valuable ancient artifact.\n(exactly why something this rare and valuable would be hidden in such a simple location is unclear.)\nNevertheless, you're here with a purpose, and there is but one way forward: a single entrance to the north.",
		NULL, NULL, NULL, NULL, NULL, NULL, NULL
	);

	Room *hall1 = room(
		"You step into a large hallway. Though it expands further north, you cannot explore further as it is too dark to see.\nHowever, you can just barely make out a door to the east.",
		NULL, NULL, NULL, NULL, NULL, NULL, NULL
	);

	Room *lightroom = room(
		"You open the door to discover what appears to be a cozy living room, barely lit by a hearthstone fireplace.\n(oddly out of place, considering the setting.)",
		flashlight, NULL, NULL, NULL, NULL, NULL, NULL
	);

	Room *hall2 = room(
		"At this end of the hallway, you find what appears to be a large, metal door. Again, very out of place, but it's possible that this is what houses your sought-after treasure.\nHowever, the door is locked, as evidenced by its large keyhole and refusal to budge.\nIn addition, there is an entrance to an attic above you which is too high to reach, and another, less grandiose door to the west.",
		NULL, NULL, NULL, NULL, NULL, NULL, NULL
	);

	Room *roperoom = room(
		"You open the door to find some sort of supply closet. Not really much else to it. Maybe something in here could be of use?",
		rope, NULL, NULL, NULL, NULL, NULL, NULL
	);

	Room *basement = room(
		"You make a hard landing onto a concrete floor. The walls and ceiling down here are unfinished and it smells musty. Ugh...",
		ladder, NULL, NULL, NULL, NULL, NULL, NULL
	);

	Room *attic = room(
		"You arrive in a small crawlspace up top. The attic is particularly dusty. From the light seeping through the cracks in the roof, something glints from among a large pile of scrap in front of you.",
		key, NULL, NULL, NULL, NULL, NULL, NULL
	);

	Room *end = room(
		"This is it. The end of your quest is finally at hand.",
		card, NULL, NULL, NULL, NULL, NULL, NULL
	);

	set_north(start, hall1);
	set_east(hall1, lightroom);
	set_west(hall2, roperoom);

	printf("Name: ");
	Player *avatar = player(input(32, 0), NULL, start);

	printf("\n");
	look(avatar);
	printf("\n\n");

	while (!has_item(inventory(avatar), "card")) {
		printf("What will you do, %s?\n- ", player_name(avatar));
		char *command = input(10, 1);
		char *token1 = strtok(command, " "), *token2 = strtok(NULL, " ");
		printf("\n");

		if (token2 == NULL && strncmp(token1, "look", 5) == 0) {
			look(avatar);
		} else if (strncmp(token1, "go", 3) == 0) {
			if (go(avatar, token2))
				look(avatar);
			else
				printf("You can't go that way.");
		} else if (strncmp(token1, "take", 5) == 0) {
			if (!take(avatar, token2))
				printf("You can't take that.");
			else
				printf("Obtained %s.", token2);
		} else if (strncmp(token1, "use", 4) == 0) {
			if (strncmp(token2, "flashlight", 11) == 0) {
				Item *used = use(avatar, hall1, token2);
				if (used != NULL) {
					set_room_description(hall1, "With the illumination of the flashlight, you can see the end of the long hallway towards the north and a trapdoor leading downward.\nThe trapdoor's deep — you'd need some kind of climbing tool to go down there.");
					set_north(hall1, hall2);
				}
			} else if (strncmp(token2, "rope", 5) == 0) {
				Item *used = use(avatar, hall1, token2);
				if (used != NULL) {
					set_down(hall1, basement);
					printf("You can now descend through the trapdoor.");
				}
			} else if (strncmp(token2, "ladder", 7) == 0) {
				Item *used = use(avatar, hall2, token2);
				if (used != NULL) {
					set_up(hall2, attic);
					printf("You can now climb up to the attic.");
				}
			} else if (strncmp(token2, "key", 4) == 0) {
				Item *used = use(avatar, hall2, token2);
				if (used != NULL) {
					set_north(hall2, end);
					printf("The big metal door is now unlocked.");
				}
				free(used);
			}
		} else if (strncmp(token1, "drop", 5) == 0) {
			if (!drop(avatar, token2))
				printf("You don't have that.");
			else
				printf("Dropped %s.", token2);
		} else if (token2 == NULL && strncmp(token1, "help", 5) == 0) {
			help();
		} else {
			printf("What?");
		}
		printf("\n\n");
		free(command);
	}

	free(avatar);

	free(flashlight);
	free(rope);
	free(ladder);
	free(key);
	free(card);

	free(start);
	free(hall1);
	free(lightroom);
	free(hall2);
	free(roperoom);
	free(basement);
	free(attic);
	free(end);

	printf("Congratulations, you're rich!\n");
}