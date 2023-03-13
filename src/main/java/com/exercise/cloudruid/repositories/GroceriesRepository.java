package com.exercise.cloudruid.repositories;

import com.exercise.cloudruid.models.Groceries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroceriesRepository extends JpaRepository<Groceries, Integer> {
    /**
     * This method retrieves an object from a Database while searching by its name
     * @param name the name if the object we are looking for
     * @return null if the object is not found or object of type Groceries if found
     */
    Optional<Groceries> findByName(String name);

    /**
     * This method checks whether an item exists in the Database
     * @param name of the item to check for
     * @return true if the item exists and false if the item was not found
     */
    boolean existsByName(String name);
}
