package com.ap.portfolio.security.repositories;

import com.ap.portfolio.security.roles.IUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<IUser, Integer> {
    Optional<IUser> findByUsername(String username);
    boolean existsByUsername(String username);

}
