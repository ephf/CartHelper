package com.carthelper;

import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

// -------------------------------------------------------------------------
/**
 * Tests the Validation class. Each public method has at least one normal case
 * and one bad-input case.
 * 
 * @author shafiursarker
 * @version Oct 20, 2026
 */
public class ValidationTest
{
    private PrintStream originalOut;
    private ByteArrayOutputStream output;

    /**
     * starts saving what gets printed, so tests can check the messages
     */
    private void startCapture()
    {
        originalOut = System.out;
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
    }


    /**
     * stops saving what gets printed and returns it
     *
     * @return everything printed since startCapture()
     */
    private String stopCapture()
    {
        System.setOut(originalOut);
        return output.toString();
    }


    /**
     * tests that readLine() returns the line without extra spaces
     */
    @Test
    public void testReadLine()
    {
        Scanner scanner = new Scanner("  Target  \nMilk\n");

        assertEquals("Target", Validation.readLine(scanner));
        assertEquals("Milk", Validation.readLine(scanner));
    }


    /**
     * tests that readLine() returns null instead of crashing when there is no
     * more input
     */
    @Test
    public void testReadLineNoInput()
    {
        Scanner scanner = new Scanner("");

        assertNull(Validation.readLine(scanner));
    }


    /**
     * tests isBlank() with real names
     */
    @Test
    public void testIsBlankFalse()
    {
        assertFalse(Validation.isBlank("Milk"));
        assertFalse(Validation.isBlank("  Milk  "));
    }


    /**
     * tests isBlank() with null, empty, and spaces-only text
     */
    @Test
    public void testIsBlankTrue()
    {
        assertTrue(Validation.isBlank(null));
        assertTrue(Validation.isBlank(""));
        assertTrue(Validation.isBlank("    "));
    }


    /**
     * tests that requireName() returns the name without extra spaces
     */
    @Test
    public void testRequireName()
    {
        assertEquals("Target", Validation.requireName("  Target ", "Store"));
    }


    /**
     * tests that requireName() rejects blank names
     */
    @Test
    public void testRequireNameBlank()
    {
        assertThrows(
            IllegalArgumentException.class,
            () -> Validation.requireName("", "Item"));
        assertThrows(
            IllegalArgumentException.class,
            () -> Validation.requireName("   ", "Item"));
        assertThrows(
            IllegalArgumentException.class,
            () -> Validation.requireName(null, "Item"));
    }


    /**
     * tests that sameName() ignores upper and lower case
     */
    @Test
    public void testSameName()
    {
        assertTrue(Validation.sameName("Milk", "milk"));
        assertTrue(Validation.sameName("Milk", "  MILK "));
    }


    /**
     * tests sameName() with different names and with null
     */
    @Test
    public void testSameNameDifferent()
    {
        assertFalse(Validation.sameName("Milk", "Bread"));
        assertFalse(Validation.sameName(null, "Milk"));
        assertFalse(Validation.sameName("Milk", null));
    }


    /**
     * tests that storeExists() finds a store in any case
     */
    @Test
    public void testStoreExists()
    {
        LinkedChain<Store> stores = new LinkedChain<Store>();
        stores.addToEnd(new Store("Target"));
        stores.addToEnd(new Store("Walmart"));

        assertTrue(Validation.storeExists(stores, "Target"));
        assertTrue(Validation.storeExists(stores, "walmart"));
    }


    /**
     * tests storeExists() with a missing store, an empty trip, and null
     */
    @Test
    public void testStoreExistsMissing()
    {
        LinkedChain<Store> stores = new LinkedChain<Store>();
        assertFalse(Validation.storeExists(stores, "Target"));

        stores.addToEnd(new Store("Target"));
        assertFalse(Validation.storeExists(stores, "Costco"));
        assertFalse(Validation.storeExists(stores, null));
    }


    /**
     * tests that findItem() returns the same item that is on the list, in any
     * case
     */
    @Test
    public void testFindItem()
    {
        LinkedChain<Item> items = new LinkedChain<Item>();
        Item milk = new Item("Milk");
        items.addToEnd(milk);

        assertSame(milk, Validation.findItem(items, "Milk"));
        assertSame(milk, Validation.findItem(items, "mILK"));
    }


    /**
     * tests findItem() with a missing item, an empty list, and null
     */
    @Test
    public void testFindItemMissing()
    {
        LinkedChain<Item> items = new LinkedChain<Item>();
        assertNull(Validation.findItem(items, "Milk"));

        items.addToEnd(new Item("Milk"));
        assertNull(Validation.findItem(items, "Bread"));
        assertNull(Validation.findItem(items, null));
    }


    /**
     * tests that readStoreName() returns a new store name
     */
    @Test
    public void testReadStoreName()
    {
        LinkedChain<Store> stores = new LinkedChain<Store>();
        Scanner scanner = new Scanner("  Target  \n");

        startCapture();
        String name = Validation.readStoreName(scanner, stores, "next?");
        String printed = stopCapture();

        assertEquals("Target", name);
        assertEquals("", printed);
    }


    /**
     * tests that readStoreName() rejects a duplicate store in any case, asks
     * again, and returns "" for a blank line
     */
    @Test
    public void testReadStoreNameDuplicateAndBlank()
    {
        LinkedChain<Store> stores = new LinkedChain<Store>();
        stores.addToEnd(new Store("Target"));
        Scanner scanner = new Scanner("target\nWalmart\n\n");

        startCapture();
        String first = Validation.readStoreName(scanner, stores, "next?");
        String second = Validation.readStoreName(scanner, stores, "next?");
        String printed = stopCapture();

        assertEquals("Walmart", first);
        assertEquals("", second);
        assertTrue(
            printed.contains("target is already in your list of stores!"));
        assertTrue(printed.contains("next?"));
    }


    /**
     * tests that readStoreName() returns null when the input ends
     */
    @Test
    public void testReadStoreNameInputEnded()
    {
        startCapture();
        String name = Validation
            .readStoreName(new Scanner(""), new LinkedChain<Store>(), "next?");
        String printed = stopCapture();

        assertNull(name);
        assertTrue(printed.contains("No more input"));
    }


    /**
     * tests that readItemName() returns the item name without spaces
     */
    @Test
    public void testReadItemName()
    {
        assertEquals("Milk", Validation.readItemName(new Scanner(" Milk \n")));
    }


    /**
     * tests readItemName() with a blank line and with no input
     */
    @Test
    public void testReadItemNameBlankAndInputEnded()
    {
        assertEquals("", Validation.readItemName(new Scanner("   \n")));

        startCapture();
        String name = Validation.readItemName(new Scanner(""));
        String printed = stopCapture();

        assertNull(name);
        assertTrue(printed.contains("No more input"));
    }


    /**
     * tests that waitForEnter() returns true when the user presses enter
     */
    @Test
    public void testWaitForEnter()
    {
        assertTrue(Validation.waitForEnter(new Scanner("\n")));
    }


    /**
     * tests that waitForEnter() returns false when the input ends
     */
    @Test
    public void testWaitForEnterInputEnded()
    {
        startCapture();
        boolean result = Validation.waitForEnter(new Scanner(""));
        String printed = stopCapture();

        assertFalse(result);
        assertTrue(printed.contains("No more input"));
    }


    /**
     * tests that readItemToBuy() returns the item from the list, in any case
     */
    @Test
    public void testReadItemToBuy()
    {
        LinkedChain<Item> needed = new LinkedChain<Item>();
        Item milk = new Item("Milk");
        needed.addToEnd(milk);

        assertSame(
            milk,
            Validation.readItemToBuy(new Scanner("milk\n"), needed));
    }


    /**
     * tests that readItemToBuy() asks again after a blank entry and an item
     * that is not on the list
     */
    @Test
    public void testReadItemToBuyBadInput()
    {
        LinkedChain<Item> needed = new LinkedChain<Item>();
        Item milk = new Item("Milk");
        needed.addToEnd(milk);

        startCapture();
        Item item =
            Validation.readItemToBuy(new Scanner("\nBread\nMilk\n"), needed);
        String printed = stopCapture();

        assertSame(milk, item);
        assertTrue(printed.contains("Please type an item name."));
        assertTrue(printed.contains("'Bread' is not on the list!"));
    }


    /**
     * tests that readItemToBuy() returns null when the input ends
     */
    @Test
    public void testReadItemToBuyInputEnded()
    {
        LinkedChain<Item> needed = new LinkedChain<Item>();
        needed.addToEnd(new Item("Milk"));

        startCapture();
        Item item = Validation.readItemToBuy(new Scanner("Bread\n"), needed);
        String printed = stopCapture();

        assertNull(item);
        assertTrue(printed.contains("No more input"));
    }
}
