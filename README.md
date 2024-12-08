# Inventory

The purpose of this component is to model a collection of items in an inventory. 
Inventories are fixed size and hold Item objects. Items have a name and a set of tags 
(modeled as String, Integer pairs), which always includes their count. Inventories may 
be restricted so that only Items with all of a list of tags are permitted.
