package com.recipes.searchrecipe.adapter.web.rest;


import com.recipes.searchrecipe.adapter.web.rest.client.RecipeClient;
import com.recipes.searchrecipe.adapter.web.rest.client.dto.ExtendedIngredient;
import com.recipes.searchrecipe.adapter.web.rest.dto.IngredientDto;
import com.recipes.searchrecipe.adapter.web.rest.dto.RecipeDto;
import com.recipes.searchrecipe.adapter.web.rest.dto.RecipeSummaryDto;
import com.recipes.searchrecipe.exception.InvalidDataException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RecipeController {

    private final RecipeClient recipeClient;

    @Value("${client.apiKey}")
    private String apiKey;

    @Value("${client.userAgent}")
    private String userAgent;

    public RecipeController(RecipeClient recipe_client) {
        this.recipeClient = recipe_client;
    }

    @GetMapping(value = "recipes")
    public List<RecipeDto> findRecipe(@RequestParam String title, @RequestParam String cuisine, @RequestParam String excludeCuisine) {

        return recipeClient.findRecipe(userAgent, apiKey, title, cuisine, excludeCuisine).getResults()
                .stream()
                .map(ToUserDtoConverterUtil::convertToRecipeDto)
                .collect(Collectors.toList());
    }


    @GetMapping(value = "recipes/ingredients")
    public RecipeSummaryDto getRecipeSummary(@RequestParam String name ,@RequestParam Long id) {

        List<IngredientDto> ingredients = getIngredients(id)
                .stream()
                .map(ToUserDtoConverterUtil::convertToIngredientDto)
                .peek(ingredientDto -> ingredientDto.setCalories(computeCalories(ingredientDto)))
                .collect(Collectors.toList());

        return RecipeSummaryDto
                .builder()
                .name(name)
                .totalNumberOfCalories(getTotalNumberOfCalories(ingredients))
                .ingredients(ingredients)
                .build();

    }



    @GetMapping(value = "recipes/ingredients/custom")
    public RecipeSummaryDto getRecipeCustomizedSummary(@RequestParam String name,@RequestParam Long id, @RequestParam List<Long> excludedIngredients) throws InvalidDataException {

        List<IngredientDto> ingredients = getIngredients(id)
                .stream()
                .filter((ingredient) -> excludedIngredients.contains(ingredient.getId()) == false)
                .map(ToUserDtoConverterUtil::convertToIngredientDto)
                .peek(ingredientDto -> ingredientDto.setCalories(computeCalories(ingredientDto)))
                .collect(Collectors.toList());

        return RecipeSummaryDto
                .builder()
                .name(name)
                .totalNumberOfCalories(getTotalNumberOfCalories(ingredients))
                .ingredients(ingredients)
                .build();

    }

    private List<ExtendedIngredient> getIngredients(@RequestParam Long id) {
        return Arrays.stream(recipeClient.getRecipeIngredient(userAgent, apiKey, id)
                .orElseThrow(() -> new InvalidDataException("recipeId:" + id)))
                .findFirst()
                .orElseThrow(() -> new InvalidDataException("recipeId:" + id))
                .getExtendedIngredients();
    }

    private Double getTotalNumberOfCalories(List<IngredientDto> ingredients) {
        return ingredients.stream().map(i -> i.getCalories()).reduce(0.0, (integer, aDouble) -> integer + aDouble);
    }

    private double computeCalories(IngredientDto ingredient) {

        try {
            return Arrays.stream(
                        recipeClient.getIngredientCalories(userAgent, apiKey, ingredient.getId(),
                        ingredient.getAmount(), ingredient.getUnit())
                    .getNutrition()
                    .getNutrients())
                    .filter(ing -> ing.getName().contains("Calories"))
                    .findFirst()
                    .get()
                    .getAmount();
        } catch (Exception e) {
            return 0.0;
        }

    }

}


