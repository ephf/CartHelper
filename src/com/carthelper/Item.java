package com.carthelper;

// Maybe use a counter
public record Item(String name, int count) {

    @Override
    public boolean equals(Object other) {
        return other instanceof Item && name.equals(((Item) other).name());
    }

}
