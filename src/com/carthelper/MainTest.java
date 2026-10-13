package com.carthelper;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class MainTest {

    /**
     * Runs Main with simulated input and captures its output.
     */
    private String runMain(String input) throws Exception {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;

        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try {
            System.setIn(new ByteArrayInputStream(
                input.getBytes(StandardCharsets.UTF_8)));

            System.setOut(new PrintStream(output));

            Main.main(new String[0]);

            return output.toString(StandardCharsets.UTF_8);
        }
        finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
    }

    /**
     * Tests ending the program without adding any stores.
     */
    @Test
    public void testNoStores() throws Exception {
        String output = runMain("\n");

        assertTrue(output.contains("What store are you shopping at?"));
        assertFalse(output.contains("Store 1:"));
    }

    /**
     * Tests adding a store and purchasing an item.
     */
    @Test
    public void testAddStoreAndPurchaseItem() throws Exception {
        String input = "Target\nMilk\n\n\nMilk\n";

        String output = runMain(input);

        assertTrue(output.contains("Target"));
        assertTrue(output.contains("Milk"));
        assertTrue(output.contains("Target List"));
    }

    /**
     * Tests that duplicate stores are rejected.
     */
    @Test
    public void testDuplicateStore() throws Exception {
        String input = "Target\nMilk\n\nTarget\n\n\nMilk\n";

        String output = runMain(input);

        assertTrue(output.contains(
            "Target is already in your list of stores!"));
    }

    /**
     * Tests that adding the same item twice increases its count.
     */
    @Test
    public void testDuplicateItems() throws Exception {
        String input = "Target\nMilk\nMilk\n\n\nMilk\n";

        String output = runMain(input);

        assertTrue(output.contains("Milk"));
        assertTrue(output.contains("x2"));
    }

    /**
     * Tests entering an item that is not on the list.
     */
    @Test
    public void testInvalidPurchase() throws Exception {
        String input = "Target\nMilk\n\n\nBread\nMilk\n";

        String output = runMain(input);

        assertTrue(output.contains(
            "'Bread' is not on the list!"));
    }

    /**
     * Tests adding and visiting multiple stores in order.
     */
    @Test
    public void testMultipleStores() throws Exception {
        String input =
            "Target\nMilk\n\n"
            + "Walmart\nBread\n\n"
            + "\n\n"
            + "Milk\n"
            + "\n"
            + "Bread\n";

        String output = runMain(input);

        assertTrue(output.contains("Store 1:"));
        assertTrue(output.contains("Store 2:"));
        assertTrue(output.indexOf("Target") <
                   output.indexOf("Walmart"));
    }

    /**
     * Tests adding a store without any items.
     */
    @Test
    public void testStoreWithoutItems() throws Exception {
        String input = "Target\n\n\n";

        String output = runMain(input);

        assertTrue(output.contains("Target"));
        assertFalse(output.contains("(type the item you want to remove)"));
    }
}
