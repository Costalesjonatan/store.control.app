package com.stock.control.app.persistence.mapper;

import com.stock.control.app.domain.pojo.UserPojo;
import com.stock.control.app.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserPersistenceMapper {

    public UserEntity toCreateEntity(UserPojo userPojo) {
        return UserEntity.builder()
                .username(userPojo.getUsername())
                .password(userPojo.getPassword())
                .build();
    }

    public UserEntity toUpdateEntity(UserPojo userPojo) {
        return UserEntity.builder()
                .id(userPojo.getId())
                .username(userPojo.getUsername())
                .password(userPojo.getPassword())
                .build();
    }

    public UserPojo toPojo(UserEntity userEntity) {
        return UserPojo.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .build();
    }
}
