package com.stock.control.app.domain.service;

import com.stock.control.app.domain.mapper.UserMapper;
import com.stock.control.app.domain.pojo.UserPojo;
import com.stock.control.app.domain.protocol.AuthorityProtocolRepository;
import com.stock.control.app.domain.protocol.UserProtocolRepository;
import com.stock.control.app.domain.validator.UserValidator;
import com.stock.control.app.rest.dto.UserDto;
import com.stock.control.app.utils.AuthorityName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
public class UserServiceShouldTest {
    private UserProtocolRepository userProtocolRepository;
    private AuthorityProtocolRepository authorityProtocolRepository;
    private UserValidator userValidator;
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;
    private UserService userService;
    private UserPojo userPojoResult;
    private Exception exceptionResult;
    private UserDto userDto;
    private UserPojo userPojo;
    private UserPojo userPojoWhitAuthorities;

    @Test
    void createUser() {
        givenUserDto();
        givenUserPojo();
        givenUserService();
        whenCreatingUser();
        thenUserIsCreated();
    }

    @Test
    void getUserByUsername() {
        givenUserDto();
        givenUserPojoWhitAuthorities();
        givenUserService();
        whenGettingUserByUsername();
        thenUserIsReturned();
    }

    private void whenGettingUserByUsername() {
        doNothing().when(userValidator).validateUsername(userDto.getUsername());
        when(userProtocolRepository.findByName(userDto.getUsername())).thenReturn(Optional.of(userPojoWhitAuthorities));
        when(authorityProtocolRepository.getAuthoritiesByUserId(1L)).thenReturn(List.of("USER"));
        try {
            Optional<UserPojo> userPojo = userService.getUserBy(userDto.getUsername());
            userPojo.ifPresent(pojo -> userPojoResult = pojo);
        } catch (Exception exception) {
            exceptionResult = exception;
        }
    }

    private void thenUserIsReturned() {
        verify(userService, only()).getUserBy(userDto.getUsername());
        verify(userValidator, times(1)).validateUsername(userDto.getUsername());
        verify(userProtocolRepository, times(1)).findByName(userDto.getUsername());
        verify(authorityProtocolRepository, times(1)).getAuthoritiesByUserId(1L);
        then(exceptionResult).isNull();
        then(userPojoResult).isEqualTo(userPojoWhitAuthorities);
    }

    private void whenCreatingUser() {
        doNothing().when(userValidator).validatePassword(userDto.getPassword());
        doNothing().when(userValidator).validateUsername(userDto.getUsername());
        when(userMapper.toPojo(userDto)).thenReturn(userPojo);
        when(passwordEncoder.encode(userPojo.getPassword())).thenReturn("passwordEncoded");
        when(userProtocolRepository.create(userPojo)).thenReturn(Optional.of(userPojo));
        doNothing().when(authorityProtocolRepository).createAuthority(AuthorityName.USER, userPojo.getId());

        try {
            userService.createUser(userDto);
        } catch (Exception exception) {
            exceptionResult = exception;
        }
    }

    private void thenUserIsCreated() {
        verify(userService, only()).createUser(userDto);
        verify(userValidator, times(1)).validatePassword(userDto.getPassword());
        verify(userValidator, times(1)).validateUsername(userDto.getUsername());
        verify(userMapper, times(1)).toPojo(userDto);
        verify(passwordEncoder, times(1)).encode(userDto.getPassword());
        verify(userProtocolRepository, times(1)).create(userPojo);
        verify(authorityProtocolRepository, times(1))
                .createAuthority(AuthorityName.USER, userPojo.getId());
        then(exceptionResult).isNull();
    }

    private void givenUserService() {
        givenUserProtocolRepository();
        givenAuthorityProtocolRepository();
        givenUserValidator();
        givenUserDomainMapper();
        givenPasswordEncoder();
        userService = spy(new UserService(
                userProtocolRepository,
                authorityProtocolRepository,
                userValidator,
                userMapper,
                passwordEncoder
        ));
    }

    private void givenPasswordEncoder() {passwordEncoder = mock(BCryptPasswordEncoder.class);}
    private void givenUserDomainMapper() {userMapper = mock(UserMapper.class);}
    private void givenUserValidator() {userValidator = mock(UserValidator.class);}
    private void givenAuthorityProtocolRepository() {authorityProtocolRepository = mock(AuthorityProtocolRepository.class);}
    private void givenUserProtocolRepository() {userProtocolRepository = mock(UserProtocolRepository.class);}

    private void givenUserDto() {
        userDto = UserDto.builder().username("username").password("password").build();
    }
    private void givenUserPojo() {
        userPojo = UserPojo.builder()
                .id(1L).username(userDto.getUsername())
                .password(userDto.getPassword())
                .build();
    }
    private void givenUserPojoWhitAuthorities() {
        userPojoWhitAuthorities = UserPojo.builder()
                .id(1L)
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .roles(List.of("CUSTOMER")).build();
    }

}
