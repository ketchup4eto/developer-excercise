package com.exercise.cloudruid.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "Groceries")
@Table(name = "groceries")
public class Groceries {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private int price;

    @Column(name = "2_for_3")
    private boolean twoForThree;

    @Column(name = "buy_1_get_1_half_price")
    private boolean buyOneGetOneHalfPrice;
}
