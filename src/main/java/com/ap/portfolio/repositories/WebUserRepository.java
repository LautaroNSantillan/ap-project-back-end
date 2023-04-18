package com.ap.portfolio.repositories;

import com.ap.portfolio.models.MockUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WebUserRepository extends JpaRepository <MockUser, Long>{
    Optional<MockUser> findById(long id);
    Optional<MockUser> findByEmail(String email);
}