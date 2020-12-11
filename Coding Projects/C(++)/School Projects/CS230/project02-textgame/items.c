#include <stdio.h>
#include <string.h>
#include "items.h"

struct item {
	char *name;
	char *description;
	Item *next;
};

void set_item_name(Item *item, char *name) {
	item->name = name;
}

void set_item_description(Item *item, char *description) {
	item->description = description;
}

void set_next(Item *item, Item *next) {
	item->next = next;
}

char *item_name(Item *item) {
	return item->name;
}

char *item_description(Item *item) {
	return item->description;
}

Item *item_next(Item *item) {
	return item->next;
}

Item *item_take(Item **head, char *name) {
	Item *node, *prev;
	for (
		node = *head, prev = NULL;
		node != NULL && strcmp(item_name(node), name) != 0;
		prev = node, node = item_next(node)
	);

	if (node != NULL) {
		if (prev == NULL)
			*head = item_next(node);
		else
			set_next(prev, item_next(node));
		set_next(node, NULL);
	}
	
	return node;
}

int has_item(Item *head, char *name) {
	return head != NULL && (strcmp(item_name(head), name) == 0 || has_item(item_next(head), name));
}

Item *item(char *name, char *description, Item *next) {
	Item *out = (Item *) malloc(sizeof(Item));
	if (out == NULL) {
		fprintf(stderr, "item malloc failed\n");
		exit(EXIT_FAILURE);
	}

	set_item_name(out, name);
	set_item_description(out, description);
	set_next(out, next);

	return out;
}