package com.carthelper;

import java.util.Scanner;

public class Main
{

    public static void main(String[] args)
    {

        final Scanner scanner = new Scanner(System.in);
        final LinkedChain<Store> stores = new LinkedChain<>();

        final String next_store_prompt =
            "What store are you shopping at next? \33[3;90m(press enter if you are done adding stores)\33[0m";

        for (boolean once = true;; once = false)
        {
            if (once)
            {
                System.out.println(
                    "What store are you shopping at? \33[3;90m(press enter if you are done)\33[0m");
            }
            else
            {
                System.out.println(next_store_prompt);
            }

            final String store_name =
                Validation.readStoreName(scanner, stores, next_store_prompt);
            if (store_name == null)
                return;
            if (store_name.isEmpty())
                break;

            final Store store = new Store(store_name);
            stores.addToEnd(store);

            System.out.println(
                "Type the items you want to add to the list \33[3;90m(press enter if you are done)\33[0m");
            while (true)
            {
                System.out.print("\t\33[90m( )\33[0m  ");

                final String item_name = Validation.readItemName(scanner);
                if (item_name == null)
                    return;
                if (item_name.isEmpty())
                    break;

                final Item existing =
                    Validation.findItem(store.needed_items(), item_name);
                if (existing != null)
                {
                    existing.count++;
                    continue;
                }

                final Item item = new Item(item_name);
                item.count = 1;
                store.needed_items().addToEnd(item);
            }
        }

        for (int i = 0; i < stores.size(); i++)
        {
            final Store store = stores.get(i);

            System.out.println(
                "Store \33[1m" + (i + 1) + ":\33[0m Head to \33[1;36m"
                    + store.name()
                    + " \33[0;3;90m(press enter when you are there)\33[0m");
            if (!Validation.waitForEnter(scanner))
                return;

            while (true)
            {
                System.out.println("\33[1;36m" + store.name() + " List\33[0m");

                for (final Item item : store.needed_items()
                    .toArray(new Item[store.needed_items().size()]))
                {
                    System.out.println(
                        "\t( )  " + item.name + "  \33[3;90mx" + item.count
                            + "\33[0m");
                }

                for (final Item item : store.gotten_items()
                    .toArray(new Item[store.gotten_items().size()]))
                {
                    System.out.println(
                        "\t\33[1;31m(x)\33[0;90m  " + item.name + "\33[0m");
                }

                if (store.needed_items().isEmpty())
                    break;

                System.out.print("\n(type the item you want to remove) ");
                final Item bought_item =
                    Validation.readItemToBuy(scanner, store.needed_items());
                if (bought_item == null)
                    return;

                store.needed_items().remove(bought_item);
                store.gotten_items().add(bought_item);
            }
        }
    }

}
