package com.stock.control.app.domain.mapper;

import com.stock.control.app.domain.pojo.UserPojo;
import com.stock.control.app.rest.dto.UserRequest;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserPojo toPojo(UserRequest user) {
        return UserPojo.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }
}
