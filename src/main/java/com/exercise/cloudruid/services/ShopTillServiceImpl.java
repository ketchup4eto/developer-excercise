package com.exercise.cloudruid.services;

import com.exercise.cloudruid.models.Groceries;
import com.exercise.cloudruid.models.ShoppingCart;
import com.exercise.cloudruid.services.contracts.GroceriesService;
import com.exercise.cloudruid.services.contracts.ShopTillService;
import com.exercise.cloudruid.utils.enums.Deals;
import com.exercise.cloudruid.utils.exceptions.ItemNotInCartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShopTillServiceImpl implements ShopTillService {

    private final GroceriesService groceriesService;

    public static final ShoppingCart shoppingCart = new ShoppingCart(new HashMap<>());

    @Autowired
    public ShopTillServiceImpl(GroceriesService groceriesService) {
        this.groceriesService = groceriesService;
    }


    @Override
    public List<Groceries> viewCart() {
        List<Groceries> groceriesList = new ArrayList<>();
        for (List<Groceries> key : shoppingCart.getCart().values()) {
            groceriesList.addAll(key);
        }
        return groceriesList;
    }

    @Override
    public void addToCart(String itemName) {
        Groceries item = groceriesService.getByName(itemName);
        if (shoppingCart.getCart().get(Deals.TWOFORTHREE).size() != 3)
            shoppingCart.getCart().get(Deals.TWOFORTHREE).add(item);
        else if (item.getDeal() == Deals.TWOFORTHREE)
            shoppingCart.getCart().get(Deals.NONE).add(item);
        else shoppingCart.getCart().get(item.getDeal()).add(item);
    }

    @Override
    public void removeFromCart(String itemName) {
        Groceries item = groceriesService.getByName(itemName);
        if (!shoppingCart.getCart().get(item.getDeal()).contains(item))
            throw new ItemNotInCartException("The item you are trying to remove is not you cart");
        shoppingCart.getCart().get(item.getDeal()).remove(item);
    }

    @Override
    public void scanListOfItems(List<String> itemNames) {
        for (String itemName : itemNames) {
            addToCart(itemName);
        }
    }

    @Override
    public void emptyShoppingCart() {
        shoppingCart.getCart().get(Deals.NONE).clear();
        shoppingCart.getCart().get(Deals.BUYONEGETONEHALFPRICE).clear();
        shoppingCart.getCart().get(Deals.TWOFORTHREE).clear();
    }

    @Override
    public String totalCostOfCart() {
        double price = 0;
        price += buyOneGetOneHalfPrice(shoppingCart.getCart().get(Deals.BUYONEGETONEHALFPRICE));
        price += twoForThree(shoppingCart.getCart().get(Deals.TWOFORTHREE));
        for (Groceries groceries : shoppingCart.getCart().get(Deals.NONE)) {
            price += groceries.getPrice();
        }
        if (price < 100) {
            return String.format("%.0f clouds", price);
        } else {
            price /= 100;
            return String.format("%.2f aws", price);
        }
    }

    private int twoForThree(List<Groceries> items) {
        if (items.size() < 3) {
            int price = 0;
            for (Groceries item : items) {
                price += item.getPrice();
            }
            return price;
        }
        if (items.get(0).getPrice() < items.get(1).getPrice()
                && items.get(0).getPrice() < items.get(2).getPrice()) {
            return items.get(1).getPrice() + items.get(2).getPrice();
        } else if (items.get(1).getPrice() < items.get(2).getPrice()) {
            return items.get(0).getPrice() + items.get(2).getPrice();
        } else {
            return items.get(0).getPrice() + items.get(1).getPrice();
        }
    }

    private int buyOneGetOneHalfPrice(List<Groceries> items) {
        if (items.isEmpty())
            return 0;
        else if (items.size() == 1)
            return items.get(0).getPrice();
        int finalPrice = 0;
        Map<String, Integer> promoCount = new HashMap<>();
        for (Groceries item : items) {
            if (!promoCount.containsKey(item.getName()))
                promoCount.put(item.getName(), 1);
            else
                promoCount.put(item.getName(), promoCount.get(item.getName()) + 1);
        }
        for (Map.Entry<String, Integer> s : promoCount.entrySet()) {
            if (s.getValue() % 2 == 1)
                s.setValue(s.getValue() - 1);
            while (s.getValue() != 0) {
                if (s.getValue() % 2 == 0) {
                    finalPrice += groceriesService.getByName(s.getKey()).getPrice();
                } else {
                    finalPrice += groceriesService.getByName(s.getKey()).getPrice() / 2;
                }
                s.setValue(s.getValue() - 1);
            }
        }
        return finalPrice;
    }
}
