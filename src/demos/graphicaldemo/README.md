# Graphical Demo

This folder contains a graphical demonstration of the Inventory component in the form of an RPG menu screen.

# Instructions

- Move the cursor within or between inventories with the arrow keys.
- When the 'V' key is pressed, the current inventory slot will be selected.
- After pressing 'V' there is a saved cursor position and:
    - Pressing the 'C' key will unselect the saved position.
    - Pressing the 'V' key again will attempt to swap items between the current and saved position.
    - Pressing the 'X' key over an empty slot will display a prompt to select the number of items to split.
    Using the up/down keys will increase/decrease the number to split. Pressing any key will then send the
    selected number of items from the saved position to the current position.
- Pressing the 'Z' key will attempt to 'auto-equip' / 'auto-dequip' by sending the item in the current cursor
position to a different inventory, based on where it is allowed and where there is room.