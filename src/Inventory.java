/**
 * {@code InventoryKernel} enhanced with secondary methods.
 *
 * @author David Stuckey
 */
public interface Inventory extends InventoryKernel {

    Item getItem(int slot);

    void copyItem(InventoryConcept src, String name, int destSlot);

    void swapItems(int slot1, int slot2);

    void swapItems(InventoryConcept src, int srcSlot, int destSlot);

    boolean transferItem(InventoryConcept src, int srcSlot, int destSlot);

    int nextPlacement(Item item, int maxStack);

    void useItem(int slot);

    boolean isAt(int slot, Item item);

    /** Representation of a single item in the inventory. */
    interface Item {

        /** Constant for count tag. An item is guaranteed to have this tag. */
        String COUNT = "count";

        /** Constant for empty item name. */
        String EMPTY_NAME = "";

        /**
         * Returns whether this is an empty Item.
         *
         * @return whether {@code this} is empty.
         *
         * @ensures isEmpty = (this.name == Item.EMPTY_NAME )
         */
        boolean isEmpty();

        /**
         * Returns the name/identifier of this Item.
         *
         * @return the name of {@code this}.
         *
         * @ensures getName = this.name
         */
        String getName();

        /**
         * Returns whether this Item has the tag {@code tag}.
         *
         * @param tag
         *            the tag to check {@code this} for.
         *
         * @return true if the Item has the tag {@code tag} and false otherwise.
         *
         * @ensures hasTag = ( (tag, any) is in this.tags )
         */
        boolean hasTag(String tag);

        /**
         * If the tag is not already present, adds the {@code (tag,value)} pair
         * to this Item, otherwise updates the value associated with the tag.
         *
         * @param tag
         *            the tag to add / update
         * @param tagVal
         *            the tag value to add / update
         *
         * @updates this
         *
         * @ensures (tag, tagVal) is in this.tags
         */
        void putTag(String tag, int tagVal);

        /**
         * Removes {@code tag} from this Item.
         *
         * @param tag
         *            the tag to remove from {@code this}
         *
         * @requires <pre>
         * - (tag, any) is in this.tags.
         *- tag != Item.COUNT
         * </pre>
         * @ensures (tag, any) is not in this.tags
         */
        void removeTag(String tag);

        /**
         * Returns the integer tag value associated with {@code tag}.
         *
         * @param tag
         *            The tag to get the associated value of.
         * @return The integer value associated with {@code tag}.
         *
         * @requires (tag, x) is in this.tags, where x is an integer
         *
         * @ensures tagValue = x
         */
        int tagValue(String tag);
    }
}
