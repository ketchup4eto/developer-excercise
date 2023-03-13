package com.exercise.cloudruid.controllers;

import com.exercise.cloudruid.services.contracts.ShopTillService;
import com.exercise.cloudruid.utils.dtos.GroceriesInOutDto;
import com.exercise.cloudruid.utils.exceptions.GrocerieNotFoundException;
import com.exercise.cloudruid.utils.exceptions.ItemNotInCartException;
import com.exercise.cloudruid.utils.helper.classes.Helpers;
import com.exercise.cloudruid.utils.mappers.GroceriesMapper;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/till/cart")
public class ShopTillRestController {

    private final ShopTillService shopTillService;
    private final GroceriesMapper groceriesMapper;

    @Autowired
    public ShopTillRestController(ShopTillService shopTillService, GroceriesMapper groceriesMapper) {
        this.shopTillService = shopTillService;
        this.groceriesMapper = groceriesMapper;
    }

    @GetMapping
    public List<GroceriesInOutDto> viewCart() {
        return shopTillService.viewCart().stream().map(groceriesMapper::groceriesToDto).toList();
    }

    @PutMapping("/add/{itemName}")
    public String addToCart(@PathVariable String itemName) {
        try {
            shopTillService.addToCart(itemName);
        } catch (GrocerieNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return "Added to cart successfully";
    }

    @PutMapping("/remove/{itemName}")
    public String removeFromCart(@PathVariable String itemName) {
        try {
            shopTillService.removeFromCart(itemName);
        } catch (ItemNotInCartException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return "Removed from cart successfully";
    }

    @PutMapping("/add/list")
    public String scanListOfItems(@RequestBody String itemNames) {
        try {
            shopTillService.scanListOfItems(Helpers.parseJsonList(itemNames));
        } catch (ParseException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        } catch (GrocerieNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return "Items added to cart successfully";
    }

    @PutMapping("/empty")
    public String emptyShoppingCart() {
        shopTillService.emptyShoppingCart();
        return "Cart has been emptied";
    }

    @GetMapping("/total")
    public String totalCostOfCart() {
        try {
            return shopTillService.totalCostOfCart();
        } catch (GrocerieNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
