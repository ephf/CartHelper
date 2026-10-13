# CartHelper

A shopping list helper for shopping across multiple stores

> [!NOTE]
> `Main.java` file located at [src/com/carthelper/Main.java](src/com/carthelper/Main.java)

## How to Run

Go to the [latest release](https://github.com/ephf/CartHelper/releases) to download the `.jar` file.

You can run the program by running the `java` command in the terminal:

```shell
java -jar /path/to/carthelper.jar
```

## Example Photos

Start by inputting the stores and items you want to add, you are allowed to add multiple of the same items to each store.

<div align="center">
    <img src="assets/screenshot-1.png" height=300 />
</div>

Then you can check off the items after heading to the store is says to head to,

<div align="center">
    <img src="assets/screenshot-2.png" height=300 />
</div>

When you have checked off every item for one store, you can head to the next store:

<div align="center">
    <img src="assets/screenshot-3.png" height=300 />
</div>

# Reflection
What changed from our specification? Our spec had a separate input for each item's quantity. Instead, 
the user just types the same item again, and its count goes up. We changed this so the user can add items 
quickly without an extra step. Because of this, duplicate checking now happens through Item.equals(), 
and our tests check for duplicate items instead of bad quantity input.

What changed in the LinkedChain? We used the provided LinkedChain as storage for our stores and items. 
We used addToEnd so the list stays in the order the user typed it.

What happened to our unknowns? We figured out how to read input until a blank line and how to test Main 
by simulating user input. Saving the list between runs is still open.

What doesn't work yet? Names are case sensitive, blank names with spaces are not rejected, and the user 
cannot remove an item they added by mistake.

* Next Steps
What is left to finish the MVP?
- Make names case insensitive
- Reject blank names
- Let the user remove an item before shopping

Which stretch goals will we attempt? We will attempt saving the list to a file. 
We are dropping any other stretch goals to focus on the MVP.

What new tests will we need? Tests for mixed-case duplicates, blank names, removing items, 
and saving/loading the list.

## Attribution
| File | Aneesh | Grant | Shafiur | Seth |
| --- | --- | --- | --- | --- |
| `Main.java` | Tested | Wrote | Reviewed | Reviewed |
| `Store.java` | Wrote | Revised | Reviewed | Tested |
| `Item.java` | Reviewed | Revised | Tested | Wrote |
| `LinkedChain.java` | Reviewed | Reviewed | Tested | Revised |
