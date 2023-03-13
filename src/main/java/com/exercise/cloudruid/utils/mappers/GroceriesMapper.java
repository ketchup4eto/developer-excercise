package com.exercise.cloudruid.utils.mappers;

import com.exercise.cloudruid.models.Groceries;
import com.exercise.cloudruid.utils.dtos.GroceriesInOutDto;
import com.exercise.cloudruid.utils.exceptions.InvalidPriceFormatException;
import org.springframework.stereotype.Component;

@Component
public class GroceriesMapper {

    /**
     * This method converts the Data transfer object to a regular object that is ready to be stored or used by other
     * methods
     * @param dto the object which contains the user input information
     * @return the regular object which is used by other methods and is stored in the Database
     */
    public Groceries dtoToGroceries(GroceriesInOutDto dto) {
        String[] price = dto.getPrice().split(" ");
        Groceries groceries = new Groceries();
        groceries.setName(dto.getName());
        if (price[1].equals("aws"))
            groceries.setPrice((int) (Double.parseDouble(price[0]) * 100));
        else if (price[1].equals("c"))
            groceries.setPrice(Integer.parseInt(price[0]));
        else throw new InvalidPriceFormatException("Price format should be the following: 1,34 aws or 50 c");
        return groceries;
    }

    /**
     * This method converts the regular object to a Data transfer object with the purpose to be shown to the user while
     * hiding sensitive information
     * @param item the item that is going to be converted to a Data transfer object
     * @return the Data transfer object which is going to be displayed to the user
     */
    public GroceriesInOutDto groceriesToDto(Groceries item) {
        return new GroceriesInOutDto(item.getName(), (double) (item.getPrice()) / 100 + " aws");
    }
}
