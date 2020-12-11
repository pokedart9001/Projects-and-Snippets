#include <stdlib.h>

typedef struct item Item;

//Setters
void set_item_name(Item *item, char *name);

void set_item_description(Item *item, char *description);

void set_next(Item *item, Item *next);

//Getters
char *item_name(Item *item);

char *item_description(Item *item);

Item *item_next(Item *item);

//Returns and removes an item from the given list, or returns NULL if the item doesn't exist
Item *item_take(Item **head, char *name);

//States whether or not the given list contains a certain item
int has_item(Item *head, char *name);

//Constructor
Item *item(char *name, char *description, Item *next);