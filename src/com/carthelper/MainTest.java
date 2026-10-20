package com.carthelper;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

// -------------------------------------------------------------------------
/**
 * Tests Main by typing input into it and checking what it prints. The first
 * tests cover the normal shopping trip, and the rest cover bad input: input
 * that ends early, names in different upper and lower case, blank entries, and
 * items that are not on the list.
 * 
 * @author shafiursarker
 * @version Oct 20, 2026
 */
public class MainTest
{

    /**
     * Runs Main with the given typed input and returns what it printed.
     *
     * @param input
     *            everything the user types, with \n for enter
     * @return the program's output
     */
    private String runMain(String input)
    {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try
        {
            System.setIn(
                new ByteArrayInputStream(
                    input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output));

            Main.main(new String[0]);

            return output.toString(StandardCharsets.UTF_8);
        }
        finally
        {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
    }


    /**
     * tests a normal trip with no bad input, so no error messages show up
     */
    @Test
    public void testNormalTripNoErrors()
    {
        String output = runMain("Target\nMilk\n\n\n\nMilk\n");

        assertFalse(output.contains("already in your list"));
        assertFalse(output.contains("is not on the list!"));
        assertFalse(output.contains("Please type an item name."));
        assertFalse(output.contains("No more input"));
    }


    /**
     * tests that the program closes instead of crashing when the input ends
     * while adding stores
     */
    @Test
    public void testInputEndsWhileAddingStores()
    {
        String output = runMain("");

        assertTrue(output.contains("No more input. Closing CartHelper."));
    }


    /**
     * tests that the program closes instead of crashing when the input ends
     * while adding items
     */
    @Test
    public void testInputEndsWhileAddingItems()
    {
        String output = runMain("Target\nMilk");

        assertTrue(output.contains("No more input. Closing CartHelper."));
    }


    /**
     * tests that the program closes instead of crashing when the input ends
     * before the user gets to the store
     */
    @Test
    public void testInputEndsBeforeStore()
    {
        String output = runMain("Target\nMilk\n\n\n");

        assertTrue(output.contains("Head to"));
        assertTrue(output.contains("No more input. Closing CartHelper."));
    }


    /**
     * tests that the program closes instead of crashing when the input ends
     * while checking items off
     */
    @Test
    public void testInputEndsWhileShopping()
    {
        String output = runMain("Target\nMilk\n\n\n\n");

        assertTrue(output.contains("Target List"));
        assertTrue(output.contains("No more input. Closing CartHelper."));
    }


    /**
     * tests that the same store in a different case is caught as a duplicate
     */
    @Test
    public void testDuplicateStoreDifferentCase()
    {
        String output = runMain("Target\nMilk\n\ntarget\n\n\nMilk\n");

        assertTrue(
            output.contains("target is already in your list of stores!"));
        assertFalse(output.contains("Store \u001b[1m2:"));
    }


    /**
     * tests that typing the same item in a different case adds to its count and
     * keeps the first name
     */
    @Test
    public void testDuplicateItemDifferentCase()
    {
        String output = runMain("Target\nMilk\nMILK\n\n\n\nmilk\n");

        assertTrue(output.contains("Milk  \u001b[3;90mx2"));
        assertFalse(output.contains("MILK  "));
    }


    /**
     * tests that an item can be checked off in a different case
     */
    @Test
    public void testPurchaseDifferentCase()
    {
        String output = runMain("Target\nMilk\n\n\n\nmilk\n");

        assertFalse(output.contains("is not on the list!"));
        assertTrue(output.contains("Milk\u001b[0m"));
    }


    /**
     * tests that a blank entry at checkout gets a message and asks again
     */
    @Test
    public void testBlankPurchase()
    {
        String output = runMain("Target\nMilk\n\n\n\n\nMilk\n");

        assertTrue(output.contains("Please type an item name."));
        assertFalse(output.contains("No more input"));
    }


    /**
     * tests that an item not on the list gets a message and asks again
     */
    @Test
    public void testItemNotOnList()
    {
        String output = runMain("Target\nMilk\n\n\n\nBread\nMilk\n");

        assertTrue(output.contains("'Bread' is not on the list!"));
        assertFalse(output.contains("No more input"));
    }


    /**
     * tests that a store name with only spaces counts as blank and ends store
     * entry
     */
    @Test
    public void testSpacesOnlyStoreName()
    {
        String output = runMain("   \n");

        assertFalse(output.contains("Store \u001b[1m1:"));
        assertFalse(output.contains("No more input"));
    }
}
