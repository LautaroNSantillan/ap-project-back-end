package com.ap.portfolio.repositories;

import com.ap.portfolio.models.WebUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WebUserRepository extends JpaRepository<WebUser, Integer> {
    Optional<WebUser> findByName(String name);
    Optional<WebUser> findByEmail(String email);
    boolean existsByName(String name);
    boolean existsByEmail(String email);
}
