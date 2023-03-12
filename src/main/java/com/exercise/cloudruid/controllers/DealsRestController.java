package com.exercise.cloudruid.controllers;

import com.exercise.cloudruid.services.contracts.DealsService;
import com.exercise.cloudruid.utils.exceptions.ItemDealException;
import com.google.common.base.Functions;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
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
    public String addToDealTwoForThree(@RequestBody JSONArray itemNames) {
        try {
            JSONParser parser = new JSONParser(itemNames.toString());
            dealsService.addToDealTwoForThree(parser.list().stream().map(Functions.toStringFunction()::apply).toList());
        } catch (ParseException | NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        } catch (ItemDealException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
        return "Added successfully";
    }

    @PutMapping("/2ndHalfPrice")
    public String addToDealBuyOneGetOneHalfPrice(@RequestBody JSONArray itemNames) {
        try {
            JSONParser parser = new JSONParser(itemNames.toString());
            dealsService.addToDealBuyOneGetOneHalfPrice(parser.list().stream()
                    .map(Functions.toStringFunction()::apply).toList());
        } catch (ParseException | NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        } catch (ItemDealException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
        return "Added successfully";
    }

    @PutMapping("/removeFromPromo")
    public String removeFromPromotion(@RequestBody JSONArray itemNames) {
        try {
            JSONParser parser = new JSONParser(itemNames.toString());
            dealsService.removeFromPromotion(parser.list().stream()
                    .map(Functions.toStringFunction()::apply).toList());
        } catch (ParseException | NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        }  catch (ItemDealException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
        return "Removed successfully";
    }

}
