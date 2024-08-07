package com.stock.control.app.persistence.repository;

import com.stock.control.app.persistence.entity.UserAuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAuthorityJpaRepository extends JpaRepository<UserAuthorityEntity, Long> {

    UserAuthorityEntity findByUserIdAndAuthorityId(Long userId, Long authorityId);

    List<UserAuthorityEntity> findByUserId(Long userId);

}
