package com.recipes.searchrecipe.adapter.web.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.InetAddress;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class FindRecipeApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Environment environment;

    private static String URL = "http://localhost:3000/";
    /**
     * Find recipe, this test includes valid input
     * Happy scenario
     * @throws Exception
     */
    @Test
    void findRecipeTest() throws Exception {

        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.get(URL+"recipes")
                .params(new LinkedMultiValueMap<String,String>(){{
                    add("title" , "burger");
                    add("cuisine" , "italian");
                    add("excludeCuisine" , "greek");

                }});
        MvcResult mvcResult =
                mockMvc.perform(requestBuilder).andReturn();
        final String contentAsString = mvcResult.getResponse().getContentAsString();
        assertEquals(mvcResult.getResponse().getStatus(), HttpStatus.OK.value());
    }

    /**
     * find recipe, this test includes invalid input
     */

    @Test
    void findRecipeTestWithInvalidInput() throws Exception {

        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.get(URL+"recipes")
                        .params(new LinkedMultiValueMap<String,String>(){{
                            add("title" , "**");
                            add("cuisine" , "**");
                            add("excludeCuisine" , "**");

                        }});
        MvcResult mvcResult =
                mockMvc.perform(requestBuilder).andReturn();
        assertEquals(mvcResult.getResponse().getContentAsString(), "[]");
    }


}