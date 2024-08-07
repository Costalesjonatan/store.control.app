package com.stock.control.app.persistence.repository;

import com.stock.control.app.domain.pojo.UserPojo;
import com.stock.control.app.persistence.entity.UserEntity;
import com.stock.control.app.persistence.mapper.UserPersistenceMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
public class UserRepositoryShouldTest {
    private UserJpaRepository userJpaRepository;
    private UserPersistenceMapper userPersistenceMapper;
    private UserRepository userRepository;
    private Exception expectedException;
    private UserPojo expectedUser;
    private UserPojo saveUserPojo;
    private UserEntity saveUserEntity;
    private UserPojo updateUserPojo;
    private UserEntity updateUserEntity;
    private final String USERNAME = "USERNAME";
    private final String PASSWORD = "PASSWORD";

    @BeforeEach
    void setUp() {
        expectedException = null;
        givenSaveUserPojo();
        givenSaveUserEntity();
        givenUpdateUserPojo();
        givenUpdateUserEntity();
        givenUserJpaRepository();
        givenUserPersistenceMapper();
        givenUserRepository();
    }

    @Test
    void createUser() {
        whenCreatingUser();
        thenUserIsCreated();
    }

    @Test
    void updateUser() {
        whenUpdatingUser();
        thenUserIsUpdated();
    }

    @Test
    void findByName() {
        whenSearchingByName();
        thenUserIsFound();
    }

    @Test
    void notFindByName() {
        whenSearchingByNotSavedName();
        thenUserIsNotFound();
    }

    private void whenSearchingByNotSavedName() {
        when(userJpaRepository.findByUsername(USERNAME)).thenReturn(Optional.empty());

        try {
            Optional<UserPojo> userPojo = userRepository.findByName(USERNAME);
            userPojo.ifPresent(pojo -> expectedUser = pojo);
        } catch (Exception exception) {
            expectedException = exception;
        }
    }

    private void thenUserIsNotFound() {
        verify(userRepository, only()).findByName(USERNAME);
        verify(userPersistenceMapper, never()).toCreateEntity(any(UserPojo.class));
        verify(userPersistenceMapper, never()).toPojo(any(UserEntity.class));
        verify(userPersistenceMapper, never()).toUpdateEntity(any(UserPojo.class));
        verify(userJpaRepository, never()).save(any(UserEntity.class));
        verify(userJpaRepository, never()).findBy(any(), any());
        verify(userJpaRepository, times(1)).findByUsername(USERNAME);
        then(expectedUser).isNull();
        then(expectedException).isNull();
    }

    private void whenCreatingUser() {
        when(userPersistenceMapper.toCreateEntity(saveUserPojo)).thenReturn(saveUserEntity);
        when(userJpaRepository.save(any(UserEntity.class))).thenReturn(saveUserEntity);
        when(userPersistenceMapper.toPojo(any(UserEntity.class))).thenReturn(saveUserPojo);

        try {
            userRepository.create(saveUserPojo);
        } catch (Exception exception) {
            expectedException = exception;
        }

    }

    private void thenUserIsCreated() {
        verify(userRepository, only()).create(saveUserPojo);
        verify(userPersistenceMapper, times(1)).toCreateEntity(saveUserPojo);
        verify(userPersistenceMapper, times(1)).toPojo(saveUserEntity);
        verify(userPersistenceMapper, never()).toUpdateEntity(any(UserPojo.class));
        verify(userJpaRepository, times(1)).save(saveUserEntity);
        verify(userJpaRepository, never()).findBy(any(), any());
        verify(userJpaRepository, never()).findByUsername(anyString());
        then(expectedException).isNull();
    }

    private void whenUpdatingUser() {

        when(userPersistenceMapper.toUpdateEntity(updateUserPojo)).thenReturn(updateUserEntity);
        when(userJpaRepository.save(updateUserEntity)).thenReturn(updateUserEntity);

        try {
            userRepository.update(updateUserPojo);
        } catch (Exception exception) {
            expectedException = exception;
        }

    }

    private void thenUserIsUpdated() {
        verify(userRepository, only()).update(updateUserPojo);
        verify(userPersistenceMapper, never()).toCreateEntity(any(UserPojo.class));
        verify(userPersistenceMapper, never()).toPojo(any(UserEntity.class));
        verify(userPersistenceMapper, times(1)).toUpdateEntity(updateUserPojo);
        verify(userJpaRepository, times(1)).save(updateUserEntity);
        verify(userJpaRepository, never()).findBy(any(), any());
        verify(userJpaRepository, never()).findByUsername(anyString());
        then(expectedException).isNull();
    }

    private void whenSearchingByName() {
        when(userJpaRepository.findByUsername(USERNAME)).thenReturn(Optional.of(saveUserEntity));
        when(userPersistenceMapper.toPojo(any(UserEntity.class))).thenReturn(saveUserPojo);

        try {
            Optional<UserPojo> user = userRepository.findByName(USERNAME);
            user.ifPresent(userPojo -> expectedUser = userPojo);
        } catch (Exception exception) {
            expectedException = exception;
        }
    }

    private void thenUserIsFound() {
        verify(userRepository, only()).findByName(USERNAME);
        verify(userPersistenceMapper, never()).toCreateEntity(any(UserPojo.class));
        verify(userPersistenceMapper, times(1)).toPojo(saveUserEntity);
        verify(userPersistenceMapper, never()).toUpdateEntity(any(UserPojo.class));
        verify(userJpaRepository, never()).save(any(UserEntity.class));
        verify(userJpaRepository, never()).findBy(any(), any());
        verify(userJpaRepository, times(1)).findByUsername(USERNAME);
        then(expectedUser).isNotNull();
        then(expectedException).isNull();
    }

    private void givenUserJpaRepository() {
        userJpaRepository = mock(UserJpaRepository.class);
    }

    private void givenUserPersistenceMapper() {
        userPersistenceMapper = mock(UserPersistenceMapper.class);
    }

    private void givenUserRepository() {
        userRepository = spy(new UserRepository(userJpaRepository, userPersistenceMapper));
    }

    private void givenSaveUserPojo () {
        saveUserPojo = UserPojo.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .build();
    }
    private void givenSaveUserEntity () {
        saveUserEntity = UserEntity.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .build();
    }
    private void givenUpdateUserPojo () {
        updateUserPojo = UserPojo.builder()
                .id(1L)
                .username(USERNAME)
                .password(PASSWORD)
                .build();
    }
    private void givenUpdateUserEntity () {
        updateUserEntity = UserEntity.builder()
                .id(1L)
                .username(USERNAME)
                .password(PASSWORD)
                .build();
    }
}
