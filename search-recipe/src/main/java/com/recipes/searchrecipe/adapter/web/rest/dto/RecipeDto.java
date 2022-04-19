package com.recipes.searchrecipe.adapter.web.rest.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class RecipeDto {

    private Long Id;

    private String title;

}
