package com.carthelper;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class MainTest {

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
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
    }

    @Test
    public void testNoStores() throws Exception {
        String output = runMain("\n");

        assertTrue(output.contains("What store are you shopping at?"));
        assertFalse(output.contains("Store \u001b[1m1:"));
    }

    @Test
    public void testAddStoreAndPurchaseItem() throws Exception {
        // Store, item, end items, end stores, arrive, purchase item
        String output = runMain("Target\nMilk\n\n\n\nMilk\n");

        assertTrue(output.contains("Target"));
        assertTrue(output.contains("Milk"));
        assertTrue(output.contains("Target List"));
    }

    @Test
    public void testDuplicateStore() throws Exception {
        String input = "Target\nMilk\n\nTarget\n\n\n\nMilk\n";

        String output = runMain(input);

        assertTrue(output.contains(
            "Target is already in your list of stores!"));
    }

    @Test
    public void testDuplicateItems() throws Exception {
        String input = "Target\nMilk\nMilk\n\n\n\nMilk\n";

        String output = runMain(input);

        assertTrue(output.contains("Milk"));
        assertTrue(output.contains("x2"));
    }

    @Test
    public void testInvalidPurchase() throws Exception {
        String input = "Target\nMilk\n\n\n\nBread\nMilk\n";

        String output = runMain(input);

        assertTrue(output.contains("'Bread' is not on the list!"));
    }

    @Test
    public void testMultipleStores() throws Exception {
        String input =
            "Target\nMilk\n\n"
            + "Walmart\nBread\n\n"
            + "\n"
            + "\n"
            + "Milk\n"
            + "\n"
            + "Bread\n";

        String output = runMain(input);

        assertTrue(output.contains("Store \u001b[1m1:"));
        assertTrue(output.contains("Store \u001b[1m2:"));

        int targetIndex = output.indexOf("Head to \u001b[1;36mTarget");
        int walmartIndex = output.indexOf("Head to \u001b[1;36mWalmart");

        assertTrue(targetIndex >= 0);
        assertTrue(walmartIndex > targetIndex);
    }

    @Test
    public void testStoreWithoutItems() throws Exception {
        String input = "Target\n\n\n\n";

        String output = runMain(input);

        assertTrue(output.contains("Target"));
        assertFalse(output.contains("(type the item you want to remove)"));
    }
}