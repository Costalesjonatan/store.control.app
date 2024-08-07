package com.stock.control.app.persistence.repository;

import com.stock.control.app.domain.protocol.AuthorityProtocolRepository;
import com.stock.control.app.persistence.entity.AuthorityEntity;
import com.stock.control.app.persistence.entity.UserAuthorityEntity;
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
        AuthorityEntity authorityEntity = authorityJpaRepository.findByName(authority.name());

        UserAuthorityEntity userAuthorityEntity = userAuthorityJpaRepository
                .findByUserIdAndAuthorityId(userId, authorityEntity.getId());

        if(userAuthorityEntity == null) {
            userAuthorityJpaRepository.save(UserAuthorityEntity.builder()
                    .authorityId(authorityEntity.getId())
                    .userId(userId)
                    .build());
        }
    }

    @Override
    public void revokeAuthority(AuthorityName authority, Long userId) {
        AuthorityEntity authorityEntity = authorityJpaRepository.findByName(authority.name());

        UserAuthorityEntity userAuthorityEntity = userAuthorityJpaRepository
                .findByUserIdAndAuthorityId(userId, authorityEntity.getId());

        if(userAuthorityEntity != null) {
            userAuthorityJpaRepository.delete(userAuthorityEntity);
        }
    }

    @Override
    public List<String> getAuthoritiesByUserId(Long userId) {
        List<String> authorities = new ArrayList<>();
        List<UserAuthorityEntity> userAuthorities = userAuthorityJpaRepository.findByUserId(userId);

        for (UserAuthorityEntity userAuthority: userAuthorities) {
            Optional<AuthorityEntity> authority = authorityJpaRepository.findById(userAuthority.getAuthorityId());
            authority.ifPresent(authorityEntity -> authorities.add(authorityEntity.getName()));
        }

        return authorities;
    }
}
