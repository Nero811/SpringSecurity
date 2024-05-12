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

import com.example.demo.movie_security.entity.MemberEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest
public class MemberControllerTest {

        @Autowired
        MockMvc mockMvc;

        @Autowired
        ObjectMapper objectMapper;

        @Test
        public void testRegister() throws Exception {

                MemberEntity memberEntity = new MemberEntity();
                memberEntity.setEmail("test1@gmail.com");
                memberEntity.setPassword("111");
                memberEntity.setName("Test 1");
                memberEntity.setAge(30);

                String json = objectMapper.writeValueAsString(memberEntity);
                RequestBuilder requestBuilder = MockMvcRequestBuilders
                                .post("/register")
                                .header("Content-Type", "application/json")
                                .content(json);

                mockMvc.perform(requestBuilder)
                                .andExpect(status().is(200));

                requestBuilder = MockMvcRequestBuilders
                                .post("/userLogin")
                                .with(httpBasic("test1@gmail.com", "111"));

                mockMvc.perform(requestBuilder)
                                .andExpect(status().is(200));

                requestBuilder = MockMvcRequestBuilders
                                .get("/getMovies")
                                .with(httpBasic("test1@gmail.com", "111"));

                mockMvc.perform(requestBuilder)
                                .andExpect(status().is(200));
        }

        @Test
        public void testUserLogin() throws Exception {

                RequestBuilder requestBuilder = MockMvcRequestBuilders
                                .post("/userLogin")
                                .with(httpBasic("normal@gmail.com", "normal"));

                mockMvc.perform(requestBuilder)
                                .andExpect(status().is(200));
        }

}
