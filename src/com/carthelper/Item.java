package com.carthelper;

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

    @Override
    public String toString(){
        return name;
    }
}
