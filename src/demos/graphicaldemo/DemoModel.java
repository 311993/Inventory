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

    //Initial Contents

    /** Initial general inventory. */
    private static final String[] GEN_INIT = { "#Potion", "#Fenix", "&Atma", "",
            "'Dirk", "(Lance", "$Mythril", "", "%Memento", "%Diadem",
            "#Potion" };
    /** Initial general tags. */
    private static final String[] GEN_TAGS = { "USE", "USE", "WEAPON", "",
            "WEAPON", "WEAPON", "ARMOR", "", "RELIC", "RELIC", "USE" };

    /** Initial usable inventory. */
    private static final String[] USABLE_INIT = { "#Elixir", "#Fenix",
            "#Hero" };
    /** Initial hands inventory. */
    private static final String[] HANDS_INIT = { "&Falchion", ")Shield" };
    /** Initial armor inventory. */
    private static final String[] ARMOR_INIT = { "$Force" };
    /** Initial relic inventory. */
    private static final String[] RELICS_INIT = { "%Sprint", "%Armlet" };

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

    //State

    /** Whether or not a message is currently being displayed to the user. */
    private boolean inMessage;

    /** The message that should be displayed. */
    private String message;

    /** Number of items to transfer in a split. */
    private int count;

    /** Whether a splitting process is occuring. */
    private boolean splitting;

    /** Creates a new DemoModel. */
    public DemoModel() {

        //General inventory
        this.inv = new Inventory1(INV_SIZE);

        final int maxCount = 49;

        for (int i = 0; i < this.inv.size(); i++) {

            int count = 1;
            if (GEN_TAGS[i].equals("USE")) {
                count = 1 + (int) (Math.random() * maxCount);
            }

            Item m = new BasicItem(GEN_INIT[i], count);
            m.putTag(GEN_TAGS[i], 0);
            this.inv.addItem(i, m);
        }

        //Usable inventory
        this.usable = new Inventory1(USE_SIZE);
        this.usable.restrict("USE");

        for (int i = 0; i < this.usable.size(); i++) {
            int count = 1 + (int) (Math.random() * maxCount);
            Item m = new BasicItem(USABLE_INIT[i], count);
            m.putTag("USE", 0);
            this.usable.addItem(i, m);
        }

        //Hands inventory
        this.hands = new Inventory1(2);
        this.hands.restrict("WEAPON");

        for (int i = 0; i < this.hands.size(); i++) {
            Item m = new BasicItem(HANDS_INIT[i], 1);
            m.putTag("WEAPON", 0);
            this.hands.addItem(i, m);
        }

        //Armor inventory
        this.armor = new Inventory1(1);
        this.armor.restrict("ARMOR");

        for (int i = 0; i < this.armor.size(); i++) {
            Item m = new BasicItem(ARMOR_INIT[i], 1);
            m.putTag("ARMOR", 0);
            this.armor.addItem(i, m);
        }

        //Relic inventory
        this.relics = new Inventory1(2);
        this.relics.restrict("RELIC");

        for (int i = 0; i < this.relics.size(); i++) {
            Item m = new BasicItem(RELICS_INIT[i], 1);
            m.putTag("RELIC", 0);
            this.relics.addItem(i, m);
        }

        //Add inventories to the list
        this.invs = new ArrayList<>();
        this.invs.add(this.inv);
        this.invs.add(this.usable);
        this.invs.add(this.hands);
        this.invs.add(this.armor);
        this.invs.add(this.relics);

        //Init state
        this.currentInv = 0;
        this.savedInv = 0;
        this.cursorPos = 0;
        this.savedPos = -1;

        this.inMessage = false;
        this.count = -1;
        this.message = "";
        this.splitting = false;
    }

    /**
     * Swaps the item at the cursor position in the current inventory with the
     * item at the saved position in the saved inventory.
     */
    private void moveItem() throws ItemRestrictionException {

        Inventory dest = this.invs.get(this.currentInv);
        Inventory src = this.invs.get(this.savedInv);

        if (!dest.isAllowed(src.getItem(this.savedPos))
                || !src.isAllowed(dest.getItem(this.cursorPos))) {
            this.unselectPos();
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
     * Splits the item at the saved cursor position with the current cursor
     * position.
     */
    private void splitItem() {
        Inventory dest = this.invs.get(this.currentInv);
        Inventory src = this.invs.get(this.savedInv);

        dest.splitItem(src, this.savedPos, this.cursorPos, this.count);

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
    public ArrayList<String> getItemLabels(InvIndices inv) {
        ArrayList<String> names = new ArrayList<>();

        for (Item i : this.invs.get(inv.ordinal())) {

            String label = "";

            //Add count / type prefix to label
            switch (inv) {
                case GENERAL:
                case USABLE:
                    int count = i.tagValue(Item.COUNT);
                    final int radix = 10;

                    label += count;

                    label += " ";

                    if (count < radix) {
                        label += " ";
                    }

                    if (i.getName().length() > 0) {
                        label += i.getName().charAt(0);
                    } else {
                        label = label.substring(1);
                    }
                    break;

                case HANDS:
                    if (i.getName().length() > 0) {
                        label += i.getName().charAt(0);
                    } else {
                        label += "&";
                    }
                    label += "  ";

                    break;
                case ARMOR:
                    label += "$  ";
                    break;
                case RELICS:
                    label += "%  ";
                    break;
                default:
            }

            //Add name to label
            if (i.getName().length() > 0) {
                label += i.getName().substring(1);
            }
            names.add(label);
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
     * Return whether on not a message is being displayed.
     *
     * @return true if a message should be displayed, false otherwise
     */
    public boolean isInMessage() {
        return this.inMessage;
    }

    /**
     * Clears any message being displayed.
     */
    public void clearMessage() {
        this.inMessage = false;

        if (this.splitting) {
            this.splitItem();
            this.savedPos = -1;
            this.savedInv = this.currentInv;
            this.splitting = false;
            this.count = -1;
        }
    }

    /**
     * Sets the message to be displayed to {@code msg}.
     *
     * @param msg
     *            the message to display.
     */
    public void sendMessage(String msg) {
        this.message = msg;
        this.inMessage = true;
    }

    /**
     * Gets the message that should be displayed.
     *
     * @return the message to be displayed
     */
    public String getMessage() {

        String msg = this.message;

        if (this.count >= 0) {
            msg += this.count;
        }

        return msg;
    }

    /** Increments the number of items to split. */
    public void incrementCount() {
        if (this.savedPos >= 0 && this.count < this.invs.get(this.savedInv)
                .getItem(this.savedPos).tagValue(Item.COUNT)) {
            this.count++;
        }

    }

    /** Decrements the number of items to split. */
    public void decrementCount() {
        if (this.count > 0) {
            this.count--;
        }
    }

    /**
     * Save cursor position or advance to item movement if a saved position
     * exists.
     *
     * @throws ItemRestrictionException
     *             when the selected position is invalid
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

    /**
     * Initiate stack splitting if a saved position exists.
     *
     * @throws ItemRestrictionException
     *             when the selected position is invalid
     */
    public void selectPosSplit() throws ItemRestrictionException {

        if (this.savedPos >= 0) {

            Inventory dest = this.invs.get(this.currentInv);
            Inventory src = this.invs.get(this.savedInv);

            if (!dest.isAllowed(src.getItem(this.savedPos))
                    || src.getItem(this.savedPos).isEmpty()
                    || !dest.getItem(this.cursorPos).isEmpty()) {
                this.unselectPos();
                throw new ItemRestrictionException();
            }

            this.splitting = true;
            this.count = 0;
        }
    }

    /**
     * Send the item at the cursor position to the first valid position in a
     * different inventory, if possible. If the item is not in the general
     * inventory, it will be sent there. Otherwise, the item will be
     * "auto-equipped" to another inventory based on the tag requirements.
     */
    public void autoSend() {

        Item sent = this.invs.get(this.currentInv).getItem(this.cursorPos);
        InvIndices destInv = InvIndices.GENERAL;

        if (this.currentInv == 0) {
            if (sent.hasTag("WEAPON")) {
                destInv = InvIndices.HANDS;
            } else if (sent.hasTag("ARMOR")) {
                destInv = InvIndices.ARMOR;
            } else if (sent.hasTag("RELIC")) {
                destInv = InvIndices.RELICS;
            } else if (sent.hasTag("USE")) {
                destInv = InvIndices.USABLE;
            }
        }

        int destPos = this.invs.get(destInv.ordinal()).nextPlacement(sent, 0);

        if (destInv.ordinal() != this.currentInv && destPos >= 0) {
            this.savedPos = this.cursorPos;
            this.savedInv = this.currentInv;
            this.currentInv = destInv.ordinal();
            this.cursorPos = destPos;
            try {
                this.moveItem();
            } catch (ItemRestrictionException e) {
                //Will not occur since destPos guaranteed to be valid add position
            }
            this.cursorPos = this.savedPos;
            this.currentInv = this.savedInv;
            this.savedPos = -1;
        }
    }

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
