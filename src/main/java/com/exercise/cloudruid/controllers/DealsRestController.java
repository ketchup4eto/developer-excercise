package com.exercise.cloudruid.controllers;

import com.exercise.cloudruid.services.contracts.DealsService;
import com.exercise.cloudruid.utils.exceptions.ItemDealException;
import com.exercise.cloudruid.utils.helper.classes.Helpers;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/deals")
public class DealsRestController {

    private final DealsService dealsService;

    @Autowired
    public DealsRestController(DealsService dealsService) {
        this.dealsService = dealsService;
    }

    @PutMapping("/2for3")
    public String addToDealTwoForThree(@RequestBody String itemNames) {
        try {
            dealsService.addToDealTwoForThree(Helpers.parseJsonList(itemNames));
        } catch (ParseException | NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        } catch (ItemDealException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
        return "Added successfully";
    }

    @PutMapping("/2ndHalfPrice")
    public String addToDealBuyOneGetOneHalfPrice(@RequestBody String itemNames) {
        try {
            dealsService.addToDealBuyOneGetOneHalfPrice(Helpers.parseJsonList(itemNames));
        } catch (ParseException | NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        } catch (ItemDealException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
        return "Added successfully";
    }

    @PutMapping("/removeFromPromo")
    public String removeFromPromotion(@RequestBody String itemNames) {
        try {
            dealsService.removeFromPromotion(Helpers.parseJsonList(itemNames));
        } catch (ParseException | NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        } catch (ItemDealException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
        return "Removed successfully";
    }
}
