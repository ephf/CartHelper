package com.carthelper;

public record Store(String name, LinkedChain<Item> needed_items, LinkedChain<Item> gotten_items) {

    public Store(String name) {
        this(name, new LinkedChain<>(), new LinkedChain<>());
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Store && name.equals(((Store) other).name);
    }

}
