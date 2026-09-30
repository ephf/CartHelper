package com.carthelper;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        final Scanner scanner = new Scanner(System.in);
        final LinkedChain<Store> stores = new LinkedChain<>();

        for(boolean once = true;; once = false) {
            if(once) {
                System.out.println("What store are you shopping at? \33[3;90m(press enter if you are done)\33[0m");
            } else {
                System.out.println("What store are you shopping at next?");
            }

            final String store_name = scanner.nextLine().trim();
            if(store_name.isEmpty()) break;

            final Store store = new Store(store_name);
            if(stores.contains(store)) {
                System.out.println(store_name + " is already in your list of stores!");
                continue;
            }

            stores.addToEnd(store);

            System.out.println("Type the items you want to add to the list \33[3;90m(press enter if you are done)\33[0m");
            while(true) {
                System.out.print("\t\33[90m( )\33[0m  ");

                final String item_name = scanner.nextLine().trim();
                if(item_name.isEmpty()) break;

                final Item item = new Item(item_name);
                if(!store.needed_items().remove(item)) {
                    item.count = 1;
                } else {
                    item.count++;
                }

                store.needed_items().addToEnd(item);
            }
        }

        for(int i = 0; i < stores.size(); i++) {
            final Store store = stores.get(i);

            System.out.println("Store \33[1m" + (i + 1)
                    + ":\33[0m Head to \33[1;36m" + store.name()
                    + " \33[0;3;90m(press enter when you are there)\33[0m");
            scanner.nextLine();

            while(true) {
                System.out.println("\33[1;36m" + store.name() + " List\33[0m");

                for(final Item item : store.needed_items().toArray(new Item[store.needed_items().size()])) {
                    System.out.println("\t( )  " + item.name + "  \33[3;90mx" + item.count + "\33[0m");
                }

                for(final Item item : store.gotten_items().toArray(new Item[store.gotten_items().size()])) {
                    System.out.println("\t\33[1;31m(x)\33[0;90m  " + item.name + "\33[0m");
                }

                if(store.needed_items().isEmpty()) break;

                System.out.print("\n(type the item you want to remove) ");
                while(true) {
                    final Item compare_item = new Item(scanner.nextLine().trim());

                    if (!store.needed_items().remove(compare_item)) {
                        System.out.print("'" + compare_item.name + "' is not on the list! ");
                        continue;
                    }

                    store.gotten_items().add(compare_item);
                    break;
                }
            }
        }
    }

}