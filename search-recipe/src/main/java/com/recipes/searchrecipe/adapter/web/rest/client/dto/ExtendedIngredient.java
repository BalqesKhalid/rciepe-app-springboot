package com.recipes.searchrecipe.adapter.web.rest.client.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ExtendedIngredient {

    private long id;
    private String name;
    private double amount;
    private String unit;

}
