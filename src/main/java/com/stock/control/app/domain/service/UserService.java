package com.stock.control.app.domain.service;

import com.stock.control.app.domain.mapper.UserMapper;
import com.stock.control.app.domain.pojo.UserPojo;
import com.stock.control.app.domain.protocol.AuthorityProtocolRepository;
import com.stock.control.app.domain.protocol.UserProtocolRepository;
import com.stock.control.app.domain.validator.UserValidator;
import com.stock.control.app.rest.dto.UserRequest;
import com.stock.control.app.utils.AuthorityName;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserProtocolRepository userProtocolRepository;
    private final AuthorityProtocolRepository authorityProtocolRepository;
    private final UserValidator userValidator;
    private final UserMapper userDomainMapper;
    private final PasswordEncoder passwordEncoder;

    public void createUser(UserRequest user) {
        userValidator.validateUsername(user.getUsername());
        userValidator.validatePassword(user.getPassword());
        UserPojo mappedUser = userDomainMapper.toPojo(user);
        mappedUser.setPassword(passwordEncoder.encode(mappedUser.getPassword()));
        Optional<UserPojo> savedUser = userProtocolRepository.create(mappedUser);
        savedUser.ifPresent(userPojo -> authorityProtocolRepository.createAuthority(AuthorityName.USER, userPojo.getId()));
    }

    public Optional<UserPojo> getUserBy(String username) {
        userValidator.validateUsername(username);
        Optional<UserPojo> user = userProtocolRepository.findByName(username);
        user.ifPresent(userPojo -> userPojo.setRoles(authorityProtocolRepository.getAuthoritiesByUserId(userPojo.getId())));
        return user;
    }
}
