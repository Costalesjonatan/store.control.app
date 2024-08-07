package com.stock.control.app.domain.service;

import com.stock.control.app.domain.pojo.UserPojo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<GrantedAuthority> authorities = new ArrayList<>();

        Optional<UserPojo> user = userService.getUserBy(username);

        if(user.isPresent()) {
            UserPojo userPojo = user.get();
            if(userPojo.getAuthorities() != null) {
                for(String authority : userPojo.getAuthorities()) {
                    authorities.add(new SimpleGrantedAuthority(authority));
                }
            }

            return new User(userPojo.getUsername(), userPojo.getPassword(), authorities);
        }
        throw new UsernameNotFoundException("Bad credentials.");
    }
}
