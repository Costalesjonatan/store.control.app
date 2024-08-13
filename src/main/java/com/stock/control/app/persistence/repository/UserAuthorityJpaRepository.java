package com.stock.control.app.persistence.repository;

import com.stock.control.app.persistence.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAuthorityJpaRepository extends JpaRepository<UserRole, Long> {

    UserRole findByUserIdAndRoleId(Long userId, Long roleId);

    List<UserRole> findByUserId(Long userId);

}
