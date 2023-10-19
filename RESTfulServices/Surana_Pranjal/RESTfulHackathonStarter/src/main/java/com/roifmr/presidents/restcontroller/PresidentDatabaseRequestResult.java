package com.roifmr.presidents.restcontroller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PresidentDatabaseRequestResult {

    private int id;
    private String description;
    private float price;
    private int gears;
    private int sprockets;

}