package com.stock.control.app.domain.pojo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class UserPojo {
    private Long id;
    private String username;
    private String password;
    private List<String> roles;
}
