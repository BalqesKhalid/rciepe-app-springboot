package com.recipes.searchrecipe.adapter.web.rest.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Setter
@Getter
public class RecipeSummaryDto {

    private String name;

    private Double totalNumberOfCalories;

    private List<IngredientDto> ingredients;


}
