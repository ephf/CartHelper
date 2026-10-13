package com.carthelper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class StoreTest {

    /**
     * Tests the constructor that accepts only a store name.
     */
    @Test
    public void testConstructor() {
        Store store = new Store("Target");

        assertEquals("Target", store.name());
        assertNotNull(store.needed_items());
        assertNotNull(store.gotten_items());

        assertTrue(store.needed_items().isEmpty());
        assertTrue(store.gotten_items().isEmpty());
    }

    /**
     * Tests that different stores have separate lists.
     */
    @Test
    public void testSeparateLists() {
        Store store1 = new Store("Target");
        Store store2 = new Store("Walmart");

        store1.needed_items().addToEnd(new Item("Milk"));

        assertEquals(1, store1.needed_items().size());
        assertTrue(store2.needed_items().isEmpty());
    }

    /**
     * Tests that stores with the same name are equal.
     */
    @Test
    public void testEqualsSameName() {
        Store store1 = new Store("Target");
        Store store2 = new Store("Target");

        assertEquals(store1, store2);
    }

    /**
     * Tests that stores with different names are not equal.
     */
    @Test
    public void testEqualsDifferentName() {
        Store store1 = new Store("Target");
        Store store2 = new Store("Walmart");

        assertNotEquals(store1, store2);
    }

    /**
     * Tests that a Store is equal to itself.
     */
    @Test
    public void testEqualsSameObject() {
        Store store = new Store("Target");

        assertEquals(store, store);
    }

    /**
     * Tests that a Store is not equal to null or an unrelated object.
     */
    @Test
    public void testEqualsDifferentTypes() {
        Store store = new Store("Target");

        assertNotEquals(null, store);
        assertNotEquals("Target", store);
    }

    /**
     * Tests that equals compares names, not the contents of the lists.
     */
    @Test
    public void testEqualsIgnoresLists() {
        Store store1 = new Store("Target");
        Store store2 = new Store("Target");

        store1.needed_items().addToEnd(new Item("Milk"));

        assertEquals(store1, store2);
    }
}
