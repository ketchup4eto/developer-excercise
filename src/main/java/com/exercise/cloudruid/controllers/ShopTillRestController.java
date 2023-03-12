package com.exercise.cloudruid.controllers;

import com.exercise.cloudruid.services.contracts.ShopTillService;
import com.exercise.cloudruid.utils.dtos.GroceriesInOutDto;
import com.exercise.cloudruid.utils.exceptions.GrocerieNotFoundException;
import com.exercise.cloudruid.utils.mappers.GroceriesMapper;
import com.google.common.base.Functions;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/till")
public class ShopTillRestController {

    private final ShopTillService shopTillService;
    private final GroceriesMapper groceriesMapper;

    @Autowired
    public ShopTillRestController(ShopTillService shopTillService, GroceriesMapper groceriesMapper) {
        this.shopTillService = shopTillService;
        this.groceriesMapper = groceriesMapper;
    }

    @GetMapping("/cart")
    public List<GroceriesInOutDto> viewCart() {
        return shopTillService.viewCart().stream().map(groceriesMapper::groceriesToDto).toList();
    }

    @PutMapping("/cart/add/{itemName}")
    public String addToCart(@PathVariable String itemName) {
        try {
            shopTillService.addToCart(itemName);
        } catch (GrocerieNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "Added to cart successfully";
    }

    @PutMapping("/cart/remove/{itemName}")
    public String removeFromCart(@PathVariable String itemName) {
        try {
            shopTillService.removeFromCart(itemName);
        } catch (GrocerieNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "Removed from cart successfully";
    }

    @PutMapping("/cart/add/list")
    public String scanListOfItems(@RequestBody JSONArray itemNames) {
        try {
            JSONParser parser = new JSONParser(itemNames.toString());
            shopTillService.scanListOfItems(parser.list().stream()
                    .map(Functions.toStringFunction()::apply).toList());
        } catch (ParseException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        } catch (GrocerieNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return "Items added to cart successfully";
    }

    @PutMapping("/cart/empty")
    public String emptyShoppingCart() {
        shopTillService.emptyShoppingCart();
        return "Cart has been emptied";
    }

    @GetMapping("/cart/total")
    public String totalCostOfCart() {
        try {
            return shopTillService.totalCostOfCart();
        } catch (GrocerieNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
