package com.recipes.searchrecipe.adapter.web.rest.client.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class RecipeDetailsResponse {

    private List<ExtendedIngredient> extendedIngredients;
}
