package com.stock.control.app.persistence.mapper;

import com.stock.control.app.domain.pojo.UserPojo;
import com.stock.control.app.persistence.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
public class UserPersistenceMapperShouldTests {
    private UserPersistenceMapper userPersistenceMapper;
    private User resultUser;
    private UserPojo resultUserPojo;
    private Exception resultException;
    private UserPojo userPojo;
    private User user;


    @Test
    void mapToCreateUser() {
        givenUserEntity();
        givenUserPojo();
        givenUserPersistenceMapper();
        whenMappingToCreateUser();
        thenUserPojoForCreateIsMapped();
    }

    @Test
    void mapToUpdateUser() {
        givenUserEntity();
        givenUserPojo();
        givenUserPersistenceMapper();
        whenMappingToUpdateUser();
        thenUserPojoForUpdateIsMapped();
    }

    @Test
    void mapToPojo() {
        givenUserEntity();
        givenUserPojo();
        givenUserPersistenceMapper();
        whenMappingToPojo();
        thenUserEntityToPojoIsMapped();
    }

    private void whenMappingToPojo() {
        try {
            resultUserPojo = userPersistenceMapper.toPojo(user);
        } catch (Exception exception) {
            resultException = exception;
        }
    }

    private void thenUserEntityToPojoIsMapped() {
        verify(userPersistenceMapper, times(0)).toCreateEntity(any());
        verify(userPersistenceMapper, times(0)).toUpdateEntity(any());
        verify(userPersistenceMapper, times(1)).toPojo(user);
        then(resultException).isNull();
        then(resultUserPojo.getId()).isEqualTo(1L);
        then(resultUserPojo.getUsername()).isEqualTo("username");
        then(resultUserPojo.getPassword()).isEqualTo("password");
    }

    private void whenMappingToUpdateUser() {
        try {
            resultUser = userPersistenceMapper.toUpdateEntity(userPojo);
        } catch (Exception exception) {
            resultException = exception;
        }
    }

    private void thenUserPojoForUpdateIsMapped() {
        verify(userPersistenceMapper, times(0)).toCreateEntity(any());
        verify(userPersistenceMapper, times(1)).toUpdateEntity(userPojo);
        verify(userPersistenceMapper, times(0)).toPojo(any());
        then(resultException).isNull();
        then(resultUser.getId()).isEqualTo(1L);
        then(resultUser.getUsername()).isEqualTo("username");
        then(resultUser.getPassword()).isEqualTo("password");
    }

    private void whenMappingToCreateUser() {
        try {
            resultUser = userPersistenceMapper.toCreateEntity(userPojo);
        } catch (Exception exception) {
            resultException = exception;
        }
    }

    private void thenUserPojoForCreateIsMapped() {
        verify(userPersistenceMapper, times(1)).toCreateEntity(userPojo);
        verify(userPersistenceMapper, times(0)).toUpdateEntity(any());
        verify(userPersistenceMapper, times(0)).toPojo(any());
        then(resultException).isNull();
        then(resultUser.getId()).isNull();
        then(resultUser.getUsername()).isEqualTo("username");
        then(resultUser.getPassword()).isEqualTo("password");
    }

    private void givenUserPersistenceMapper() {
        userPersistenceMapper = spy(new UserPersistenceMapper());
    }
    
    private void givenUserPojo() {
        userPojo = UserPojo.builder().id(1L).username("username").password("password").build();
    }
    
    private void givenUserEntity() {
        user = User.builder().id(1L).username("username").password("password").build();
    }
}
