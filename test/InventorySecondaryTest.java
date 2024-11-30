import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import inventory.Inventory;
import inventory.Inventory.Item;
import inventory.Inventory1;
import inventory.InventorySecondary.BasicItem;

/**
 * Test array for Inventory secondary methods using Inventory1 kernel
 * implementation.
 *
 * @author David Stuckey
 */
public class InventorySecondaryTest {

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

    /** Tests for getItem() */

    /** Test for getItem() with an empty item. */
    @Test
    public final void testGetItemEmpty() {

        Inventory testInv = this.constructor();
        Inventory refInv = this.constructor();

        Item expected = new BasicItem();
        Item result = testInv.getItem(0);

        assertEquals(expected, result);
        assertEquals(testInv, refInv);
    }

    /** Test for getItem() with a named item. */
    @Test
    public final void testGetItemNamed() {

        Inventory testInv = this.constructor("Foo");
        Inventory refInv = this.constructor("Foo");

        Item expected = new BasicItem("Foo");
        Item result = testInv.getItem(0);

        assertEquals(expected, result);
        assertEquals(testInv, refInv);
    }

    /** Test for getItem() with an item with tags. */
    @Test
    public final void testGetItemTagged() {

        Inventory testInv = this.constructor();
        Inventory refInv = this.constructor();

        Item testItem = new BasicItem("Foo", 2);
        testItem.putTag("TEST", 0);

        Item refItem = new BasicItem("Foo", 2);
        refItem.putTag("TEST", 0);

        testInv.addItem(0, testItem);
        refInv.addItem(0, refItem);

        Item result = testInv.getItem(0);

        assertEquals(result, refItem);
        assertTrue(result == testItem);
        assertEquals(testInv, refInv);
    }

    /* Tests for copyItem() */

    /** Test for copyItem() with an empty item. */
    @Test
    public final void testCopyItemEmpty() {

        Inventory destInv = this.constructor();
        Inventory destExpectedInv = this.constructor();
        Inventory srcInv = this.constructor();
        Inventory srcExpectedInv = this.constructor();

        destInv.copyItem(srcInv, Item.EMPTY_NAME, 0);

        assertEquals(destInv, destExpectedInv);
        assertEquals(srcInv, srcExpectedInv);
        assertFalse(srcInv.getItem(0) == destInv.getItem(0));
    }

    /** Test for copyItem() with a named item. */
    @Test
    public final void testCopyItemNamed() {

        Inventory destInv = this.constructor();
        Inventory destExpectedInv = this.constructor("Foo");
        Inventory srcInv = this.constructor("Foo", "Bar");
        Inventory srcExpectedInv = this.constructor("Foo", "Bar");

        destInv.copyItem(srcInv, "Foo", 0);

        assertEquals(destInv, destExpectedInv);
        assertEquals(srcInv, srcExpectedInv);
        assertFalse(srcInv.getItem(0) == destInv.getItem(0));
    }

    /** Test for copyItem() with a named item. */
    @Test
    public final void testCopyItemNamed2() {

        Inventory destInv = this.constructor();
        Inventory destExpectedInv = this.constructor("Bar");
        Inventory srcInv = this.constructor("Foo", "Bar");
        Inventory srcExpectedInv = this.constructor("Foo", "Bar");

        destInv.copyItem(srcInv, "Bar", 0);

        assertEquals(destInv, destExpectedInv);
        assertEquals(srcInv, srcExpectedInv);
        assertFalse(srcInv.getItem(1) == destInv.getItem(0));
    }

    /** Test for copyItem() with a named item with tags. */
    @Test
    public final void testCopyItemTagged() {

        Inventory destInv = this.constructor();
        Inventory destExpectedInv = this.constructor();
        Inventory srcInv = this.constructor();
        Inventory srcExpectedInv = this.constructor();

        Item srcItem = new BasicItem("Foo", 2);
        srcItem.putTag("TEST", 0);

        Item srcExpectedItem = new BasicItem("Foo", 2);
        srcExpectedItem.putTag("TEST", 0);

        Item destExpectedItem = new BasicItem("Foo", 2);
        destExpectedItem.putTag("TEST", 0);

        srcInv.addItem(0, srcItem);
        srcExpectedInv.addItem(0, srcExpectedItem);
        destExpectedInv.addItem(0, destExpectedItem);

        destInv.copyItem(srcInv, "Foo", 0);

        assertEquals(destInv, destExpectedInv);
        assertEquals(srcInv, srcExpectedInv);
        assertFalse(srcInv.getItem(0) == destInv.getItem(0));
    }

    /** Test for copyItem() with an inventory with items already in it. */
    @Test
    public final void testCopyItemFilled() {

        Inventory destInv = this.constructor("Lorem", Item.EMPTY_NAME);
        Inventory destExpectedInv = this.constructor("Lorem", "Foo");
        Inventory srcInv = this.constructor("Foo", "Bar");
        Inventory srcExpectedInv = this.constructor("Foo", "Bar");

        destInv.copyItem(srcInv, "Foo", 1);

        assertEquals(destInv, destExpectedInv);
        assertEquals(srcInv, srcExpectedInv);
        assertFalse(srcInv.getItem(0) == destInv.getItem(0));
    }

    /* Tests for swapItems() overloads */

    /** Test for swapItems() with empty item within same inventory. */
    @Test
    public final void testSwapItemsSameInvEmptyItems() {

        Inventory testInv = this.constructor(2);
        Inventory expectedInv = this.constructor(2);

        testInv.swapItems(0, 1);

        assertEquals(testInv, expectedInv);
    }

    /** Test for swapItems() with empty and named item within same inventory. */
    @Test
    public final void testSwapItemsSameInvOneNamedItem() {

        Inventory testInv = this.constructor("Foo", Item.EMPTY_NAME);
        Inventory expectedInv = this.constructor(Item.EMPTY_NAME, "Foo");

        testInv.swapItems(0, 1);

        assertEquals(testInv, expectedInv);
    }

    /** Test for swapItems() with named items within same inventory. */
    @Test
    public final void testSwapItemsSameInvNamedItems() {

        Inventory testInv = this.constructor("Foo", "Bar");
        Inventory expectedInv = this.constructor("Bar", "Foo");

        testInv.swapItems(0, 1);

        assertEquals(testInv, expectedInv);
    }

    /** Test for swapItems() with tagged items within same inventory. */
    @Test
    public final void testSwapItemsSameInvTaggedItems() {

        Inventory testInv = this.constructor(2);
        Inventory expectedInv = this.constructor(2);

        Item testItem1 = new BasicItem("Foo", 2);
        testItem1.putTag("Tag", 0);
        testInv.addItem(0, testItem1);

        Item testItem2 = new BasicItem("Bar", 2);
        testItem2.putTag("Test", 0);
        testInv.addItem(1, testItem2);

        Item expectedItem1 = new BasicItem("Foo", 2);
        expectedItem1.putTag("Tag", 0);
        expectedInv.addItem(1, expectedItem1);

        Item expectedItem2 = new BasicItem("Bar", 2);
        expectedItem2.putTag("Test", 0);
        expectedInv.addItem(0, expectedItem2);

        testInv.swapItems(0, 1);

        assertEquals(testInv, expectedInv);
    }

    /** Test for swapItems() with empty items between inventories. */
    @Test
    public final void testSwapItemsTwoInvsEmptyItems() {

        Inventory destInv = this.constructor();
        Inventory destExpectedInv = this.constructor();
        Inventory srcInv = this.constructor();
        Inventory srcExpectedInv = this.constructor();

        destInv.swapItems(srcInv, 0, 0);

        assertEquals(destInv, destExpectedInv);
        assertEquals(srcInv, srcExpectedInv);
    }

    /** Test for swapItems() with empty and named item between inventories. */
    @Test
    public final void testSwapItemsTwoInvsOneNamedItem() {

        Inventory destInv = this.constructor("Foo");
        Inventory destExpectedInv = this.constructor(Item.EMPTY_NAME);
        Inventory srcInv = this.constructor(Item.EMPTY_NAME);
        Inventory srcExpectedInv = this.constructor("Foo");

        destInv.swapItems(srcInv, 0, 0);

        assertEquals(destInv, destExpectedInv);
        assertEquals(srcInv, srcExpectedInv);
    }

    /** Test for swapItems() with empty items between inventories. */
    @Test
    public final void testSwapItemsTwoInvsNamedItems() {

        Inventory destInv = this.constructor("Foo");
        Inventory destExpectedInv = this.constructor("Bar");
        Inventory srcInv = this.constructor("Bar");
        Inventory srcExpectedInv = this.constructor("Foo");

        destInv.swapItems(srcInv, 0, 0);

        assertEquals(destInv, destExpectedInv);
        assertEquals(srcInv, srcExpectedInv);
    }

    /** Test for swapItems() with tagged items between inventories. */
    @Test
    public final void testSwapItemsTwoInvsTaggedItems() {

        Inventory destInv = this.constructor();
        Inventory destExpectedInv = this.constructor();
        Inventory srcInv = this.constructor();
        Inventory srcExpectedInv = this.constructor();

        Item destItem = new BasicItem("Foo", 2);
        destItem.putTag("Tag", 0);
        destInv.addItem(0, destItem);

        Item srcItem = new BasicItem("Bar", 2);
        srcItem.putTag("Test", 0);
        srcInv.addItem(0, srcItem);

        Item srcExpectedItem = new BasicItem("Foo", 2);
        srcExpectedItem.putTag("Tag", 0);
        srcExpectedInv.addItem(0, srcExpectedItem);

        Item destExpectedItem = new BasicItem("Bar", 2);
        destExpectedItem.putTag("Test", 0);
        destExpectedInv.addItem(0, destExpectedItem);

        destInv.swapItems(srcInv, 0, 0);

        assertEquals(destInv, destExpectedInv);
        assertEquals(srcInv, srcExpectedInv);
    }

    /* Tests for transferItem() */

    /** Test for transferItem with empty item. */
    @Test
    public final void testTransferItemEmpty() {
        Inventory destInv = this.constructor();
        Inventory destExpectedInv = this.constructor();
        Inventory srcInv = this.constructor();
        Inventory srcExpectedInv = this.constructor();

        destInv.transferItem(srcInv, 0, 0);

        assertEquals(destInv, destExpectedInv);
        assertEquals(srcInv, srcExpectedInv);
    }

    /** Test for transferItem with named item. */
    @Test
    public final void testTransferItemNamed() {
        Inventory destInv = this.constructor("Foo", Item.EMPTY_NAME);
        Inventory destExpectedInv = this.constructor("Foo", "Bar");
        Inventory srcInv = this.constructor("Bar", "Lorem");
        Inventory srcExpectedInv = this.constructor(Item.EMPTY_NAME, "Lorem");

        destInv.transferItem(srcInv, 0, 1);

        assertEquals(destInv, destExpectedInv);
        assertEquals(srcInv, srcExpectedInv);
    }

    /** Test for transferItem with tagged item. */
    @Test
    public final void testTransferItemTagged() {
        Inventory destInv = this.constructor();
        Inventory destExpectedInv = this.constructor();
        Inventory srcInv = this.constructor();
        Inventory srcExpectedInv = this.constructor();

        Item srcItem = new BasicItem("Bar", 2);
        srcItem.putTag("Test", 0);
        srcInv.addItem(0, srcItem);

        Item destExpectedItem = new BasicItem("Bar", 2);
        destExpectedItem.putTag("Test", 0);
        destExpectedInv.addItem(0, destExpectedItem);

        destInv.transferItem(srcInv, 0, 0);

        assertEquals(destInv, destExpectedInv);
        assertEquals(srcInv, srcExpectedInv);
    }

    /* Tests for splitItem() */

    /** Test for splitItem with named item. */
    @Test
    public final void testSplitItemNamed() {
        Inventory destInv = this.constructor();
        Inventory destExpectedInv = this.constructor();
        Inventory srcInv = this.constructor();
        Inventory srcExpectedInv = this.constructor();

        final int numTotal = 13;
        final int numToSplit = 7;

        Item testItem = new BasicItem("Foo", numTotal);
        srcInv.addItem(0, testItem);

        Item destExpectedItem = new BasicItem("Foo", numToSplit);
        destExpectedInv.addItem(0, destExpectedItem);

        Item srcExpectedItem = new BasicItem("Foo", numTotal - numToSplit);
        srcExpectedInv.addItem(0, srcExpectedItem);

        destInv.splitItem(srcInv, 0, 0, numToSplit);

        assertEquals(destInv, destExpectedInv);
        assertEquals(srcInv, srcExpectedInv);
    }

    /** Test for splitItem with tagged item. */
    @Test
    public final void testSplitItemTagged() {
        Inventory destInv = this.constructor();
        Inventory destExpectedInv = this.constructor();
        Inventory srcInv = this.constructor();
        Inventory srcExpectedInv = this.constructor();

        final int numTotal = 55;
        final int numToSplit = 38;

        Item testItem = new BasicItem("Foo", numTotal);
        testItem.putTag("TEST", 0);
        srcInv.addItem(0, testItem);

        Item destExpectedItem = new BasicItem("Foo", numToSplit);
        destExpectedItem.putTag("TEST", 0);
        destExpectedInv.addItem(0, destExpectedItem);

        Item srcExpectedItem = new BasicItem("Foo", numTotal - numToSplit);
        srcExpectedItem.putTag("TEST", 0);
        srcExpectedInv.addItem(0, srcExpectedItem);

        destInv.splitItem(srcInv, 0, 0, numToSplit);

        assertEquals(destInv, destExpectedInv);
        assertEquals(srcInv, srcExpectedInv);
    }

    /** Test for splitItem with splitting all items. */
    @Test
    public final void testSplitItemFull() {
        Inventory destInv = this.constructor();
        Inventory destExpectedInv = this.constructor();
        Inventory srcInv = this.constructor();
        Inventory srcExpectedInv = this.constructor();

        final int numTotal = 400;
        final int numToSplit = 400;

        Item testItem = new BasicItem("Foo", numTotal);
        srcInv.addItem(0, testItem);

        Item destExpectedItem = new BasicItem("Foo", numToSplit);
        destExpectedInv.addItem(0, destExpectedItem);

        Item srcExpectedItem = new BasicItem();
        srcExpectedInv.addItem(0, srcExpectedItem);

        destInv.splitItem(srcInv, 0, 0, numToSplit);

        assertEquals(destInv, destExpectedInv);
        assertEquals(srcInv, srcExpectedInv);
    }

    /** Test for splitItem with splitting 0 items. */
    @Test
    public final void testSplitItemNone() {
        Inventory destInv = this.constructor();
        Inventory destExpectedInv = this.constructor();
        Inventory srcInv = this.constructor();
        Inventory srcExpectedInv = this.constructor();

        final int numTotal = 34;
        final int numToSplit = 0;

        Item testItem = new BasicItem("Foo", numTotal);
        srcInv.addItem(0, testItem);

        Item destExpectedItem = new BasicItem();
        destExpectedInv.addItem(0, destExpectedItem);

        Item srcExpectedItem = new BasicItem("Foo", numTotal);
        srcExpectedInv.addItem(0, srcExpectedItem);

        destInv.splitItem(srcInv, 0, 0, numToSplit);

        assertEquals(destInv, destExpectedInv);
        assertEquals(srcInv, srcExpectedInv);
    }

    /* Tests for nextPlacement */

    /** Test for nextPlacement with only empty slots. */
    @Test
    public final void testNextPlacementEmpty() {

        final int sizeInv = 10;

        Inventory testInv = this.constructor(sizeInv);
        Inventory refInv = this.constructor(sizeInv);

        Item testItem = new BasicItem("Foo", 2);

        assertEquals(testInv.nextPlacement(testItem, 0), 0);
        assertEquals(testInv, refInv);
    }

    /** Test for nextPlacement with one empty slot. */
    @Test
    public final void testNextPlacementFindEmpty() {

        Inventory testInv = this.constructor("A", "A", Item.EMPTY_NAME, "A",
                "A", "A");
        Inventory refInv = this.constructor("A", "A", Item.EMPTY_NAME, "A", "A",
                "A");

        Item testItem = new BasicItem("Foo", 2);

        assertEquals(testInv.nextPlacement(testItem, 0), 2);
        assertEquals(testInv, refInv);
    }

    /** Test for nextPlacement with one slot to stack. */
    @Test
    public final void testNextPlacementFindStack() {

        Inventory testInv = this.constructor("A", "A", "B", "A", "A", "A");
        Inventory refInv = this.constructor("A", "A", "B", "A", "A", "A");

        Item testItem = new BasicItem("B", 2);

        assertEquals(testInv.nextPlacement(testItem, 0), 2);
        assertEquals(testInv, refInv);
    }

    /** Test for nextPlacement with no valid slots. */
    @Test
    public final void testNextPlacementNoValid() {

        Inventory testInv = this.constructor("A", "A", "A", "A", "A");
        Inventory refInv = this.constructor("A", "A", "A", "A", "A");

        Item testItem = new BasicItem("B", 2);

        assertEquals(testInv.nextPlacement(testItem, 0), -1);
        assertEquals(testInv, refInv);
    }

    /** Test for nextPlacement with only a stack position over the max stack. */
    @Test
    public final void testNextPlacementFullStack() {

        Inventory testInv = this.constructor("A", "A", Item.EMPTY_NAME, "A",
                "A", "A");
        Inventory refInv = this.constructor("A", "A", Item.EMPTY_NAME, "A", "A",
                "A");

        Item stackItem = new BasicItem("B", 2);
        testInv.addItem(2, stackItem);
        Item refItem = new BasicItem("B", 2);
        refInv.addItem(2, refItem);

        Item testItem = new BasicItem("B", 2);

        assertEquals(testInv.nextPlacement(testItem, 2), -1);
        assertEquals(testInv, refInv);
    }

    /* Tests for useItem() */

    /** Test for useItem with an empty item. */
    @Test
    public final void testUseItemEmpty() {
        Inventory testInv = this.constructor();
        Inventory refInv = this.constructor();

        String name = testInv.useItem(0);

        assertEquals(testInv, refInv);
        assertEquals(name, Item.EMPTY_NAME);
    }

    /** Test for useItem with a named item. */
    @Test
    public final void testUseItemNamed() {
        Inventory testInv = this.constructor();
        Inventory refInv = this.constructor();

        Item testItem = new BasicItem("Foo", 2);
        testInv.addItem(0, testItem);
        Item refItem = new BasicItem("Foo", 1);
        refInv.addItem(0, refItem);

        String name = testInv.useItem(0);

        assertEquals(testInv, refInv);
        assertEquals(name, "Foo");
    }

    /** Test for useItem with the last item in a slot. */
    @Test
    public final void testUseItemLast() {
        Inventory testInv = this.constructor();
        Inventory refInv = this.constructor();

        Item testItem = new BasicItem("Foo", 1);
        testInv.addItem(0, testItem);

        String name = testInv.useItem(0);

        assertEquals(testInv, refInv);
        assertEquals(name, "Foo");
    }

    /** Tests for isAt(). */
    @Test
    public final void testIsAt() {
        Inventory testInv = this.constructor(Item.EMPTY_NAME, "Foo", "Bar",
                Item.EMPTY_NAME);
        Inventory refInv = this.constructor(Item.EMPTY_NAME, "Foo", "Bar",
                Item.EMPTY_NAME);

        Item testItem = new BasicItem("Foo", 2);
        testItem.putTag("TEST", 0);
        testInv.addItem(testInv.size() - 1, testItem);
        Item refItem = new BasicItem("Foo", 2);
        refItem.putTag("TEST", 0);
        refInv.addItem(refInv.size() - 1, refItem);

        assertFalse(testInv.isAt(0, "Foo"));
        assertTrue(testInv.isAt(1, "Foo"));
        assertFalse(testInv.isAt(2, "Foo"));
        assertTrue(testInv.isAt(testInv.size() - 1, "Foo"));
        assertEquals(testInv, refInv);
    }
}
