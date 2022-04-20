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

    /**
     * <b>This Api return list of recipes that are matched search criteria</b>
     * @param title
     * @param cuisine
     * @param excludeCuisine
     * @return
     */
    @GetMapping(value = "recipes")
    public List<RecipeDto> findRecipe(@RequestParam String title, @RequestParam String cuisine, @RequestParam String excludeCuisine) {

        return recipeClient.findRecipe(userAgent, apiKey, title, cuisine, excludeCuisine).getResults()
                .stream()
                .map(ToUserDtoConverterUtil::convertToRecipeDto)
                .collect(Collectors.toList());
    }

    /**
     * <b>This Api will return information about the recipe of the submitted recipe_id, information includes
     * each ingredient calories and the summation of each ingredient calories as a total calories</b>
     * @param name
     * @param id
     * @return
     */

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

    /**
     * <b>This Api will return information about the recipe of the submitted recipe_id, information includes
     * each ingredient calories and the summation of each ingredient calories as a total calories
     * and it will exclude the ingredients of the excludedIngredients list</b>
     * @param name
     * @param id
     * @param excludedIngredients
     * @return
     * @throws InvalidDataException
     */

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

    /**
     * <b>This method will call end point to get recipe ingredients<b/>
     * @param id
     * @return
     */
    private List<ExtendedIngredient> getIngredients(@RequestParam Long id) {
        return Arrays.stream(recipeClient.getRecipeIngredient(userAgent, apiKey, id)
                .orElseThrow(() -> new InvalidDataException("recipeId:" + id)))
                .findFirst()
                .orElseThrow(() -> new InvalidDataException("recipeId:" + id))
                .getExtendedIngredients();
    }

    /**
     * <b>get the summation of total ingredients calories</b>
     * @param ingredients
     * @return
     */
    private Double getTotalNumberOfCalories(List<IngredientDto> ingredients) {
        return ingredients.stream().map(i -> i.getCalories()).reduce(0.0, (integer, aDouble) -> integer + aDouble);
    }

    /**
     * <b>call Api to get each ingredient calories</b>
     * @param ingredient
     * @return
     */
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


