package com.stock.control.app.persistence.repository;

import com.stock.control.app.persistence.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityJpaRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
