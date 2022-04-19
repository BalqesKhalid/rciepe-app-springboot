package com.recipes.searchrecipe.adapter.web.rest.client;

import com.recipes.searchrecipe.adapter.web.rest.client.dto.IngredientDetailsResponse;
import com.recipes.searchrecipe.adapter.web.rest.client.dto.RecipeDetailsResponse;
import com.recipes.searchrecipe.adapter.web.rest.client.dto.SearchRecipeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@FeignClient(name = "spoonacular" ,url = "https://api.spoonacular.com/")
public interface RecipeClient {

    @GetMapping(value = "recipes/complexSearch",produces = {MediaType.APPLICATION_JSON_VALUE})
    SearchRecipeResponse findRecipe(@RequestHeader(value = "user-agent") String userAgent, @RequestParam String apiKey
                    , @RequestParam String title, @RequestParam String cuisine, @RequestParam String excludeCuisine);



    @GetMapping(value = "recipes/informationBulk",produces = {MediaType.APPLICATION_JSON_VALUE})
    Optional<RecipeDetailsResponse []> getRecipeIngredient(@RequestHeader(value = "user-agent") String userAgent, @RequestParam String apiKey
            , @RequestParam(value = "ids") Long id);


    @GetMapping(value = "food/ingredients/{ingredientId}/information",produces = {MediaType.APPLICATION_JSON_VALUE})
    IngredientDetailsResponse getIngredientCalories(@RequestHeader(value = "user-agent") String userAgent
                                                , @RequestParam String apiKey
                                            , @PathVariable long ingredientId
                                            , @RequestParam double amount
                                            , @RequestParam String unit);
}
