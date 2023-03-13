package com.exercise.cloudruid.services.contracts;

import com.exercise.cloudruid.models.Groceries;

import java.util.List;

public interface ShopTillService {

    /**
     * This method return a list of all the items that are currently in the user's shopping cart
     * @return a list of objects that the user has put in their cart
     */
     List<Groceries> viewCart();

    /**
     * This method adds an item to the user's shopping cart
     * @param itemName the name of the item the user wishes to add
     */
    void addToCart(String itemName);

    /**
     * This method removes an item from the user's shopping cart
     * @param itemName the name of the item the user wishes to remove from their cart
     */
    void removeFromCart(String itemName);

    /**
     * This method takes a list of items and instantly adds if to the user's shopping cart
     * @param items the list of item names that the user wishes to add to their cart
     */
    void scanListOfItems(List<String> items);

    /**
     * This method removes all the items from the user's shopping cart
     */
    void emptyShoppingCart();

    /**
     * This method calculates the total amount of the user's shopping cart, automatically applying any available discounts
     * @return a formatted string with the exact amount of the items in the cart
     */
    String totalCostOfCart();
}


