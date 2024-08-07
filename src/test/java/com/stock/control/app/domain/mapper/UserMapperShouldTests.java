package com.stock.control.app.domain.mapper;

import com.stock.control.app.domain.pojo.UserPojo;
import com.stock.control.app.rest.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
public class UserMapperShouldTests {

    private UserMapper userMapper;
    private UserPojo expectedUserPojo;
    private Exception expectedException;
    private UserDto userDto;

    @Test
    void mapToPojo() {
        givenUserPojo();
        givenUserDomainMapper();
        whenMappingToPojo();
        thenUserDtoIsMapped();
    }

    private void whenMappingToPojo() {
        try {
            expectedUserPojo = userMapper.toPojo(userDto);
        } catch (Exception exception) {
            expectedException = exception;
        }
    }

    private void thenUserDtoIsMapped() {

        verify(userMapper, only()).toPojo(userDto);

        then(expectedException).isNull();
        then(expectedUserPojo).isNotNull();
        then(expectedUserPojo.getUsername()).isEqualTo(userDto.getUsername());
        then(expectedUserPojo.getPassword()).isEqualTo(userDto.getPassword());
        then(expectedUserPojo.getAuthorities()).isNull();
    }

    private void givenUserDomainMapper() {
        userMapper = spy(new UserMapper());
    }

    private void givenUserPojo() {
        userDto = UserDto.builder()
                .username("username")
                .password("password")
                .build();
    }
}
