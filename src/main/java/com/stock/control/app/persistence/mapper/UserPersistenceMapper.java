package com.stock.control.app.persistence.mapper;

import com.stock.control.app.domain.pojo.UserPojo;
import com.stock.control.app.persistence.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserPersistenceMapper {

    public User toCreateEntity(UserPojo userPojo) {
        return User.builder()
                .username(userPojo.getUsername())
                .password(userPojo.getPassword())
                .build();
    }

    public User toUpdateEntity(UserPojo userPojo) {
        return User.builder()
                .id(userPojo.getId())
                .username(userPojo.getUsername())
                .password(userPojo.getPassword())
                .build();
    }

    public UserPojo toPojo(User user) {
        return UserPojo.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }
}
