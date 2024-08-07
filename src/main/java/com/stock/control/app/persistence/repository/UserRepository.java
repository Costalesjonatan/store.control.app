package com.stock.control.app.persistence.repository;

import com.stock.control.app.domain.pojo.UserPojo;
import com.stock.control.app.domain.protocol.UserProtocolRepository;
import com.stock.control.app.persistence.entity.UserEntity;
import com.stock.control.app.persistence.mapper.UserPersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository implements UserProtocolRepository {

    private final UserJpaRepository userJpaRepository;
    private final UserPersistenceMapper userPersistenceMapper;

    @Override
    public Optional<UserPojo> create(UserPojo user) {
        UserEntity userToSave = userPersistenceMapper.toCreateEntity(user);
        return Optional.ofNullable(userPersistenceMapper.toPojo(userJpaRepository.save(userToSave)));
    }

    @Override
    public void update(UserPojo user) {
        UserEntity userToSave = userPersistenceMapper.toUpdateEntity(user);
        userJpaRepository.save(userToSave);
    }

    @Override
    public Optional<UserPojo> findByName(String name) {
        Optional<UserEntity> optionalUser = userJpaRepository.findByUsername(name);
        return optionalUser.map(userPersistenceMapper::toPojo);
    }
}
