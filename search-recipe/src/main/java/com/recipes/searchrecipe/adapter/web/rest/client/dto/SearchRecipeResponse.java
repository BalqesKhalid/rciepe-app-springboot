package com.recipes.searchrecipe.adapter.web.rest.client.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class SearchRecipeResponse {

    private List<Recipe> results;

    private int offset;
}
