package com.stock.control.app.persistence.repository;

import com.stock.control.app.domain.protocol.AuthorityProtocolRepository;
import com.stock.control.app.persistence.entity.Role;
import com.stock.control.app.persistence.entity.UserRole;
import com.stock.control.app.utils.AuthorityName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserAuthorityRepository implements AuthorityProtocolRepository {
    private final AuthorityJpaRepository authorityJpaRepository;
    private final UserAuthorityJpaRepository userAuthorityJpaRepository;

    @Override
    public void createAuthority(AuthorityName authority, Long userId) {
        Role role = authorityJpaRepository.findByName(authority.name());

        UserRole userRole = userAuthorityJpaRepository
                .findByUserIdAndRoleId(userId, role.getId());

        if(userRole == null) {
            userAuthorityJpaRepository.save(UserRole.builder()
                    .roleId(role.getId())
                    .userId(userId)
                    .build());
        }
    }

    @Override
    public void revokeAuthority(AuthorityName authority, Long userId) {
        Role role = authorityJpaRepository.findByName(authority.name());

        UserRole userRole = userAuthorityJpaRepository
                .findByUserIdAndRoleId(userId, role.getId());

        if(userRole != null) {
            userAuthorityJpaRepository.delete(userRole);
        }
    }

    @Override
    public List<String> getAuthoritiesByUserId(Long userId) {
        List<String> authorities = new ArrayList<>();
        List<UserRole> userAuthorities = userAuthorityJpaRepository.findByUserId(userId);

        for (UserRole userAuthority: userAuthorities) {
            Optional<Role> authority = authorityJpaRepository.findById(userAuthority.getRoleId());
            authority.ifPresent(role -> authorities.add(role.getName()));
        }

        return authorities;
    }
}
