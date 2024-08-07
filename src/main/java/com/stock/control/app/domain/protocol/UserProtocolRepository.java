package com.stock.control.app.domain.protocol;

import com.stock.control.app.domain.pojo.UserPojo;

import java.util.Optional;

public interface UserProtocolRepository {
    Optional<UserPojo> create(UserPojo user);
    void update(UserPojo user);
    Optional<UserPojo> findByName(String name);
}
