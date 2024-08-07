package com.stock.control.app.domain.protocol;

import com.stock.control.app.utils.AuthorityName;

import java.util.List;

public interface AuthorityProtocolRepository {
    void createAuthority(AuthorityName authority, Long userId);
    void revokeAuthority(AuthorityName authority, Long userId);
    List<String> getAuthoritiesByUserId(Long userId);
}
