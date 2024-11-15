package demos.graphicaldemo;

import java.util.ArrayList;

import inventory.Inventory;
import inventory.Inventory.Item;
import inventory.Inventory1;
import inventory.InventorySecondary.BasicItem;

/** */
public final class DemoModel {

    //Constants

    /** Size of the general inventory. */
    private static final int INV_SIZE = 11;

    /** Size of the consumable-specific inventory. */
    private static final int USE_SIZE = 3;

    /** The indices of each sub-inventory. */
    public enum InvIndices {
        GENERAL, USABLE, HANDS, ARMOR, RELICS
    };

    //Inventories

    /** The general inventory. */
    private Inventory inv;

    /** The consumable-specific inventory. */
    private Inventory usable;

    /** The equipment inventories. */
    private Inventory hands, armor, relics;

    /** The list of inventories. */
    private ArrayList<Inventory> invs;

    //Indices

    /** Which inventory is currently selected. */
    private int currentInv;

    /** Which inventory was selected when the cursor's position was saved. */
    private int savedInv;

    /** Stored positions in the current or past inventory. */
    private int cursorPos, savedPos;

    /** Creates a new DemoModel. */
    public DemoModel() {

        this.inv = new Inventory1(INV_SIZE);
        this.usable = new Inventory1(USE_SIZE);
        this.hands = new Inventory1(2);
        this.armor = new Inventory1(1);
        this.relics = new Inventory1(2);

        //TODO: add tag restrictions to non-general inventories
        //TODO: add proper items

        for (int i = 0; i < this.inv.size(); i++) {
            this.inv.addItem(i, new BasicItem("FOO"));
        }

        for (int i = 0; i < this.usable.size(); i++) {
            this.usable.addItem(i, new BasicItem("BAR"));
        }

        for (int i = 0; i < this.hands.size(); i++) {
            this.hands.addItem(i, new BasicItem("FIZZ"));
        }

        this.armor.addItem(0, new BasicItem("BUZZ"));

        for (int i = 0; i < this.relics.size(); i++) {
            this.relics.addItem(i, new BasicItem("GNOME"));
        }

        this.invs = new ArrayList<>();
        this.invs.add(this.inv);
        this.invs.add(this.usable);
        this.invs.add(this.hands);
        this.invs.add(this.armor);
        this.invs.add(this.relics);

        this.currentInv = 0;
        this.savedInv = 0;
        this.cursorPos = 0;
        this.savedPos = -1;
    }

    /**
     * Swaps the item at the cursor position in the current inventory with the
     * item at the saved position in the saved inventory.
     */
    private void moveItem() throws ItemRestrictionException {

        Inventory dest = this.invs.get(this.currentInv);
        Inventory src = this.invs.get(this.savedInv);

        if (!dest.isAllowed(src.getItem(this.savedPos))) {
            throw new ItemRestrictionException();
        }

        String destName = dest.getItem(this.cursorPos).getName();
        String srcName = src.getItem(this.savedPos).getName();

        if (destName.equals(srcName)) {
            if (dest == src) {
                dest.addItem(this.cursorPos, dest.removeItem(this.savedPos));
            } else {
                dest.transferItem(src, this.savedPos, this.cursorPos);
            }

        } else {

            if (dest == src) {
                dest.swapItems(this.savedPos, this.cursorPos);
            } else {
                dest.swapItems(src, this.savedPos, this.cursorPos);
            }
        }
    }

    /**
     * Moves the cursor up or down by 1.
     *
     * @param isDown
     *            whether to move the cursor down (true) or up (false).
     */
    public void moveCursor(boolean isDown) {

        if (isDown) {
            this.cursorPos++;
        } else {
            this.cursorPos--;
        }

        if (this.cursorPos < 0) {
            this.cursorPos++;
        }

        if (this.cursorPos == this.invs.get(this.currentInv).size()) {
            this.cursorPos--;
        }
    }

    /**
     * Returns the current cursor position.
     *
     * @return the current cursor position
     */
    public int getCursorPos() {
        return this.cursorPos;
    }

    /**
     * Returns the saved cursor position.
     *
     * @return the saved cursor position
     */
    public int getSavedPos() {
        return this.savedPos;
    }

    /**
     * Returns the current inventory.
     *
     * @return the current inventory
     */
    public InvIndices getCurrentInv() {
        return InvIndices.values()[this.currentInv];
    }

    /**
     * Returns the saved inventory.
     *
     * @return the saved inventory
     */
    public InvIndices getSavedInv() {
        return InvIndices.values()[this.savedInv];
    }

    /**
     * Returns a list of the items in the inventory given by {@code inv}.
     *
     * @param inv
     *            the inventory index to get the names from
     * @return the names of the inventory's items in order
     */
    public ArrayList<String> getItemNames(InvIndices inv) {
        ArrayList<String> names = new ArrayList<>();

        for (Item i : this.invs.get(inv.ordinal())) {
            names.add(i.getName());
        }

        return names;
    }

    /**
     * Switch which inventory is selected.
     *
     * @param newInv
     *            - the index of the inventory to switch to
     */
    public void changeInv(InvIndices newInv) {
        this.currentInv = newInv.ordinal();
        this.cursorPos = 0;
    }

    /**
     * Save cursor position or advance to item movement if a saved position
     * exists.
     *
     * @throws ItemRestrictionException
     */
    public void selectPosMove() throws ItemRestrictionException {

        if (this.savedPos < 0) {
            this.savedPos = this.cursorPos;
        } else {
            this.moveItem();
            this.savedPos = -1;
        }

        this.savedInv = this.currentInv;
    }

    //TODO:Implement splitting?, otherwise disable stacking

    /**
     * Reset cursor position.
     */
    public void unselectPos() {

        if (this.savedPos >= 0) {
            this.cursorPos = this.savedPos;
            this.savedPos = -1;

            this.currentInv = this.savedInv;
        }
    }

    /**
     * Exception for trying to add an item that doesn't meet an inventory's
     * requirements.
     */
    protected class ItemRestrictionException extends Exception {

        //CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
        @Override
        public String getMessage() {

            return "Item not allowed at destination";
        }
    }
}
