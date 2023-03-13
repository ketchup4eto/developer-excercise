package com.exercise.cloudruid.services.contracts;

import com.exercise.cloudruid.models.Groceries;

import java.util.List;

public interface GroceriesService {

    /**
     * This method returns a list of all the groceries that are currently available
     * @return a list of objects of type Groceries
     */
    List<Groceries> getAll();

    /**
     * This method searches for an item with the id specified as the parameter
     * @param id the id for which the method will look for
     * @return the item with the corresponding id (if found) otherwise will throw an exception
     */
    Groceries getById(int id);

    /**
     * This method searches for an item with the name specified as the parameter
     * @param name the name for which the method will look for
     * @return the item with the corresponding name (if found) otherwise will throw an exception
     */
    Groceries getByName(String name);

    /**
     * This method saves an object to the Database and throws an exception if an item already exists
     * @param item the object to be saved
     */
    void create(Groceries item);

    /**
     * This method takes a list of items and saves them to the Database
     * @param items the list of objects that are to be saved
     */
    void createFromList(List<Groceries> items);

    /**
     * This method is used to update the information the object contains. The main difference with the creation is that
     * here there is not a name check and is supposed to accept items which have already been saved to the Database
     * and not save new ones
     * @param item the updated item that will override its older version
     */
    void update(Groceries item);

    /**
     * This method removes the given entity from the Database
     * @param item the item that will be removed from the Database
     */
    void delete(Groceries item);
}
