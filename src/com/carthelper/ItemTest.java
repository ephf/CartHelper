package com.carthelper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// -------------------------------------------------------------------------
/**
 * tests the item class
 * 
 * @author shafiursarker
 * @version Oct 13, 2026
 */
public class ItemTest
{

    private Item apple;

    /**
     * makes a new item before each test
     */
    @BeforeEach
    public void setUp()
    {
        apple = new Item("apple");
    }


    /**
     * tests the constructor
     */
    @Test
    public void testConstructor()
    {
        assertEquals("apple", apple.name);
        assertEquals(0, apple.count);
    }


    /**
     * tests equals() with an item that has the same name
     */
    @Test
    public void testEqualsSameName()
    {
        Item other = new Item("apple");
        other.count = 5;

        assertTrue(apple.equals(other));

        // equals copies the other item's count
        assertEquals(5, apple.count);
    }


    /**
     * tests equals() with an item that has a different name
     */
    @Test
    public void testEqualsDifferentName()
    {
        Item other = new Item("banana");
        other.count = 5;

        assertFalse(apple.equals(other));
        assertEquals(0, apple.count);
    }


    /**
     * tests equals() with null and with something that is not an Item
     */
    @Test
    public void testEqualsNotItem()
    {
        assertFalse(apple.equals(null));
        assertFalse(apple.equals("apple"));
    }


    /**
     * tests that Items can be found inside a LinkedChain
     */
    @Test
    public void testItemInChain()
    {
        LinkedChain<Item> cart = new LinkedChain<Item>();
        cart.add(apple);

        assertTrue(cart.contains(new Item("apple")));
        assertFalse(cart.contains(new Item("pear")));
    }
}
