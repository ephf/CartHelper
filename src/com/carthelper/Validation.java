package com.carthelper;

import java.util.Scanner;

// -------------------------------------------------------------------------
/**
 * Validation keeps all of CartHelper's input checks in one place. Main asks
 * this class before it trusts anything the user types, and Item and Store use
 * it to make sure a name is never blank.
 * 
 * @author shafiursarker
 * @version Oct 20, 2026
 */
public class Validation
{
    /**
     * Printed when the user runs out of input (for example Ctrl-D).
     */
    public static final String INPUT_ENDED =
        "\nNo more input. Closing CartHelper.";

    // ----------------------------------------------------------
    /**
     * Reads the next line the user types and removes extra spaces. It checks
     * hasNextLine() first, so the program does not crash when the input ends
     * early.
     *
     * @param scanner
     *            the scanner to read from
     * @return the trimmed line, or null if there is no more input
     */
    public static String readLine(Scanner scanner)
    {
        if (!scanner.hasNextLine())
        {
            return null;
        }
        String line = scanner.nextLine();
        return line.trim();
    }


    // ----------------------------------------------------------
    /**
     * Asks for a store name until the user types a store that is not already on
     * the trip, or presses enter to stop.
     *
     * @param scanner
     *            the scanner to read from
     * @param stores
     *            the stores already on the trip
     * @param retryPrompt
     *            what to print before asking again after a duplicate store
     * @return the new store name, "" if the user is done adding stores, or null
     *             if the input ended
     */
    public static String readStoreName(
        Scanner scanner,
        LinkedChain<Store> stores,
        String retryPrompt)
    {
        while (true)
        {
            String name = readLine(scanner);
            if (name == null)
            {
                System.out.println(INPUT_ENDED);
                return null;
            }
            if (isBlank(name))
            {
                return "";
            }
            if (!storeExists(stores, name))
            {
                return name;
            }

            System.out.println(name + " is already in your list of stores!");
            System.out.println(retryPrompt);
        }
    }


    // ----------------------------------------------------------
    /**
     * Asks for one item name to add to a store's list.
     *
     * @param scanner
     *            the scanner to read from
     * @return the item name, "" if the user is done adding items, or null if
     *             the input ended
     */
    public static String readItemName(Scanner scanner)
    {
        String name = readLine(scanner);
        if (name == null)
        {
            System.out.println(INPUT_ENDED);
            return null;
        }
        if (isBlank(name))
        {
            return "";
        }
        return name;
    }


    // ----------------------------------------------------------
    /**
     * Waits for the user to press enter when they get to a store.
     *
     * @param scanner
     *            the scanner to read from
     * @return true if the user pressed enter, or false if the input ended
     */
    public static boolean waitForEnter(Scanner scanner)
    {
        String line = readLine(scanner);
        if (line == null)
        {
            System.out.println(INPUT_ENDED);
            return false;
        }
        return true;
    }


    // ----------------------------------------------------------
    /**
     * Asks which item the user bought until they type an item that is on the
     * list. Blank entries and items not on the list get a message and the user
     * is asked again.
     *
     * @param scanner
     *            the scanner to read from
     * @param needed
     *            the items still needed at this store
     * @return the item from the list that matches, or null if the input ended
     */
    public static Item readItemToBuy(Scanner scanner, LinkedChain<Item> needed)
    {
        while (true)
        {
            String name = readLine(scanner);
            if (name == null)
            {
                System.out.println(INPUT_ENDED);
                return null;
            }

            if (isBlank(name))
            {
                System.out.print("Please type an item name. ");
            }
            else
            {
                Item item = findItem(needed, name);
                if (item != null)
                {
                    return item;
                }
                System.out.print("'" + name + "' is not on the list! ");
            }
        }
    }


    // ----------------------------------------------------------
    /**
     * Checks if some text is missing or only spaces.
     *
     * @param text
     *            the text to check
     * @return true if text is null, empty, or only spaces
     */
    public static boolean isBlank(String text)
    {
        if (text == null)
        {
            return true;
        }
        if (text.trim().isEmpty())
        {
            return true;
        }
        return false;
    }


    // ----------------------------------------------------------
    /**
     * Makes sure a name is not blank and returns it without extra spaces.
     *
     * @param name
     *            the name to check
     * @param type
     *            what the name is for, like "Item" or "Store"
     * @return the trimmed name
     * @throws IllegalArgumentException
     *             if the name is blank
     */
    public static String requireName(String name, String type)
    {
        if (isBlank(name))
        {
            throw new IllegalArgumentException(
                type + " name must not be blank");
        }
        return name.trim();
    }


    // ----------------------------------------------------------
    /**
     * Checks if two names are the same, ignoring upper and lower case, so
     * "Milk" and "milk" count as the same name.
     *
     * @param first
     *            the first name
     * @param second
     *            the second name
     * @return true if both names match, false if either one is null
     */
    public static boolean sameName(String first, String second)
    {
        if (first == null || second == null)
        {
            return false;
        }
        return first.trim().equalsIgnoreCase(second.trim());
    }


    // ----------------------------------------------------------
    /**
     * Checks if a store with this name is already on the trip.
     *
     * @param stores
     *            the stores on the trip
     * @param name
     *            the store name the user typed
     * @return true if the store is already in the chain
     */
    public static boolean storeExists(LinkedChain<Store> stores, String name)
    {
        Store[] storeArray = stores.toArray(new Store[stores.size()]);
        for (int i = 0; i < storeArray.length; i++)
        {
            if (sameName(storeArray[i].name(), name))
            {
                return true;
            }
        }
        return false;
    }


    // ----------------------------------------------------------
    /**
     * Finds the item with this name in a list, ignoring upper and lower case.
     *
     * @param items
     *            the list of items to look through
     * @param name
     *            the item name the user typed
     * @return the item that matches, or null if it is not on the list
     */
    public static Item findItem(LinkedChain<Item> items, String name)
    {
        Item[] itemArray = items.toArray(new Item[items.size()]);
        for (int i = 0; i < itemArray.length; i++)
        {
            if (sameName(itemArray[i].name, name))
            {
                return itemArray[i];
            }
        }
        return null;
    }
}
