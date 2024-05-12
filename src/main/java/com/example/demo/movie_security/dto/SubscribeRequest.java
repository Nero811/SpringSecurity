package com.example.demo.movie_security.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscribeRequest {

    @JsonProperty("email")
    private String email;
}
