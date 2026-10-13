package com.carthelper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// -------------------------------------------------------------------------
/**
 * tests the linked chain class
 * 
 * @author shafiursarker
 * @version Oct 13, 2026
 */
public class LinkedChainTest
{

    private LinkedChain<String> chain;

    /**
     * makes a new empty chain before each test
     */
    @BeforeEach
    public void setUp()
    {
        chain = new LinkedChain<String>();
    }


    /**
     * tests that a new chain is empty
     */
    @Test
    public void testNewChain()
    {
        assertTrue(chain.isEmpty());
        assertEquals(0, chain.size());
        assertEquals("[]", chain.toString());
    }


    /**
     * tests that add puts entries at the front
     */
    @Test
    public void testAdd()
    {
        assertTrue(chain.add("a"));
        assertTrue(chain.add("b"));

        assertEquals(2, chain.size());
        assertFalse(chain.isEmpty());
        assertEquals("[b, a]", chain.toString());
    }


    /**
     * tests that add rejects null
     */
    @Test
    public void testAddNull()
    {
        assertThrows(IllegalArgumentException.class, () -> chain.add(null));
    }


    /**
     * tests that addToEnd keeps the order things were added
     */
    @Test
    public void testAddToEnd()
    {
        assertTrue(chain.addToEnd("a"));
        assertTrue(chain.addToEnd("b"));
        assertTrue(chain.addToEnd("c"));

        assertEquals(3, chain.size());
        assertEquals("[a, b, c]", chain.toString());
    }


    /**
     * tests that addToEnd rejects null
     */
    @Test
    public void testAddToEndNull()
    {
        assertThrows(
            IllegalArgumentException.class,
            () -> chain.addToEnd(null));
    }


    /**
     * tests remove with no parameter
     */
    @Test
    public void testRemoveFirst()
    {
        assertNull(chain.remove());

        chain.addToEnd("a");
        chain.addToEnd("b");

        assertEquals("a", chain.remove());
        assertEquals(1, chain.size());
        assertEquals("b", chain.remove());
        assertTrue(chain.isEmpty());
    }


    /**
     * tests remove on an empty chain and with null
     */
    @Test
    public void testRemoveEntryEmptyAndNull()
    {
        assertFalse(chain.remove("a"));

        chain.add("a");

        assertFalse(chain.remove(null));
        assertEquals(1, chain.size());
    }


    /**
     * tests remove on the first entry
     */
    @Test
    public void testRemoveEntryFirst()
    {
        chain.addToEnd("a");
        chain.addToEnd("b");

        assertTrue(chain.remove("a"));
        assertEquals(1, chain.size());
        assertEquals("[b]", chain.toString());
    }


    /**
     * tests remove on an entry later in the chain
     */
    @Test
    public void testRemoveEntryLater()
    {
        chain.addToEnd("a");
        chain.addToEnd("b");
        chain.addToEnd("c");

        assertTrue(chain.remove("c"));
        assertEquals(2, chain.size());
        assertEquals("[a, b]", chain.toString());
    }


    /**
     * tests remove when the entry is not in the chain
     */
    @Test
    public void testRemoveEntryNotFound()
    {
        chain.addToEnd("a");
        chain.addToEnd("b");

        assertFalse(chain.remove("z"));
        assertEquals(2, chain.size());
    }


    /**
     * tests clear
     */
    @Test
    public void testClear()
    {
        chain.add("a");
        chain.add("b");

        chain.clear();

        assertTrue(chain.isEmpty());
        assertEquals(0, chain.size());
        assertEquals("[]", chain.toString());
    }


    /**
     * tests get with good indexes
     */
    @Test
    public void testGet()
    {
        chain.addToEnd("a");
        chain.addToEnd("b");
        chain.addToEnd("c");

        assertEquals("a", chain.get(0));
        assertEquals("b", chain.get(1));
        assertEquals("c", chain.get(2));
    }


    /**
     * tests get with bad indexes
     */
    @Test
    public void testGetOutOfBounds()
    {
        chain.add("a");

        assertThrows(IndexOutOfBoundsException.class, () -> chain.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> chain.get(1));
    }


    /**
     * tests contains
     */
    @Test
    public void testContains()
    {
        assertFalse(chain.contains("a"));

        chain.add("a");
        chain.add("b");

        assertTrue(chain.contains("a"));
        assertTrue(chain.contains("b"));
        assertFalse(chain.contains("c"));
        assertFalse(chain.contains(null));
    }


    /**
     * tests count
     */
    @Test
    public void testCount()
    {
        assertEquals(0, chain.count("a"));

        chain.add("a");
        chain.add("b");
        chain.add("a");

        assertEquals(2, chain.count("a"));
        assertEquals(1, chain.count("b"));
        assertEquals(0, chain.count("c"));
        assertEquals(0, chain.count(null));
    }


    /**
     * tests toArray with arrays that are big enough
     */
    @Test
    public void testToArray()
    {
        chain.addToEnd("a");
        chain.addToEnd("b");

        String[] result = chain.toArray(new String[2]);
        assertEquals("a", result[0]);
        assertEquals("b", result[1]);

        String[] bigger = chain.toArray(new String[3]);
        assertEquals("a", bigger[0]);
        assertEquals("b", bigger[1]);
        assertNull(bigger[2]);
    }


    /**
     * tests toArray with a null array
     */
    @Test
    public void testToArrayNull()
    {
        assertThrows(IllegalArgumentException.class, () -> chain.toArray(null));
    }


    /**
     * tests toArray with an array that is too small
     */
    @Test
    public void testToArrayTooSmall()
    {
        chain.add("a");
        chain.add("b");

        assertThrows(
            IllegalArgumentException.class,
            () -> chain.toArray(new String[1]));
    }


    /**
     * tests toString with one entry
     */
    @Test
    public void testToStringOne()
    {
        chain.add("a");

        assertEquals("[a]", chain.toString());
    }
}
