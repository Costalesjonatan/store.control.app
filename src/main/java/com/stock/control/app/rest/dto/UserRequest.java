package com.stock.control.app.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@RequiredArgsConstructor
@AllArgsConstructor
public class UserRequest {
    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;
}
