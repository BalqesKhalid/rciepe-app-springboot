package com.recipes.searchrecipe.adapter.web.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class CustomizedRecipeInformationApiTest {

    @Autowired
    private MockMvc mockMvc;

    private static String URL = "http://localhost:3000/";
    /**
     * Test Api that get the information of recipe, this test includes valid input
     * Happy scenario test
     *
     * @throws Exception
     */
    @Test
    void getRecipeInformationTest() throws Exception {

        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.get(URL+"recipes/ingredients/custom")
                        .params(new LinkedMultiValueMap<String,String>(){{
                            add("name","Italian Tuna Pasta");
                            add("id" , "648279");
                            add("excludedIngredients" , "10010062,20420,1033");
                        }});

        MvcResult mvcResult =
                mockMvc.perform(requestBuilder).andReturn();
        assertEquals(mvcResult.getResponse().getStatus(), HttpStatus.OK.value());
    }

    /**
     * Test Api that get the information of recipe, this test includes invalid input
     */

    @Test
    void getRecipeInformationTestWithInvalidInput() throws Exception {

        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.get(URL+"recipes/ingredients/custom")
                        .params(new LinkedMultiValueMap<String,String>(){{
                            add("name","Italian Tuna Pasta");
                            add("id" , "0");
                            add("excludedIngredients" , "11819,1002030,10015121");
                        }});

        MvcResult mvcResult =
                mockMvc.perform(requestBuilder).andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("Invalid  input for : recipeId:"+0));
    }

}