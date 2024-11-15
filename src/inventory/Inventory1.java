package inventory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Implementation of Inventory on Array and Set.
 *
 * @convention <pre>
 * |$this.slots| > 0 and
 * for all 0 <= i < |$this.slots|:
 *  [$this.slots[i] is defined and is not null] and
 *  [if $this.slots[i] is not an empty Item, it has all the entries in this.reqs as tags]
 * </pre>
 *
 * @correspondence <pre>
 *  this = [the Items in $this.slots]
 *</pre>
 *
 * @author David Stuckey
 */
public class Inventory1 extends InventorySecondary {

    /** The primary representation variable: the slots of this. */
    private Item[] slots;

    /** The requirements for an Item to be added to this. */
    private Set<String> reqs;

    /**
     * Creates initial representation.
     *
     * @param size
     *            - the number of slots this will have
     */
    private void createNewRep(int size) {
        this.slots = new Item[size];
        this.reqs = new HashSet<String>();

        for (int i = 0; i < size; i++) {
            this.slots[i] = new BasicItem();
        }
    }

    /**
     * Creates a new Inventory with a single slot.
     */
    public Inventory1() {
        this.createNewRep(1);
    }

    /**
     * Create a new Inventory with one or more slots.
     *
     * @param size
     *            the number of slots this will have
     *
     * @requires size > 0
     */
    public Inventory1(int size) {
        assert size > 0 : "Violation of size > 0";

        this.createNewRep(size);
    }

    //CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public void addItem(int slot, Item item) {
        assert 0 <= slot : "Violation of 0 <= slot";
        assert slot < this.slots.length : "Violation of slot < |this|";
        assert item != null : "Violation of item is not null";
        assert this.slots[slot].isEmpty() || this.slots[slot].equals(
                item) : "Violation of slot is empty or has an Item of the same name.";
        assert this.isAllowed(item) : "Violation of isAllowed(item)";

        Item dest = this.slots[slot];

        if (dest.equals(item)) {
            dest.putTag(Item.COUNT,
                    dest.tagValue(Item.COUNT) + item.tagValue(Item.COUNT));
        } else {
            this.slots[slot] = item;
        }
    }

    //CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public Item removeItem(int slot) {
        assert 0 <= slot : "Violation of 0 <= slot";
        assert slot < this.slots.length : "Violation of slot < |this|";

        Item removed = this.slots[slot];
        this.slots[slot] = new BasicItem();

        return removed;
    }

    //CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public ArrayList<Item> restrict(String tag) {
        if (!this.reqs.contains(tag)) {
            this.reqs.add(tag);
        }

        ArrayList<Item> removed = new ArrayList<>();

        for (int i = 0; i < this.slots.length; i++) {

            if (!this.slots[i].hasTag(tag)) {

                removed.add(this.slots[i]);
                this.slots[i] = new BasicItem();
            }
        }

        return removed;
    }

    //CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public void freeRestrictions() {
        this.reqs = new HashSet<>();
    }

    //CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public boolean isAllowed(Item item) {
        assert item != null : "Violation of item is not null";

        boolean allow = true;

        for (String t : this.reqs) {
            if (!item.hasTag(t)) {
                allow = false;
            }
        }

        if (item.getName().equals(Item.EMPTY_NAME)) {
            allow = true;
        }

        return allow;
    }

    //CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public int nextIndexOf(String name, int pos) {
        assert 0 <= pos : "Violation of 0 <= pos";
        assert pos < this.slots.length : "Violation of pos < |this|";

        int i = pos;
        boolean hasItem = false;

        while (!hasItem && i < this.slots.length) {
            hasItem = this.slots[i].getName().equals(name);
            i++;
        }

        if (!hasItem) {
            i = 0;
        }

        return i - 1;
    }

    //CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public int size() {
        return this.slots.length;
    }

    //CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public void clear() {
        this.createNewRep(1);
    }

    //CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public Inventory newInstance() {
        return new Inventory1();
    }

    //CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public void transferFrom(Inventory src) {
        assert src != null : "Violation of: source is not null";
        assert src != this : "Violation of: source is not this";
        assert src instanceof Inventory1 : ""
                + "Violation of: source is of dynamic type Inventory1";

        Inventory1 localSrc = (Inventory1) src;

        localSrc.reqs = this.reqs;
        localSrc.slots = this.slots;

        this.createNewRep(1);
    }

    //CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public Iterator<Item> iterator() {
        return new InventoryIterator();
    }

    /**
     * Implementation of {@code Iterator} interface for {@code Inventory1}.
     */
    private final class InventoryIterator implements Iterator<Item> {

        /** The current index. */
        private int i;

        /**
         * Creates a new Iterator.
         */
        private InventoryIterator() {
            this.i = 0;
        }

        @Override
        public boolean hasNext() {
            return this.i < Inventory1.this.size();
        }

        @Override
        public Item next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }

            Item removed = Inventory1.this.removeItem(this.i);
            Inventory1.this.addItem(this.i, removed);
            this.i++;

            return removed;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException(
                    "remove operation not supported");
        }

    }
}
