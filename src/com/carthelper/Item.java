package com.carthelper;

// -------------------------------------------------------------------------
/**
 * Write a one-sentence summary of your class here. Follow it with additional
 * details about its purpose, what abstraction it represents, and how to use it.
 * 
 * @author Seth
 * @version Oct 13, 2026
 */
public class Item
{
    /**
     * 
     */
    public final String name;
    /**
     * 
     */
    public int count = 0;

    // ----------------------------------------------------------
    /**
     * Create a new Item object.
     * 
     * @param name
     */
    public Item(String name)
    {
        this.name = name;
    }


    @Override
    public boolean equals(Object other)
    {
        if (other instanceof final Item item && name.equals(((Item)other).name))
        {
            count = item.count;
            return true;
        }

        return false;
    }
}
