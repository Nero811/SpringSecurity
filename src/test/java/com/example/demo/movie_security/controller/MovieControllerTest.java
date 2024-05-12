package com.example.demo.movie_security.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@AutoConfigureMockMvc
@SpringBootTest
public class MovieControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void getMovies_normalMember_success() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/getMovies")
                .with(httpBasic("normal@gmail.com", "normal"));

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200));
    }

    @Test
    void watchFreeMovie_normalMember_success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/watchFreeMovie")
                .with(httpBasic("normal@gmail.com", "normal"));

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200));
    }

    @Test
    void watchFreeMovie_roleMovieManager_failed() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/watchFreeMovie")
                .with(httpBasic("movie-manager@gmail.com", "movie-manager"));

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(403));
    }
}
