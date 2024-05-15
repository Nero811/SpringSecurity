package com.example.demo.movie_security.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
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
public class CrossTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testCross() throws Exception {
        // 模擬一個Preflight call
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .options("/getMovies")
                .with(httpBasic("normal@gmail.com", "normal"))
                .header("Access-Control-Request-Method", "Get")
                .header("Origin", "http://example.com");

        mockMvc.perform(requestBuilder)
                .andExpect(header().exists("Access-Control-Allow-Origin"))
                .andExpect(header().string("Access-Control-Allow-Origin", "http://example.com"))
                .andExpect(header().exists("Access-Control-Allow-Methods"))
                .andExpect(header().string("Access-Control-Allow-Methods", "Get"))
                .andExpect(status().is(200));
    }
}
