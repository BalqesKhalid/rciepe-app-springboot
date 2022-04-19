package com.recipes.searchrecipe.adapter.web.rest.client.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Nutrient {

    private String name;
    private String unit;
    private double amount;
}
