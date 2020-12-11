import inventory

#demonstration below this point
print (inventory.weight_carrying())
inventory.add_item("Matchbox")
inventory.add_item("Candle")
print (inventory.weight_carrying())
inventory.list_carrying()
inventory.delete_item(inventory.on_hand, "Candle")
print (inventory.weight_carrying())