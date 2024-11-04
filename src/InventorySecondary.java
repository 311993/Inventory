import java.util.Map;
import java.util.TreeMap;

import InventoryConcept.Item;

/** Layered implementations of secondary methods for Inventory. */
public abstract class InventorySecondary implements Inventory {

    //CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public Item getItem(int slot) {

        assert slot >= 0 && slot < this.size();

        Item removed = this.removeItem(slot);
        this.addItem(slot, removed);

        return removed;
    }

    //CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public void copyItem(Inventory src, String name, int destSlot) {

        assert src != null;
        assert src.nextIndexOf(name, 0) >= 0;
        assert destSlot >= 0 && destSlot < this.size();

        Item original = src.getItem(src.nextIndexOf(name, 0));
        Item copy = new BasicItem(original.getName());

        for (Map.Entry<String, Integer> tag : original.getTags().entrySet()) {
            copy.putTag(tag.getKey(), tag.getValue());
        }

        this.addItem(destSlot, copy);
    }

    //CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public void swapItems(int slot1, int slot2) {

        assert slot1 >= 0 && slot1 < this.size();
        assert slot2 >= 0 && slot2 < this.size();

        Item removed1 = this.removeItem(slot1);
        Item removed2 = this.removeItem(slot2);

        this.addItem(slot1, removed2);
        this.addItem(slot2, removed1);
    }

    //CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public void swapItems(Inventory src, int srcSlot, int destSlot) {

        assert src != null;
        assert srcSlot >= 0 && srcSlot < src.size();
        assert destSlot >= 0 && destSlot < this.size();

        Item srcRemoved = src.removeItem(srcSlot);
        Item destRemoved = this.removeItem(destSlot);

        src.addItem(srcSlot, destRemoved);
        this.addItem(destSlot, srcRemoved);

    }

    //CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public void transferItem(Inventory src, int srcSlot, int destSlot) {

        assert src != null;
        assert srcSlot >= 0 && srcSlot < src.size();
        assert destSlot >= 0 && destSlot < this.size();

        this.addItem(destSlot, src.removeItem(srcSlot));
    }

    //CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public int nextPlacement(Item item, int maxStack) {

        assert item != null;

        int pos = -1;
        int checkAt = 0;
        boolean doneCheckingStacks = false;

        //Try to stack the Item with others of its kind
        while (!doneCheckingStacks) {

            pos = this.nextIndexOf(item.getName(), checkAt);

            //If pos >= 0 a potential stack has been found
            if (pos >= 0) {

                //Make sure the stack is not full
                if (maxStack < 0
                        || this.getItem(pos).tagValue(Item.COUNT) < maxStack) {

                    doneCheckingStacks = true;
                } else {
                    checkAt = pos + 1;
                }

            }
            if (pos < 0 || checkAt >= this.size() - 1) {
                doneCheckingStacks = true;
            }
        }

        //If no viable stack is found, start a new one if possible
        if (pos < 0) {
            pos = this.nextIndexOf(Item.EMPTY_NAME, 0);
        }

        return pos;
    }

    //CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public String useItem(int slot) {

        assert slot >= 0 && slot < this.size();

        Item removed = this.removeItem(slot);

        removed.putTag(Item.COUNT, removed.tagValue(Item.COUNT) - 1);

        this.addItem(slot, removed);

        return removed.getName();
    }

    //CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public boolean isAt(int slot, String name) {

        assert slot >= 0 && slot < this.size();

        return this.getItem(slot).getName().equals(name);

    }

    @Override
    public boolean equals(Object o) {
        boolean equals = false;

        if (o.getClass().equals(this.getClass())) {
            Inventory n = (Inventory) o;

            if (this.size() == n.size()) {
                equals = true;

                for (int i = 0; i < this.size(); i++) {

                    Item removed1 = this.removeItem(i);
                    Item removed2 = n.removeItem(i);

                    if (!removed1.equals(removed2)) {
                        equals = false;
                    }

                    this.addItem(i, removed1);
                    n.addItem(i, removed2);
                }
            }

        }

        return equals;
    }

    @Override
    public String toString() {
        String rep = "{ ";

        for (int i = 0; i < this.size(); i++) {
            Item removed = this.removeItem(i);
            rep += removed.toString() + ", ";
            this.addItem(i, removed);
        }

        rep += " }";
        return rep;
    }

    /**
     * A basic implementation of {@code Item} interface.
     */
    protected static final class BasicItem implements Item {

        /** The name of this item, which serves as its primary identifier. */
        private String name;

        /**
         * Tags denoting the properties of the item, with integer values when
         * appropriate.
         */
        private Map<String, Integer> tags;

        /** Constructs an empty Item. */
        public BasicItem() {
            this(Item.EMPTY_NAME, 0);
        }

        /**
         * Constructs a named(non-empty) Item with count 1.
         *
         * @param name
         *            a String identifier for the Item.
         */
        public BasicItem(String name) {
            this(name, 1);
        }

        /**
         * Constructs a named(non-empty) Item with count n.
         *
         * @param name
         *            a String identifier for the Item.
         * @param count
         *            the number of this Item.
         */
        public BasicItem(String name, int count) {
            this.tags = new TreeMap<String, Integer>();
            this.name = name;
            this.tags.put("count", count);
        }

        //CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
        @Override
        public boolean isEmpty() {
            return this.name.equals(Item.EMPTY_NAME);
        }

        //CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
        @Override
        public String getName() {
            return this.name;
        }

        //CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
        @Override
        public Map<String, Integer> getTags() {
            return this.tags;
        }

        //CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
        @Override
        public boolean hasTag(String tag) {
            return this.tags.containsKey(tag);
        }

        //CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
        @Override
        public void putTag(String tag, int value) {
            this.tags.put(tag, value);
        }

        //CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
        @Override
        public void removeTag(String tag) {
            assert !tag.equals(COUNT);
            this.tags.remove(tag);
        }

        //CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
        @Override
        public int tagValue(String tag) {
            return this.tags.get(tag);
        }

        //CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
        @Override
        public int hashCode() {
            return this.name.hashCode();
        }

        //CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
        @Override
        public boolean equals(Object o) {
            boolean equal = false;

            if (o.getClass().equals(this.getClass())) {
                equal = ((BasicItem) (o)).name.equals(this.name);
            } else if (o.getClass().equals(String.class)) {
                equal = this.name.equals((String) o);
            }

            return equal;
        }
    }
}
