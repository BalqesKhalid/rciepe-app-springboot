package com.recipes.searchrecipe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SearchRecipesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SearchRecipesApplication.class, args);
	}

}
