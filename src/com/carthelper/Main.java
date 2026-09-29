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
                System.out.println("What store are you shopping at? \33[3;90m(press enter if you are done)\33[0m");
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

            System.out.println("Type the items you want to add to the list \33[3;90m(press enter if you are done)\33[0m");
            while(true) {
                final String item_name = scanner.nextLine().trim();
                if(item_name.isEmpty()) break;

                // defaulting to 1 item, we can change this later
                store.needed_items().addToEnd(new Item(item_name, 1));
            }
        }

        for(int i = 0; i < stores.size(); i++) {
            final Store store = stores.get(i);

            System.out.println("Store \33[1m" + i
                    + ":\33[0m Head to \33[1;36m" + store.name()
                    + " \33[0;3;90m(press enter when you are there)\33[0m");
            scanner.nextLine();

            while(!store.needed_items().isEmpty()) {
                System.out.println("\33[1;36m" + store.name() + " List\33[0m");

                for(final Item item : store.needed_items().toArray(new Item[store.needed_items().size()])) {
                    System.out.println("\t( )  " + item.name() + "  \33[3;90mx" + item.count() + "\33[0m");
                }

                for(final Item item : store.gotten_items().toArray(new Item[store.gotten_items().size()])) {
                    System.out.println("\t\33[1;31m(x)\33[0;90m  " + item.name() + "\33[0m");
                }

                System.out.print("\n(type the item you want to remove) ");
                while(true) {
                    final Item compare_item = new Item(scanner.nextLine().trim(), 0);

                    if (!store.needed_items().remove(compare_item)) {
                        System.out.print("'" + compare_item.name() + "' is not on the list! ");
                        continue;
                    }

                    store.gotten_items().add(compare_item);
                    break;
                }
            }

        }
    }

}