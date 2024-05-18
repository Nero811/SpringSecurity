package com.example.demo.movie_security.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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
public class CsrfTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void test_watchFreeMovie_with_no_csrf_fail() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/watchFreeMovie")
                .with(httpBasic("normal@gmail.com", "normal"));

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(403));
    }

    @Test
    public void test_watchFreeMovie_with_csrf_success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/watchFreeMovie")
                .with(httpBasic("normal@gmail.com", "normal"))
                .with(csrf());

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200));
    }

    @Test
    public void test_register_with_no_csrf_success() throws Exception {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setEmail("normal@gmail.com");
        memberEntity.setPassword("normal");
        memberEntity.setName("Normal Member");
        memberEntity.setAge(30);

        String json = objectMapper.writeValueAsString(memberEntity);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/register")
                .header("Content-Type", "application/json")
                .with(httpBasic("normal@gmail.com", "normal"))
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200));
    }

    @Test
    public void test_userLogin_with_no_csrf_success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/userLogin")
                .with(httpBasic("normal@gmail.com", "normal"));

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200));
    }
}
