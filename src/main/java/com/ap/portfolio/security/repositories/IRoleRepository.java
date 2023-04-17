package com.ap.portfolio.security.repositories;

import com.ap.portfolio.security.enums.RoleName;
import com.ap.portfolio.security.roles.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRoleName(RoleName roleName);
}
