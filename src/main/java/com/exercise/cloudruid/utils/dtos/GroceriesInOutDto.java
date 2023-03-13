package com.exercise.cloudruid.utils.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class GroceriesInOutDto {

    @NotEmpty(message = "Product name cannot be empty!")
    private String name;

    @NotEmpty(message = "Price cannot be empty!")
    private String price;

}
