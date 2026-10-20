package com.carthelper;

import java.util.Scanner;

// -------------------------------------------------------------------------
/**
 * Runs CartHelper. The user types the stores in the order they will visit them
 * and the items for each store, then checks the items off one store at a time.
 * Bad input (blank names, duplicate stores, items not on the list, input that
 * ends early) is caught here without crashing.
 * 
 * @author grant, shafiursarker
 * @version Oct 20, 2026
 */
public class Main
{
    public static void main(String[] args)
    {

        final Scanner scanner = new Scanner(System.in);
        final LinkedChain<Store> stores = new LinkedChain<>();

        for (boolean once = true;; once = false)
        {
            if (once)
            {
                System.out.println(
                    "What store are you shopping at? \33[3;90m(press enter if you are done)\33[0m");
            }
            else
            {
                System.out.println(
                    "What store are you shopping at next? \33[3;90m(press enter if you are done adding stores)\33[0m");
            }

            // stop instead of crashing if the input ends early
            if (!scanner.hasNextLine())
            {
                System.out.println("\nNo more input. Closing CartHelper.");
                return;
            }
            final String store_name = scanner.nextLine().trim();
            if (store_name.isEmpty())
                break;

            if (storeExists(stores, store_name))
            {
                System.out.println(
                    store_name + " is already in your list of stores!");
                continue;
            }

            final Store store = new Store(store_name);
            stores.addToEnd(store);

            System.out.println(
                "Type the items you want to add to the list \33[3;90m(press enter if you are done)\33[0m");
            while (true)
            {
                System.out.print("\t\33[90m( )\33[0m  ");

                if (!scanner.hasNextLine())
                {
                    System.out.println("\nNo more input. Closing CartHelper.");
                    return;
                }
                final String item_name = scanner.nextLine().trim();
                if (item_name.isEmpty())
                    break;

                if(item_name.equals("undo") || item_name.equals("UNDO")){
                    System.out.println("undo and UNDO are not valid elements of list. Please input a valid element.");
                    continue;
                }
                // typing the same item again adds 1 to its count
                final Item existing = findItem(store.needed_items(), item_name);
                if (existing != null)
                {
                    existing.count++;
                    continue;
                }

                final Item item = new Item(item_name);
                item.count = 1;
                store.needed_items().addToEnd(item);
            }
        }

        for (int i = 0; i < stores.size(); i++)
        {
            final Store store = stores.get(i);

            System.out.println(
                "Store \33[1m" + (i + 1) + ":\33[0m Head to \33[1;36m"
                    + store.name()
                    + " \33[0;3;90m(press enter when you are there)\33[0m");
            if (!scanner.hasNextLine())
            {
                System.out.println("\nNo more input. Closing CartHelper.");
                return;
            }
            scanner.nextLine();

            while (true)
            {
                System.out.println("\33[1;36m" + store.name() + " List\33[0m");

                for (final Item item : store.needed_items()
                    .toArray(new Item[store.needed_items().size()]))
                {
                    System.out.println(
                        "\t( )  " + item.name + "  \33[3;90mx" + item.count
                            + "\33[0m");
                }

                for (final Item item : store.gotten_items()
                    .toArray(new Item[store.gotten_items().size()]))
                {
                    System.out.println(
                        "\t\33[1;31m(x)\33[0;90m  " + item.name + "\33[0m");
                }

                if (store.needed_items().isEmpty())
                    break;

                System.out.print("\nType the item you want to remove (type undo to revert last removal): ");
                while (true)
                {
                    if (!scanner.hasNextLine())
                    {
                        System.out
                            .println("\nNo more input. Closing CartHelper.");
                        return;
                    }
                    final String bought_name = scanner.nextLine().trim();
                    if (bought_name.isEmpty())
                    {
                        System.out.print("Please type an item name. ");
                        continue;
                    }

                    final Item bought_item =
                        findItem(store.needed_items(), bought_name);
                    
                    if (bought_item == null)
                    {
                        System.out.print(
                            "'" + bought_name + "' is not on the list! Type a valid item to remove: ");
                        continue;
                    }

                    if(bought_item.name.equals("undo"))
                    {
                        // System.out.println("We came here 1");
                        if(!store.gotten_items().isEmpty()){
                            // System.out.println("We came here 2");
                            Item rmItem = store.gotten_items().remove();
                            store.needed_items().add(rmItem); 
                            // System.out.println("You added back " + rmItem.name + " into your list. \nWhat Item would you like to remove?");
                        }
                            // } else {
                        //     System.out.println("You have not removed any elements yet! \nWhat Item would you like to remove?");
                        // }
                        break;
                    }
                    
                    store.needed_items().remove(bought_item);
                    store.gotten_items().add(bought_item);
                    // System.out.println("Line 149; gotten_items() is: " + store.gotten_items().toString());
                    break;
                }
            }
        }
    }


    /**
     * Checks if a store with this name is already on the trip, ignoring upper
     * and lower case, so "target" and "Target" are the same store.
     *
     * @param stores
     *            the stores on the trip
     * @param name
     *            the store name the user typed
     * @return true if the store is already in the chain
     */
    private static boolean storeExists(LinkedChain<Store> stores, String name)
    {
        for (final Store store : stores.toArray(new Store[stores.size()]))
        {
            if (store.name().equalsIgnoreCase(name))
            {
                return true;
            }
        }
        return false;
    }


    /**
     * Finds the item with this name in a list, ignoring upper and lower case,
     * so "milk" finds "Milk".
     *
     * @param items
     *            the list of items to look through
     * @param name
     *            the item name the user typed
     * @return the item that matches, or null if it is not on the list
     */
    private static Item findItem(LinkedChain<Item> items, String name)
    {
        for (final Item item : items.toArray(new Item[items.size()]))
        {
            if (item.name.equalsIgnoreCase(name))
            {
                return item;
            }
        }
        if(name.equals("undo") || name.equals("UNDO")){
            return new Item("undo");
        }
        return null;
    }

}
