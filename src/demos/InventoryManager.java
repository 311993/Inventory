package demos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import components.inventory.Inventory;
import components.inventory.Inventory.Item;
import components.inventory.Inventory1;
import components.inventory.InventorySecondary.BasicItem;

/**
 * Demo of inventory component representing an inventory management system for a
 * store that allows items to be ordered from a catalog and placed on the
 * shelves, then sold or removed from the shelves.
 *
 * @author David Stuckey
 */
public final class InventoryManager {

    /** Private construcotr ot prevent instantiation. */
    private InventoryManager() {

    }

    /**
     * Creates the catalog of items available to the store.
     *
     * @return an Inventory containing each of the items available to the store.
     */
    private static Inventory generateCatalog() {

        final int catalogSize = 5;
        final int three = 3, four = 4;
        final int sandPrice = 5000, gravelPrice = 4000, cementPrice = 2000,
                shovelPrice = 2500, malletPrice = 1500;

        Inventory catalog = new Inventory1(catalogSize);

        Item sand = new BasicItem("Sand");
        sand.putTag("Material", 0);
        sand.putTag("GrainSize", 1);
        sand.putTag("Price", sandPrice);
        catalog.addItem(0, sand);

        Item gravel = new BasicItem("Gravel");
        gravel.putTag("Material", 0);
        gravel.putTag("GrainSize", 2);
        gravel.putTag("Price", gravelPrice);
        catalog.addItem(1, gravel);

        Item cement = new BasicItem("Cement");
        cement.putTag("Material", 0);
        cement.putTag("Price", cementPrice);
        catalog.addItem(2, cement);

        Item shovel = new BasicItem("Shovel");
        shovel.putTag("Tool", 0);
        shovel.putTag("Price", shovelPrice);
        catalog.addItem(three, shovel);

        Item mallet = new BasicItem("Mallet");
        mallet.putTag("Tool", 0);
        mallet.putTag("Price", malletPrice);
        catalog.addItem(four, mallet);

        return catalog;
    }

    /**
     * Gets an integer between 0 and maxInput from the user. Will request new
     * input until valid input has been given.
     *
     * @param in
     *            the BufferedReader to get the input from
     * @param maxInput
     *            the maximum acceptable input
     * @return an integer between 0 and maxInput
     */
    private static int getInputOption(BufferedReader in, int maxInput) {

        String input = null;
        boolean goodInput = false;

        System.out.print("Enter Command: ");

        while (!goodInput) {

            try {
                input = in.readLine();

                if (Integer.parseInt(input) < 0
                        || Integer.parseInt(input) > maxInput) {
                    throw new NumberFormatException();
                }

                goodInput = true;
            } catch (IOException e) {
                System.out.println("Invalid Input.");
                System.out.print("Enter Command: ");
            } catch (NumberFormatException e) {
                System.out.println("Invalid Input.");
                System.out.print("Enter Command: ");
            }

        }

        return Integer.parseInt(input);
    }

    /**
     * Get multiline string display for inventory {@code inv}. Format for each
     * item will be: <pre>
     *  #) name : count | tag1 (val1) | ...
     * </pre>Count and tags will be ommited if {@code namesOnly} is true.
     *
     * @param inv
     *            - the inventory to get a display for
     * @param namesOnly
     *            - whether or not to only include item names
     * @return a multiline string display of the inventory that can be directly
     *         written to the command line or a file
     */
    private static String getInventoryDisplay(Inventory inv,
            boolean namesOnly) {

        String display = "";
        Item temp;

        for (int i = 0; i < inv.size(); i++) {

            temp = inv.getItem(i);

            display += "\t" + i + ") " + temp.getName();

            if (!namesOnly) {

                display += " : " + temp.tagValue(Item.COUNT);

                for (String tag : temp.getTags().keySet()) {
                    if (tag != Item.COUNT) {

                        display += "\t| " + tag + "(" + temp.tagValue(tag)
                                + ")";

                    }
                }
            }

            display += "\n";
        }

        return display;
    }

    /**
     * The main method.
     *
     * @param args
     *            - the command line arguments
     */
    public static void main(String[] args) {

        final int shelfSize = 4, catalogSize = 5;

        Inventory catalog = generateCatalog(),
                shelf = new Inventory1(shelfSize), buffer = new Inventory1();

        boolean quitProgram = false;

        BufferedReader in = new BufferedReader(
                new InputStreamReader(System.in));

        System.out.println("********Inventory Manager********\n");

        while (!quitProgram) {

            System.out.println("Current Inventory:\n");
            System.out.println(getInventoryDisplay(shelf, false));

            System.out.println("\n Do what?: \n" + "\t0) Add Items to Shelf\n"
                    + "\t1) Sell Items off Shelf\n"
                    + "\t2) Remove Items from Shelf\n" + "\t3) Quit\n");

            final int numOperations = 4, add = 0, sell = 1, remove = 2;

            int op = getInputOption(in, numOperations);

            switch (op) {
                case add:

                    System.out.println("Select item to add from Catalog:\n");
                    System.out.println(getInventoryDisplay(catalog, true));

                    int catalogSlot = getInputOption(in, catalogSize);
                    String itemName = catalog.getItem(catalogSlot).getName();

                    buffer.clear();
                    buffer.copyItem(catalog, itemName, 0);

                    final int maxStack = 99;
                    System.out.println("How Many? (Max 99)?\n");

                    int numItems = getInputOption(in, maxStack);

                    int partialPos = shelf.nextPlacement(buffer.getItem(0),
                            maxStack);
                    buffer.getItem(0).putTag(Item.COUNT, numItems);
                    int fullPos = shelf.nextPlacement(buffer.getItem(0),
                            maxStack);

                    //Find valid placement if exists
                    if (fullPos >= 0) {
                        shelf.addItem(fullPos, buffer.removeItem(0));
                        System.out.println("Added all items requested.");

                    } else if (partialPos >= 0) {
                        shelf.getItem(partialPos).putTag(Item.COUNT, maxStack);
                        System.out.println(
                                "Added as many items as could be placed in a slot.");
                    } else {
                        System.out.println("Nowhere to place that.");
                    }

                    break;

                case sell:

                    System.out.println("Which slot to sell from?:\n");
                    System.out.println(getInventoryDisplay(shelf, false));

                    int shelfSlot = getInputOption(in, shelfSize);
                    System.out.println("Sell how Many?\n");

                    int numSell = getInputOption(in,
                            shelf.getItem(shelfSlot).tagValue(Item.COUNT));

                    buffer.clear();
                    buffer.splitItem(shelf, shelfSlot, 0, numSell);

                    final double centsToDollars = 100.0;
                    double price = 0.00;

                    if (!buffer.getItem(0).isEmpty()) {
                        price = (buffer.getItem(0).tagValue("Price") * numSell)
                                / centsToDollars;
                    }

                    System.out.println("Price is: $" + price);
                    System.out.println("Sell (Yes = 0, No = 1)");
                    int sellChoice = getInputOption(in, 2);

                    if (sellChoice == 0) {
                        buffer.clear();
                        System.out.println("Sold.");

                    } else {
                        shelf.addItem(shelfSlot, buffer.removeItem(shelfSlot));
                        System.out.println("Items returned to shelf.");
                    }

                    break;

                case remove:
                    System.out.println("Which slot to remove from?:\n");
                    System.out.println(getInventoryDisplay(shelf, false));

                    int shelfSlot2 = getInputOption(in, shelfSize);

                    System.out.println("Remove how Many?\n");

                    int numRemove = getInputOption(in,
                            shelf.getItem(shelfSlot2).tagValue(Item.COUNT));

                    buffer.clear();
                    buffer.splitItem(shelf, shelfSlot2, 0, numRemove);
                    buffer.clear();
                    System.out.println("Removed.");

                    break;

                default:
                    quitProgram = true;
                    break;
            }
        }

        try {
            in.close();
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}
