package demos;

import inventory.Inventory;
import inventory.Inventory1;
import inventory.InventorySecondary;

public class TerminalDemo {

    private TerminalDemo() {
    }

    public static void main(String[] args) {
        /******* Demo 1: Collating items from one Inventory to another. *******/
        System.out.println(
                "Demo 1: Collating items from one Inventory to another.\n");

        //Construct demo vars
        final int ten = 10;
        final int three = 3;
        Inventory inv1 = new Inventory1(ten);
        Inventory inv2 = new Inventory1(three);

        //Add items to slots of first inventory
        System.out.println("Items in Inventory 1:");

        String[] names = { "Dirt", "Sand", "Gravel" };

        for (int i = 0; i < inv1.size(); i++) {

            //Create Items with alternating names and random counts
            String name = names[i % three];

            Inventory.Item temp = new InventorySecondary.BasicItem(name,
                    (int) (Math.random() * ten) + 1);
            inv1.addItem(i, temp);

            //Display each added item in terminal
            System.out.printf("%s : %d, \n", inv1.getItem(i).getName(),
                    inv1.getItem(i).tagValue(Inventory.Item.COUNT));
        }

        //Send items to 2nd inventory
        for (int i = 0; i < inv1.size(); i++) {

            inv2.transferItem(inv1, i,
                    inv2.nextPlacement(inv1.getItem(i), ten * ten));
        }

        //Show results
        System.out.println();
        System.out.println("Items Sent to Inventory 2:");
        for (int i = 0; i < inv2.size(); i++) {
            System.out.printf("%s : %d\n", inv2.getItem(i).getName(),
                    inv2.getItem(i).tagValue(Inventory.Item.COUNT));
        }
        System.out.println();
        System.out.println("Items in Inventory 1:");
        for (int i = 0; i < inv1.size(); i++) {
            System.out.printf("%s : %d, \n", inv1.getItem(i).getName(),
                    inv1.getItem(i).tagValue(Inventory.Item.COUNT));
        }

        /******* Demo 2: Reference Inventory *******/
        System.out.println(
                "Demo 2: Sourcing Item Definitions from Reference Inventory\n");

        //Construct reference inventory
        Inventory.Item shovel = new InventorySecondary.BasicItem("Shovel");
        Inventory.Item mallet = new InventorySecondary.BasicItem("Mallet");
        Inventory.Item food = new InventorySecondary.BasicItem("Food");
        Inventory.Item medicine = new InventorySecondary.BasicItem("Medicine");

        shovel.putTag("tool", 0);
        mallet.putTag("tool", 0);
        food.putTag("consumable", 1);
        medicine.putTag("consumable", 2);

        Inventory reference = new Inventory1(ten);
        reference.addItem(0, medicine);
        reference.addItem(1, shovel);
        reference.addItem(2, food);
        reference.addItem(three, mallet);

        //Add items to in-use inventory based on those defined in reference inventory
        Inventory hotbar = new Inventory1(ten);
        hotbar.copyItem(reference, "Mallet", 0);
        hotbar.copyItem(reference, "Food", 1);
        hotbar.copyItem(reference, "Shovel", 2);
        hotbar.copyItem(reference, "Food", three);
        hotbar.copyItem(reference, "Medicine", hotbar.size() - 1);

        //Display what is in the in-use inventory
        System.out.println("Items in In-Use Inventory:\n");

        for (int i = 0; i < hotbar.size(); i++) {
            System.out.printf("Item in slot %d is %s.\n", i,
                    hotbar.getItem(i).getName());
            System.out.printf("\tIs a tool? : %b\n",
                    hotbar.getItem(i).hasTag("tool"));
            System.out.printf("\tIs consumable? : %b\n",
                    hotbar.getItem(i).hasTag("consumable"));
            System.out.println();
        }
    }
}
