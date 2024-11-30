import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import inventory.Inventory;
import inventory.Inventory.Item;
import inventory.Inventory1;
import inventory.InventorySecondary.BasicItem;

/**
 * Test array for Inventory kernel methods, as implemented in Inventory1.
 *
 * @author David Stuckey
 */
public class InventoryKernelTest {

    /**
     * Helper constructor for inventory implementation under test.
     *
     * @param n
     *            - the size of the desired inventory, or 0 for the default size
     * @return an instance of the inventory implementation under test
     */
    private Inventory constructor(int n) {

        if (n == 0) {
            return new Inventory1();
        } else {
            return new Inventory1(n);
        }
    }

    /**
     * Helper constructor for inventory implementation under test.
     *
     * @param names
     *            - the items in the desired inventory
     * @return an instance of the inventory implementation under test containing
     *         items with the names given and no tags except count
     */
    private Inventory constructor(String... names) {

        Inventory inv = this.constructor(names.length);

        for (int i = 0; i < names.length; i++) {
            inv.addItem(i, new BasicItem(names[i]));
        }

        return inv;
    }

    //Tests for constructor

    /** Test for default constructor. */
    @Test
    public final void testConstructorDefault() {

        Inventory testInv = this.constructor();

        assertEquals(testInv.size(), 1);
        assertTrue(testInv.isAllowed(new BasicItem()));
    }

    /** Test for int constructor with minimum size. */
    @Test
    public final void testConstructorOne() {

        Inventory testInv = this.constructor(1);

        assertEquals(testInv.size(), 1);
        assertTrue(testInv.isAllowed(new BasicItem()));
    }

    /** Test for int constructor with larger size. */
    @Test
    public final void testConstructorTen() {

        final int invSize = 10;
        Inventory testInv = this.constructor(invSize);

        assertEquals(testInv.size(), invSize);
        assertTrue(testInv.isAllowed(new BasicItem()));
    }

    //Test for size()

    /** Test for size() with minimum size inventory. */
    @Test
    public final void testSizeOne() {

        Inventory testInv = this.constructor();
        Inventory refInv = this.constructor();

        assertEquals(testInv.size(), 1);
        assertEquals(testInv, refInv);

    }

    /** Test for size() with larger size inventory. */
    @Test
    public final void testSizeTen() {

        final int invSize = 10;

        Inventory testInv = this.constructor(invSize);
        Inventory refInv = this.constructor(invSize);

        assertEquals(testInv.size(), invSize);
        assertEquals(testInv, refInv);

    }

    /** Test for size() with larger size inventory with non-empty items. */
    @Test
    public final void testSizeFilled() {

        final int invSize = 3;

        Inventory testInv = this.constructor("Foo", "Bar", "Lorem");
        Inventory refInv = this.constructor("Foo", "Bar", "Lorem");

        assertEquals(testInv.size(), invSize);
        assertEquals(testInv, refInv);

    }

    // Tests for addItem()

    /** Test for addItems() method with an empty item. */
    @Test
    public final void testAddItemEmpty() {

        //Set up test variables
        final int invSize = 10;
        Inventory testInv = this.constructor(invSize);

        Item testItem = new BasicItem();
        Item refItem = new BasicItem();

        final int slot = 1;

        //Call method under test
        testInv.addItem(slot, testItem);

        //Check inventory state is as expected
        assertEquals(testInv.size(), invSize);

        for (int i = 0; i < invSize; i++) {
            assertEquals(testInv.removeItem(i), refItem);
        }
    }

    /** Test for addItems() method with a named item. */
    @Test
    public final void testAddItemNamed() {

        //Set up test variables
        final int invSize = 10;
        Inventory testInv = this.constructor(invSize);

        Item blankItem = new BasicItem();
        Item testItem = new BasicItem("Foo");
        Item refItem = new BasicItem("Foo");

        final int slot = 1;

        //Call method under test
        testInv.addItem(slot, testItem);

        //Check inventory state is as expected
        assertEquals(testInv.size(), invSize);

        Item removed;

        for (int i = 0; i < invSize; i++) {

            removed = testInv.removeItem(i);

            if (i == slot) {
                assertEquals(removed, refItem);
            } else {
                assertEquals(removed, blankItem);
            }

        }
    }

    /** Test for addItems() method with a named item with tags. */
    @Test
    public final void testAddItemTags() {

        //Set up test variables
        final int invSize = 10;
        Inventory testInv = this.constructor(invSize);

        Item blankItem = new BasicItem();
        Item testItem = new BasicItem("Foo", 2);
        Item refItem = new BasicItem("Foo", 2);

        testItem.putTag("TEST", 0);
        refItem.putTag("TEST", 0);

        final int slot = 1;

        //Call method under test
        testInv.addItem(slot, testItem);

        //Check inventory state is as expected
        assertEquals(testInv.size(), invSize);

        Item removed;

        for (int i = 0; i < invSize; i++) {

            removed = testInv.removeItem(i);

            if (i == slot) {
                assertEquals(removed, refItem);
            } else {
                assertEquals(removed, blankItem);
            }

        }
    }

    /** Test for addItems() method with multiple items. */
    @Test
    public final void testAddItemMultiple() {

        //Set up test variables
        final int invSize = 10;
        Inventory testInv = this.constructor(invSize);

        Item blankItem = new BasicItem();
        Item testItem1 = new BasicItem("Foo");
        Item refItem1 = new BasicItem("Foo");

        Item testItem2 = new BasicItem("Bar");
        Item refItem2 = new BasicItem("Bar");

        final int slot1 = 1, slot2 = 8;

        //Call method under test
        testInv.addItem(slot1, testItem1);
        testInv.addItem(slot2, testItem2);

        //Check inventory state is as expected
        assertEquals(testInv.size(), invSize);

        Item removed;

        for (int i = 0; i < invSize; i++) {

            removed = testInv.removeItem(i);

            if (i == slot1) {
                assertEquals(removed, refItem1);
            } else if (i == slot2) {
                assertEquals(removed, refItem2);
            } else {
                assertEquals(removed, blankItem);
            }

        }
    }

    /**
     * Test for addItems() method stacking behavior when adding at same position
     * with equal Items.
     */
    @Test
    public final void testAddItemStacked() {

        //Set up test variables
        final int invSize = 10;
        Inventory testInv = this.constructor(invSize);

        Item blankItem = new BasicItem();
        Item testItem1 = new BasicItem("Foo", 2);
        Item testItem2 = new BasicItem("Foo", 1);
        Item refItem = new BasicItem("Foo", 2 + 1);

        testItem1.putTag("TEST", 0);
        testItem2.putTag("TEST", 0);
        refItem.putTag("TEST", 0);

        final int slot = 1;

        //Call method under test
        testInv.addItem(slot, testItem1);
        testInv.addItem(slot, testItem2);

        //Check inventory state is as expected
        assertEquals(testInv.size(), invSize);

        Item removed;

        for (int i = 0; i < invSize; i++) {

            removed = testInv.removeItem(i);

            if (i == slot) {
                assertEquals(removed, refItem);
            } else {
                assertEquals(removed, blankItem);
            }

        }
    }

    /**
     * Test for addItems() method stacking behavior when adding at same position
     * with Items with same name but differing tags.
     */
    @Test
    public final void testAddItemStackedMixedTags() {

        //Set up test variables
        final int invSize = 10;
        Inventory testInv = this.constructor(invSize);

        Item blankItem = new BasicItem();
        Item testItem1 = new BasicItem("Foo", 2);
        Item testItem2 = new BasicItem("Foo", 1);
        Item expectedItem = new BasicItem("Foo", 2 + 1);

        testItem1.putTag("MATCH", 0);
        testItem1.putTag("OLD", 0);

        testItem2.putTag("MATCH", 1);
        testItem2.putTag("NEW", 0);

        expectedItem.putTag("OLD", 0);
        expectedItem.putTag("MATCH", 1);
        expectedItem.putTag("NEW", 0);

        final int slot = 1;

        //Call method under test
        testInv.addItem(slot, testItem1);
        testInv.addItem(slot, testItem2);

        //Check inventory state is as expected
        assertEquals(testInv.size(), invSize);

        Item removed;

        for (int i = 0; i < invSize; i++) {

            removed = testInv.removeItem(i);

            if (i == slot) {
                assertEquals(removed, expectedItem);
            } else {
                assertEquals(removed, blankItem);
            }

        }
    }

    //Tests for removeItem()

    /** Test for removeItem() with empty item. */
    @Test
    public final void testRemoveItemEmpty() {

        Inventory testInv = this.constructor();
        Inventory expectedInv = this.constructor();

        Item expected = new BasicItem();

        Item removed = testInv.removeItem(0);

        assertEquals(testInv, expectedInv);
        assertEquals(removed, expected);
    }

    /** Test for removeItem() with non-empty item. */
    @Test
    public final void testRemoveItemNamed() {

        Inventory testInv = this.constructor("Foo");
        Inventory expectedInv = this.constructor();

        Item expected = new BasicItem("Foo");

        Item removed = testInv.removeItem(0);

        assertEquals(testInv, expectedInv);
        assertEquals(removed, expected);
    }

    /** Test for removeItem() with non-empty item from larger inventory. */
    @Test
    public final void testRemoveItemNamed2() {

        Inventory testInv = this.constructor("Foo", "Bar");
        Inventory expectedInv = this.constructor("Foo", "");

        Item expected = new BasicItem("Bar");

        Item removed = testInv.removeItem(1);

        assertEquals(testInv, expectedInv);
        assertEquals(removed, expected);
    }

    /** Test for removeItem() with non-empty item with tags. */
    @Test
    public final void testRemoveItemTags() {

        Inventory testInv = this.constructor();
        Inventory expectedInv = this.constructor();

        Item testItem = new BasicItem("Foo", 2);
        testItem.putTag("TEST", 0);

        testInv.addItem(0, testItem);

        Item expected = new BasicItem("Foo", 2);
        expected.putTag("TEST", 0);

        Item removed = testInv.removeItem(0);

        assertEquals(testInv, expectedInv);
        assertEquals(removed, expected);
    }

    //Tests for nextIndexOf()

    /** Test for nextIndexof() starting from 0, with desired item at 0. */
    @Test
    public final void testNextIndexOfFromZeroAtZero() {

        Inventory testInv = this.constructor("Foo");
        Inventory expectedInv = this.constructor("Foo");

        final int expectedPos = 0;
        int actualPos = testInv.nextIndexOf("Foo", 0);

        assertEquals(testInv, expectedInv);
        assertEquals(actualPos, expectedPos);

    }

    /**
     * Test for nextIndexof() starting from 0, with desired item at a higher
     * position.
     */
    @Test
    public final void testNextIndexOfFromZeroFound() {

        Inventory testInv = this.constructor("Foo", "Lorem", "Ipsum", "Bar");
        Inventory expectedInv = this.constructor("Foo", "Lorem", "Ipsum",
                "Bar");

        final int expectedPos = 3;
        int actualPos = testInv.nextIndexOf("Bar", 0);

        assertEquals(testInv, expectedInv);
        assertEquals(actualPos, expectedPos);
    }

    /**
     * Test for nextIndexof() starting from 0, with desired item not in the
     * inventory.
     */
    @Test
    public final void testNextIndexOfFromZeroNotFound() {

        Inventory testInv = this.constructor("Foo", "Lorem", "Ipsum", "Bar");
        Inventory expectedInv = this.constructor("Foo", "Lorem", "Ipsum",
                "Bar");

        final int expectedPos = -1;
        int actualPos = testInv.nextIndexOf("Zzyzx", 0);

        assertEquals(testInv, expectedInv);
        assertEquals(actualPos, expectedPos);
    }

    /**
     * Test for nextIndexof() starting from middle, with desired item at a
     * higher position.
     */
    @Test
    public final void testNextIndexOfFromMiddleAfter() {

        Inventory testInv = this.constructor("Foo", "Lorem", "Ipsum", "Bar");
        Inventory expectedInv = this.constructor("Foo", "Lorem", "Ipsum",
                "Bar");

        final int expectedPos = 3;
        int actualPos = testInv.nextIndexOf("Bar", testInv.size() / 2);

        assertEquals(testInv, expectedInv);
        assertEquals(actualPos, expectedPos);
    }

    /**
     * Test for nextIndexof() starting from middle, with desired item at a lower
     * position.
     */
    @Test
    public final void testNextIndexOfFromMiddleBefore() {

        Inventory testInv = this.constructor("Foo", "Lorem", "Ipsum", "Bar");
        Inventory expectedInv = this.constructor("Foo", "Lorem", "Ipsum",
                "Bar");

        final int expectedPos = -1;
        int actualPos = testInv.nextIndexOf("Foo", testInv.size() / 2);

        assertEquals(testInv, expectedInv);
        assertEquals(actualPos, expectedPos);
    }

    /**
     * Test for nextIndexof() starting from middle of inventory, with desired
     * item not in the inventory.
     */
    @Test
    public final void testNextIndexOfFromMiddleNotFound() {

        Inventory testInv = this.constructor("Foo", "Lorem", "Ipsum", "Bar");
        Inventory expectedInv = this.constructor("Foo", "Lorem", "Ipsum",
                "Bar");

        final int expectedPos = -1;
        int actualPos = testInv.nextIndexOf("Zzyzx", testInv.size() / 2);

        assertEquals(testInv, expectedInv);
        assertEquals(actualPos, expectedPos);
    }

    /* Combined tests for restrict() and isAllowed(). */

    /** Test for isAllowed() with an unrestricted inventory. */
    @Test
    public final void testIsAllowedUnrestricted() {
        Inventory testInv = this.constructor();
        Inventory refInv = this.constructor();

        Item blankItem = new BasicItem();
        Item namedItem = new BasicItem("Foo");
        Item taggedItem = new BasicItem("Bar", 2);
        taggedItem.putTag("TEST", 0);

        assertTrue(testInv.isAllowed(blankItem));
        assertTrue(testInv.isAllowed(namedItem));
        assertTrue(testInv.isAllowed(taggedItem));
        assertEquals(testInv, refInv);
    };

    /**
     * Test for isAllowed() and restrict() with a single restriction on the
     * inventory.
     */
    @Test
    public final void testIsAllowedOneRestriction() {
        Inventory testInv = this.constructor();
        Inventory refInv = this.constructor();

        Item blankItem = new BasicItem();
        Item namedItem = new BasicItem("Foo");

        Item wrongTag = new BasicItem("Bar", 2);
        wrongTag.putTag("WRONG", 0);

        Item exactMatch = new BasicItem("Lorem", 2);
        exactMatch.putTag("TEST", 0);

        Item addedTags = new BasicItem("Ipsum", 2);
        addedTags.putTag("TEST", 0);
        addedTags.putTag("MORE", 0);

        ArrayList<Item> removed = testInv.restrict("TEST");
        assertEquals(testInv, refInv);

        assertTrue(testInv.isAllowed(blankItem));
        assertFalse(testInv.isAllowed(namedItem));
        assertFalse(testInv.isAllowed(wrongTag));
        assertTrue(testInv.isAllowed(exactMatch));
        assertTrue(testInv.isAllowed(addedTags));
        assertEquals(testInv, refInv);
        assertEquals(removed.size(), 0);
    };

    /**
     * Test for isAllowed() and restrict() with multiple restrictions on the
     * inventory.
     */
    @Test
    public final void testIsAllowedTwoRestrictions() {
        Inventory testInv = this.constructor();
        Inventory refInv = this.constructor();

        Item blankItem = new BasicItem();
        Item namedItem = new BasicItem("Foo");

        Item wrongTag = new BasicItem("Bar", 2);
        wrongTag.putTag("WRONG", 0);

        Item exactMatch = new BasicItem("Lorem", 2);
        exactMatch.putTag("TEST", 0);
        exactMatch.putTag("RESTRICT", 0);

        Item addedTags = new BasicItem("Ipsum", 2);
        addedTags.putTag("TEST", 0);
        addedTags.putTag("RESTRICT", 0);
        addedTags.putTag("MORE", 0);

        Item notEnoughTags = new BasicItem("Dolor", 2);
        notEnoughTags.putTag("TEST", 0);

        ArrayList<Item> removed1 = testInv.restrict("TEST");
        ArrayList<Item> removed2 = testInv.restrict("RESTRICT");
        assertEquals(testInv, refInv);

        assertTrue(testInv.isAllowed(blankItem));
        assertFalse(testInv.isAllowed(namedItem));
        assertFalse(testInv.isAllowed(wrongTag));
        assertFalse(testInv.isAllowed(notEnoughTags));
        assertTrue(testInv.isAllowed(exactMatch));
        assertTrue(testInv.isAllowed(addedTags));
        assertEquals(testInv, refInv);
        assertEquals(removed1.size(), 0);
        assertEquals(removed2.size(), 0);
    };

    /**
     * Test for restrict() with items already in the inventory.
     */
    @Test
    public final void testRestrictFilledInventory() {

        final int invSize = 4;

        Inventory testInv = this.constructor(invSize);
        Inventory expectedInv = this.constructor(invSize);

        testInv.addItem(1, new BasicItem("Foo"));

        Item rightTag = new BasicItem("Bar");
        rightTag.putTag("TEST", 0);

        Item rightTag2 = new BasicItem("Bar");
        rightTag2.putTag("TEST", 0);

        testInv.addItem(2, rightTag);
        expectedInv.addItem(2, rightTag2);

        Item wrongTag = new BasicItem("Lorem");
        wrongTag.putTag("WRONG", 0);

        Item wrongTag2 = new BasicItem("Lorem");
        wrongTag2.putTag("WRONG", 0);

        testInv.addItem(invSize - 1, wrongTag);

        ArrayList<Item> removed = testInv.restrict("TEST");

        assertEquals(testInv, expectedInv);

        assertEquals(removed.size(), 2);
        assertEquals(removed.get(0), new BasicItem("Foo"));
        assertEquals(removed.get(1), wrongTag2);
        assertTrue(testInv.isAllowed(rightTag2));
        assertFalse(testInv.isAllowed(wrongTag2));
    }

    /* Tests for freeRestrictions() */

    /** Test for freeRestrictions() on an unrestricted inventory. */
    @Test
    public final void testFreeRestrictionsUnrestricted() {
        Inventory testInv = this.constructor();
        Inventory refInv = this.constructor();

        Item blankItem = new BasicItem();
        Item namedItem = new BasicItem("Foo");
        Item taggedItem = new BasicItem("Bar", 2);
        taggedItem.putTag("TEST", 0);

        assertTrue(testInv.isAllowed(blankItem));
        assertTrue(testInv.isAllowed(namedItem));
        assertTrue(testInv.isAllowed(taggedItem));

        testInv.freeRestrictions();

        assertTrue(testInv.isAllowed(blankItem));
        assertTrue(testInv.isAllowed(namedItem));
        assertTrue(testInv.isAllowed(taggedItem));
        assertEquals(testInv, refInv);
    }

    /** Test for freeRestrictions() on a singly-restricted inventory. */
    @Test
    public final void testFreeRestrictionsOneRestriction() {
        Inventory testInv = this.constructor();
        Inventory refInv = this.constructor();

        Item blankItem = new BasicItem();
        Item namedItem = new BasicItem("Foo");
        Item wrongTag = new BasicItem("Bar", 2);
        wrongTag.putTag("WRONG", 0);

        Item exactMatch = new BasicItem("Lorem", 2);
        exactMatch.putTag("TEST", 0);

        Item addedTags = new BasicItem("Ipsum", 2);
        addedTags.putTag("TEST", 0);
        addedTags.putTag("MORE", 0);

        testInv.restrict("TEST");

        assertTrue(testInv.isAllowed(blankItem));
        assertFalse(testInv.isAllowed(namedItem));
        assertFalse(testInv.isAllowed(wrongTag));
        assertTrue(testInv.isAllowed(exactMatch));
        assertTrue(testInv.isAllowed(addedTags));

        testInv.freeRestrictions();

        assertTrue(testInv.isAllowed(blankItem));
        assertTrue(testInv.isAllowed(namedItem));
        assertTrue(testInv.isAllowed(wrongTag));
        assertTrue(testInv.isAllowed(exactMatch));
        assertTrue(testInv.isAllowed(addedTags));
        assertEquals(testInv, refInv);
    }

    /** Test for freeRestrictions() on a doubly-restricted inventory. */
    @Test
    public final void testFreeRestrictionsTwoRestrictions() {
        Inventory testInv = this.constructor();
        Inventory refInv = this.constructor();

        Item blankItem = new BasicItem();
        Item namedItem = new BasicItem("Foo");

        Item wrongTag = new BasicItem("Bar", 2);
        wrongTag.putTag("WRONG", 0);

        Item exactMatch = new BasicItem("Lorem", 2);
        exactMatch.putTag("TEST", 0);
        exactMatch.putTag("RESTRICT", 0);

        Item addedTags = new BasicItem("Ipsum", 2);
        addedTags.putTag("TEST", 0);
        addedTags.putTag("RESTRICT", 0);
        addedTags.putTag("MORE", 0);

        Item notEnoughTags = new BasicItem("Dolor", 2);
        notEnoughTags.putTag("TEST", 0);

        testInv.restrict("TEST");
        testInv.restrict("RESTRICT");

        assertTrue(testInv.isAllowed(blankItem));
        assertFalse(testInv.isAllowed(namedItem));
        assertFalse(testInv.isAllowed(wrongTag));
        assertFalse(testInv.isAllowed(notEnoughTags));
        assertTrue(testInv.isAllowed(exactMatch));
        assertTrue(testInv.isAllowed(addedTags));

        testInv.freeRestrictions();

        assertTrue(testInv.isAllowed(blankItem));
        assertTrue(testInv.isAllowed(namedItem));
        assertTrue(testInv.isAllowed(wrongTag));
        assertTrue(testInv.isAllowed(notEnoughTags));
        assertTrue(testInv.isAllowed(exactMatch));
        assertTrue(testInv.isAllowed(addedTags));
        assertEquals(testInv, refInv);
    }

    /* Tests for Standard methods */

    /** Test for clear() with an empty inventory of size one. */
    public final void testClearEmptyOne() {

        Inventory testInv = this.constructor();
        Inventory expectedInv = this.constructor();

        testInv.clear();

        assertEquals(testInv, expectedInv);
    }

    /** Test for clear() with a filled inventory of size one. */
    public final void testClearFilledOne() {

        Inventory testInv = this.constructor("Foo");
        Inventory expectedInv = this.constructor();

        testInv.clear();

        assertEquals(testInv, expectedInv);
    }

    /** Test for clear() with an empty inventory of size ten. */
    public final void testClearEmptyTen() {

        final int invSize = 10;

        Inventory testInv = this.constructor(invSize);
        Inventory expectedInv = this.constructor();

        testInv.clear();

        assertEquals(testInv, expectedInv);
    }

    /** Test for clear() with a filled inventory of size ten. */
    public final void testClearFilledTen() {

        Inventory testInv = this.constructor("A", "B", "C", "D", "E", "F", "G",
                "H", "I", "J");
        Inventory expectedInv = this.constructor();

        testInv.clear();

        assertEquals(testInv, expectedInv);
    }

    /** Test for newInstance() from empty inventory. */
    public final void testNewInstanceEmpty() {

        Inventory refInv = this.constructor();
        Inventory expectedRefInv = this.constructor();

        Inventory newInv = refInv.newInstance();
        Inventory expectedNewInv = this.constructor();

        assertEquals(refInv, expectedRefInv);
        assertEquals(newInv, expectedNewInv);
    }

    /** Test for newInstance() from non-empty inventory. */
    public final void testNewInstanceFilled() {

        Inventory refInv = this.constructor("A", "B", "C", "D", "E", "F", "G",
                "H", "I", "J");
        Inventory expectedRefInv = this.constructor("A", "B", "C", "D", "E",
                "F", "G", "H", "I", "J");

        Inventory newInv = refInv.newInstance();
        Inventory expectedNewInv = this.constructor();

        assertEquals(refInv, expectedRefInv);
        assertEquals(newInv, expectedNewInv);
    }

    /** Test for transferFrom() with two empty inventories. */
    public final void testTransferFromEmptyToEmpty() {

        Inventory refInv = this.constructor();
        Inventory expectedRefInv = this.constructor();

        Inventory newInv = this.constructor();
        Inventory expectedNewInv = this.constructor();

        newInv.transferFrom(refInv);

        assertEquals(refInv, expectedRefInv);
        assertEquals(newInv, expectedNewInv);
    }

    /** Test for transferFrom() from filled to empty inventory. */
    public final void testTransferFromEmptyToFilled() {

        Inventory refInv = this.constructor();
        Inventory expectedRefInv = this.constructor();

        Inventory newInv = this.constructor("A", "B", "C", "D", "E", "F", "G",
                "H", "I", "J");
        Inventory expectedNewInv = this.constructor();

        newInv.transferFrom(refInv);

        assertEquals(refInv, expectedRefInv);
        assertEquals(newInv, expectedNewInv);
    }

    /** Test for transferFrom() with two empty inventories. */
    public final void testTransferFromFilledToEmpty() {

        Inventory refInv = this.constructor("A", "B", "C", "D", "E", "F", "G",
                "H", "I", "J");
        Inventory expectedRefInv = this.constructor();

        Inventory newInv = this.constructor();
        Inventory expectedNewInv = this.constructor("A", "B", "C", "D", "E",
                "F", "G", "H", "I", "J");

        newInv.transferFrom(refInv);

        assertEquals(refInv, expectedRefInv);
        assertEquals(newInv, expectedNewInv);
    }

    /** Test for transferFrom() with two empty inventories. */
    public final void testTransferFromFilledToFilled() {

        Inventory refInv = this.constructor("A", "B", "C", "D", "E", "F", "G",
                "H", "I", "J");
        Inventory expectedRefInv = this.constructor();

        Inventory newInv = this.constructor("X", "Y", "Z");
        Inventory expectedNewInv = this.constructor("A", "B", "C", "D", "E",
                "F", "G", "H", "I", "J");

        newInv.transferFrom(refInv);

        assertEquals(refInv, expectedRefInv);
        assertEquals(newInv, expectedNewInv);
    }

}
