package com.recipes.searchrecipe.adapter.web.rest.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class IngredientDto {

    private long id;

    private String name;

    private double amount;

    private String unit;

    private double calories;
}
