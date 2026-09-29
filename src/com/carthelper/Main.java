package com.carthelper;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        final Scanner scanner = new Scanner(System.in);
        final LinkedChain<Store> stores = new LinkedChain<>();

        boolean ran_loop_once = false;
        while(true) {
            if(ran_loop_once) {
                System.out.println("What store are you shopping at next?");
            } else {
                System.out.println("What store are you shopping at? (press enter if you are done)");
            }

            final String store_name = scanner.nextLine().trim();
            if(store_name.isEmpty()) break;
            ran_loop_once = true;

            final Store store = new Store(store_name);
            if(stores.contains(store)) {
                System.out.println(store_name + " is already in your list of stores!");
                continue;
            }

            stores.addToEnd(store);

            System.out.println("Type the items you want to add to the list (press enter if you are done)");
            while(true) {
                final String item_name = scanner.nextLine().trim();
                if(item_name.isEmpty()) break;

                // defaulting to 1 item, we can change this later
                store.needed_items().addToEnd(new Item(item_name, 1));
            }
        }

        for(final Store store : stores.toArray(new Store[stores.size()])) {
            // Color Code: bold, cyan
            System.out.println("\33[1;36m" + store.name() + "\33[0m");

            for(final Item item : store.needed_items().toArray(new Item[store.needed_items().size()])) {
                // Color Code: gray
                System.out.println("\33[90m\t ( )  " + item.name() + "\33[0m");
            }

            System.out.println();
        }

    }

}