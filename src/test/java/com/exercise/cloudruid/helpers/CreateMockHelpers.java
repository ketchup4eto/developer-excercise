package com.exercise.cloudruid.helpers;

import com.exercise.cloudruid.models.Groceries;
import com.exercise.cloudruid.models.ShoppingCart;
import com.exercise.cloudruid.utils.enums.Deals;

import java.util.HashMap;
import java.util.List;

public class CreateMockHelpers {
    public static Groceries mockGroceries() {
        var mockGroceries = new Groceries();
        mockGroceries.setPrice(100);
        mockGroceries.setId(1);
        mockGroceries.setDeal(Deals.NONE);
        mockGroceries.setName("MockGroceries");
        return mockGroceries;
    }

    public static ShoppingCart shoppingCart(){
        return new ShoppingCart(new HashMap<>());
    }

    public static ShoppingCart shoppingCartWithItems(){
        var cart = new ShoppingCart(new HashMap<>());
        cart.getCart().put(Deals.NONE, List.of(mockGroceries()));
        return cart;
    }
}
