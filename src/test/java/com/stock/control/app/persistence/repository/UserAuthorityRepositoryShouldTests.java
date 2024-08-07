package com.stock.control.app.persistence.repository;

import com.stock.control.app.persistence.entity.AuthorityEntity;
import com.stock.control.app.persistence.entity.UserAuthorityEntity;
import com.stock.control.app.utils.AuthorityName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
public class UserAuthorityRepositoryShouldTests {
    private AuthorityJpaRepository authorityJpaRepository;
    private UserAuthorityJpaRepository userAuthorityJpaRepository;
    private UserAuthorityRepository userAuthorityRepository;
    private Exception expectedException;
    private List<String> expectedAuthorities;
    private AuthorityEntity authorityEntity;
    private UserAuthorityEntity userAuthorityEntity;
    private final String ROLE = "USER";

    @Test
    void createAuthorityForUser() {
        givenAuthorityEntity();
        givenUserAuthorityEntity();
        givenUserAuthorityRepository();
        whenCreatingAuthority();
        thenAuthorityIsCreated();
    }

    @Test
    void notCreateAuthorityForUserIfAlreadyExists() {
        givenAuthorityEntity();
        givenUserAuthorityEntity();
        givenUserAuthorityRepository();
        whenCreatingAuthorityThatAlreadyExist();
        thenAuthorityIsNotCreated();
    }

    @Test
    void revokeAuthority() {
        givenAuthorityEntity();
        givenUserAuthorityEntity();
        givenUserAuthorityRepository();
        whenRevokingAuthority();
        thenAuthorityIsRevoked();
    }

    @Test
    void notRevokeAuthorityThatAlreadyRevoked() {
        givenAuthorityEntity();
        givenUserAuthorityRepository();
        whenRevokingAuthorityAlreadyRevoked();
        thenAuthorityIsNotRevoked();
    }

    @Test
    void getAuthoritiesByUserId() {
        givenAuthorityEntity();
        givenUserAuthorityEntity();
        givenUserAuthorityRepository();
        whenGettingAuthorities();
        thenAuthoritiesIsReturned();
    }

    @Test
    void getEmptyAuthoritiesByUserId() {
        givenUserAuthorityRepository();
        whenGettingEmptyAuthorities();
        thenEmptyAuthoritiesIsReturned();
    }

    private void whenGettingEmptyAuthorities() {
        when(userAuthorityJpaRepository.findByUserId(1L)).thenReturn(Collections.emptyList());
        try {
            expectedAuthorities = userAuthorityRepository.getAuthoritiesByUserId(1L);
        } catch (Exception exception) {
            expectedException = exception;
        }
    }

    private void thenEmptyAuthoritiesIsReturned() {
        verify(userAuthorityRepository, times(0)).revokeAuthority(any(), any());
        verify(userAuthorityRepository, times(1)).getAuthoritiesByUserId(1L);
        verify(userAuthorityRepository, times(0)).createAuthority(any(), any());

        verify(userAuthorityJpaRepository, times(1)).findByUserId(1L);
        verify(authorityJpaRepository, never()).findById(anyLong());
        then(expectedException).isNull();
        then(expectedAuthorities.size()).isEqualTo(0);
    }

    private void whenGettingAuthorities() {
        when(userAuthorityJpaRepository.findByUserId(1L)).thenReturn(List.of(userAuthorityEntity));
        when(authorityJpaRepository.findById(userAuthorityEntity.getAuthorityId()))
                .thenReturn(Optional.of(authorityEntity));
        try {
            expectedAuthorities = userAuthorityRepository.getAuthoritiesByUserId(1L);
        } catch (Exception exception) {
            expectedException = exception;
        }
    }

    private void thenAuthoritiesIsReturned() {
        verify(userAuthorityRepository, times(0)).revokeAuthority(any(), any());
        verify(userAuthorityRepository, times(1)).getAuthoritiesByUserId(1L);
        verify(userAuthorityRepository, times(0)).createAuthority(any(), any());

        verify(userAuthorityJpaRepository, times(1)).findByUserId(1L);
        verify(authorityJpaRepository, times(1)).findById(userAuthorityEntity.getAuthorityId());
        then(expectedException).isNull();
        then(expectedAuthorities.size()).isEqualTo(1);
        then(expectedAuthorities.get(0)).isEqualTo(ROLE);
    }

    private void whenRevokingAuthorityAlreadyRevoked() {
        when(authorityJpaRepository.findByName(ROLE)).thenReturn(authorityEntity);
        when(userAuthorityJpaRepository.findByUserIdAndAuthorityId(1L, authorityEntity.getId()))
                .thenReturn(null);
        try {
            userAuthorityRepository.revokeAuthority(AuthorityName.USER, 1L);
        } catch (Exception exception) {
            expectedException = exception;
        }
    }

    private void thenAuthorityIsNotRevoked() {
        verify(userAuthorityRepository, times(1)).revokeAuthority(AuthorityName.USER, 1L);
        verify(userAuthorityRepository, times(0)).getAuthoritiesByUserId(any());
        verify(userAuthorityRepository, times(0)).createAuthority(any(), any());

        verify(authorityJpaRepository, times(1)).findByName(ROLE);
        verify(userAuthorityJpaRepository, times(1))
                .findByUserIdAndAuthorityId(1L, authorityEntity.getId());
        verify(userAuthorityJpaRepository, times(0)).delete(any(UserAuthorityEntity.class));
        then(expectedException).isNull();
    }

    private void whenRevokingAuthority() {
        when(authorityJpaRepository.findByName(ROLE)).thenReturn(authorityEntity);
        when(userAuthorityJpaRepository.findByUserIdAndAuthorityId(1L, authorityEntity.getId()))
                .thenReturn(userAuthorityEntity);
        doNothing().when(userAuthorityJpaRepository).delete(any(UserAuthorityEntity.class));

        try {
            userAuthorityRepository.revokeAuthority(AuthorityName.USER, 1L);
        } catch (Exception exception) {
            expectedException = exception;
        }
    }

    private void thenAuthorityIsRevoked() {
        verify(userAuthorityRepository, times(1)).revokeAuthority(AuthorityName.USER, 1L);
        verify(userAuthorityRepository, times(0)).getAuthoritiesByUserId(any());
        verify(userAuthorityRepository, times(0)).createAuthority(any(), any());

        verify(authorityJpaRepository, times(1)).findByName(ROLE);
        verify(userAuthorityJpaRepository, times(1))
                .findByUserIdAndAuthorityId(1L, authorityEntity.getId());
        verify(userAuthorityJpaRepository, times(1)).delete(any(UserAuthorityEntity.class));
        then(expectedException).isNull();
    }

    private void whenCreatingAuthorityThatAlreadyExist() {
        when(authorityJpaRepository.findByName(ROLE)).thenReturn(authorityEntity);
        when(userAuthorityJpaRepository.findByUserIdAndAuthorityId(1L, authorityEntity.getId()))
                .thenReturn(userAuthorityEntity);
        try {
            userAuthorityRepository.createAuthority(AuthorityName.USER, 1L);
        } catch (Exception exception) {
            expectedException = exception;
        }
    }

    private void thenAuthorityIsNotCreated() {
        verify(userAuthorityRepository, times(0)).revokeAuthority(any(), any());
        verify(userAuthorityRepository, times(0)).getAuthoritiesByUserId(any());
        verify(userAuthorityRepository, times(1)).createAuthority(AuthorityName.USER, 1L);

        verify(authorityJpaRepository, times(1)).findByName(ROLE);
        verify(userAuthorityJpaRepository, times(1))
                .findByUserIdAndAuthorityId(1L, authorityEntity.getId());
        verify(userAuthorityJpaRepository, times(0)).save(any(UserAuthorityEntity.class));
        then(expectedException).isNull();
    }


    private void whenCreatingAuthority() {
        when(authorityJpaRepository.findByName(ROLE)).thenReturn(authorityEntity);
        when(userAuthorityJpaRepository.findByUserIdAndAuthorityId(1L, authorityEntity.getId()))
                .thenReturn(null);
        when(userAuthorityJpaRepository.save(any(UserAuthorityEntity.class))).thenReturn(userAuthorityEntity);

        try {
            userAuthorityRepository.createAuthority(AuthorityName.USER, 1L);
        } catch (Exception exception) {
            expectedException = exception;
        }

    }

    private void thenAuthorityIsCreated() {
        verify(userAuthorityRepository, times(0)).revokeAuthority(any(), any());
        verify(userAuthorityRepository, times(0)).getAuthoritiesByUserId(any());
        verify(userAuthorityRepository, times(1)).createAuthority(AuthorityName.USER, 1L);

        verify(authorityJpaRepository, times(1)).findByName(ROLE);
        verify(userAuthorityJpaRepository, times(1))
                .findByUserIdAndAuthorityId(1L, authorityEntity.getId());
        verify(userAuthorityJpaRepository, times(1)).save(any(UserAuthorityEntity.class));
        then(expectedException).isNull();
    }

    private void givenUserAuthorityRepository() {
        givenAuthorityJpaRepository();
        givenUserAuthorityJpaRepository();
        userAuthorityRepository = spy(new UserAuthorityRepository(authorityJpaRepository, userAuthorityJpaRepository));
    }

    private void givenAuthorityJpaRepository() {
        authorityJpaRepository = mock(AuthorityJpaRepository.class);
    }

    private void givenUserAuthorityJpaRepository() {
        userAuthorityJpaRepository = mock(UserAuthorityJpaRepository.class);
    }

    private void givenAuthorityEntity() {
        authorityEntity = AuthorityEntity.builder()
                .id(1L)
                .name("USER")
                .build();
    }

    private void givenUserAuthorityEntity() {
        userAuthorityEntity = UserAuthorityEntity.builder()
                .userId(1L)
                .authorityId(1L)
                .build();
    }
}
