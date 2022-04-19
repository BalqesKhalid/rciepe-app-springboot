package com.recipes.searchrecipe.adapter.web.rest.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Recipe {

    private Long Id;

    private String title;

}
