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

import com.example.demo.movie_security.dto.SubscribeRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@AutoConfigureMockMvc
@SpringBootTest
public class SubscribeControllerTest {

        @Autowired
        public MockMvc mockMvc;

        @Autowired
        public ObjectMapper objectMapper;

        @Transactional
        @Test
        void testSubscribeAndUnSubscribe() throws Exception {
                SubscribeRequest subscribeRequest = new SubscribeRequest();
                subscribeRequest.setEmail("normal@gmail.com");
                String json = objectMapper.writeValueAsString(subscribeRequest);
                // Subscribe
                RequestBuilder requestMatcher = MockMvcRequestBuilders
                                .post("/subscribe")
                                .header("Content-Type", "application/json")
                                .content(json)
                                .with(httpBasic("normal@gmail.com", "normal"));

                mockMvc.perform(requestMatcher)
                                .andExpect(status().is(200));

                requestMatcher = MockMvcRequestBuilders
                                .post("/watchVipMovie")
                                .with(httpBasic("normal@gmail.com", "normal"));

                mockMvc.perform(requestMatcher)
                                .andExpect(status().is(200));
        }

        @Transactional
        @Test
        void testUnSubscribe() throws Exception {
                SubscribeRequest subscribeRequest = new SubscribeRequest();
                subscribeRequest.setEmail("normal@gmail.com");
                String json = objectMapper.writeValueAsString(subscribeRequest);
                // Subscribe
                RequestBuilder requestMatcher = MockMvcRequestBuilders
                                .post("/subscribe")
                                .header("Content-Type", "application/json")
                                .content(json)
                                .with(httpBasic("normal@gmail.com", "normal"));
                requestMatcher = MockMvcRequestBuilders
                                .post("/unSubscribe")
                                .header("Content-Type", "application/json")
                                .content(json)
                                .with(httpBasic("normal@gmail.com", "normal"));

                mockMvc.perform(requestMatcher)
                                .andExpect(status().is(200));

                requestMatcher = MockMvcRequestBuilders
                                .post("/watchVipMovie")
                                .with(httpBasic("normal@gmail.com", "normal"));

                mockMvc.perform(requestMatcher)
                                .andExpect(status().is(403));
        }
}
