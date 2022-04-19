package com.recipes.searchrecipe.adapter.web.rest;

import com.recipes.searchrecipe.adapter.web.rest.client.dto.ExtendedIngredient;
import com.recipes.searchrecipe.adapter.web.rest.client.dto.Recipe;
import com.recipes.searchrecipe.adapter.web.rest.dto.IngredientDto;
import com.recipes.searchrecipe.adapter.web.rest.dto.RecipeDto;

public class ToUserDtoConverterUtil {

    public static RecipeDto convertToRecipeDto(Recipe recipe){

        return RecipeDto.builder()
                .Id(recipe.getId())
                .title(recipe.getTitle())
                .build();

    }

    public static IngredientDto convertToIngredientDto(ExtendedIngredient ingredient){
        return IngredientDto.builder()
                .id(ingredient.getId())
                .name(ingredient.getName())
                .amount(ingredient.getAmount())
                .unit(ingredient.getUnit())
                .build();
    }
}
