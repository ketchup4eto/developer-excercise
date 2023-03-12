package com.exercise.cloudruid.controllers;

import com.exercise.cloudruid.models.Groceries;
import com.exercise.cloudruid.services.contracts.GroceriesService;
import com.exercise.cloudruid.utils.dtos.GroceriesInOutDto;
import com.exercise.cloudruid.utils.exceptions.GrocerieNotFoundException;
import com.exercise.cloudruid.utils.exceptions.InvalidPriceFormatException;
import com.exercise.cloudruid.utils.exceptions.ItemExistsException;
import com.exercise.cloudruid.utils.mappers.GroceriesMapper;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/groceries")
public class GroceriesRestController {

    private final GroceriesMapper groceriesMapper;

    private final GroceriesService groceriesService;

    @Autowired
    public GroceriesRestController(GroceriesMapper groceriesMapper,
                                   GroceriesService groceriesService) {
        this.groceriesMapper = groceriesMapper;
        this.groceriesService = groceriesService;
    }

    @GetMapping
    public List<GroceriesInOutDto> getAllProducts() {
        List<GroceriesInOutDto> output = new ArrayList<>();
        for (Groceries groceries : groceriesService.getAll()) {
            output.add(groceriesMapper.groceriesToDto(groceries));
        }
        return output;
    }

    @GetMapping("/id/{id}")
    public GroceriesInOutDto getById(@PathVariable int id) {
        try {
            return groceriesMapper.groceriesToDto(groceriesService.getById(id));
        } catch (GrocerieNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/name/{name}")
    public GroceriesInOutDto getByName(@PathVariable String name) {
        try {
            return groceriesMapper.groceriesToDto(groceriesService.getByName(name));
        } catch (GrocerieNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/create")
    public String create(@RequestBody GroceriesInOutDto dto) {
        try {
            groceriesService.create(groceriesMapper.dtoToGroceries(dto));
        } catch (ItemExistsException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
        return "Successfully created!";
    }

    @PostMapping("/create/list")
    public String createFromList(@RequestBody JSONArray itemList) {
        try {
            JSONParser parser = new JSONParser(String.valueOf(itemList));
            List<Groceries> convertedList = new ArrayList<>();
            for (Object o : parser.list()) {
                GroceriesInOutDto dto = (GroceriesInOutDto) o;
                convertedList.add(groceriesMapper.dtoToGroceries(dto));
            }

            groceriesService.createFromList(convertedList);
        } catch (ParseException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        } catch (ItemExistsException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
        return "Items created successfully!";
    }

    @PutMapping("/update/{name}/{newPrice}")
    public String updatePrice(@PathVariable String name, @PathVariable String newPrice) {
        try {
            Groceries itemToUpdate = groceriesService.getByName(name);
            String[] price = newPrice.split(" ");
            if (price[1].equals("aws"))
                itemToUpdate.setPrice((int) (Double.parseDouble(price[0]) * 100));
            else if (price[1].equals("c"))
                itemToUpdate.setPrice(Integer.parseInt(price[0]));
            else throw new InvalidPriceFormatException("Price format should be the following: 1,34 aws or 50 c");
        } catch (GrocerieNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return "Price updated successfully";
    }

    @DeleteMapping("/delete/{name}")
    public String delete(@PathVariable String name) {
        try {
            groceriesService.delete(groceriesService.getByName(name));
        } catch (GrocerieNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return "Deleted successfully";
    }

}
