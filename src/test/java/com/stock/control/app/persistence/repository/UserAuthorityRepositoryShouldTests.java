package com.stock.control.app.persistence.repository;

import com.stock.control.app.persistence.entity.Role;
import com.stock.control.app.persistence.entity.UserRole;
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
    private Role role;
    private UserRole userRole;
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
        when(userAuthorityJpaRepository.findByUserId(1L)).thenReturn(List.of(userRole));
        when(authorityJpaRepository.findById(userRole.getRoleId()))
                .thenReturn(Optional.of(role));
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
        verify(authorityJpaRepository, times(1)).findById(userRole.getRoleId());
        then(expectedException).isNull();
        then(expectedAuthorities.size()).isEqualTo(1);
        then(expectedAuthorities.get(0)).isEqualTo(ROLE);
    }

    private void whenRevokingAuthorityAlreadyRevoked() {
        when(authorityJpaRepository.findByName(ROLE)).thenReturn(role);
        when(userAuthorityJpaRepository.findByUserIdAndRoleId(1L, role.getId()))
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
                .findByUserIdAndRoleId(1L, role.getId());
        verify(userAuthorityJpaRepository, times(0)).delete(any(UserRole.class));
        then(expectedException).isNull();
    }

    private void whenRevokingAuthority() {
        when(authorityJpaRepository.findByName(ROLE)).thenReturn(role);
        when(userAuthorityJpaRepository.findByUserIdAndRoleId(1L, role.getId()))
                .thenReturn(userRole);
        doNothing().when(userAuthorityJpaRepository).delete(any(UserRole.class));

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
                .findByUserIdAndRoleId(1L, role.getId());
        verify(userAuthorityJpaRepository, times(1)).delete(any(UserRole.class));
        then(expectedException).isNull();
    }

    private void whenCreatingAuthorityThatAlreadyExist() {
        when(authorityJpaRepository.findByName(ROLE)).thenReturn(role);
        when(userAuthorityJpaRepository.findByUserIdAndRoleId(1L, role.getId()))
                .thenReturn(userRole);
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
                .findByUserIdAndRoleId(1L, role.getId());
        verify(userAuthorityJpaRepository, times(0)).save(any(UserRole.class));
        then(expectedException).isNull();
    }


    private void whenCreatingAuthority() {
        when(authorityJpaRepository.findByName(ROLE)).thenReturn(role);
        when(userAuthorityJpaRepository.findByUserIdAndRoleId(1L, role.getId()))
                .thenReturn(null);
        when(userAuthorityJpaRepository.save(any(UserRole.class))).thenReturn(userRole);

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
                .findByUserIdAndRoleId(1L, role.getId());
        verify(userAuthorityJpaRepository, times(1)).save(any(UserRole.class));
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
        role = Role.builder()
                .id(1L)
                .name("USER")
                .build();
    }

    private void givenUserAuthorityEntity() {
        userRole = UserRole.builder()
                .userId(1L)
                .roleId(1L)
                .build();
    }
}
