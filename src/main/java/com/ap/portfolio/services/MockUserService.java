package com.ap.portfolio.services;

import com.ap.portfolio.dtos.MockUserDTO;
import com.ap.portfolio.models.MockUser;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface MockUserService {
    List<MockUser> findAll();
    Optional<MockUser> findById(long id);
    ResponseEntity<?> findByIdDTO(long id);
    Optional<MockUser> findByEmail(String email);
    ResponseEntity<?> disableUser(long id);
    MockUser createUser(MockUserDTO mockUserDTO);
    ResponseEntity<?> register(MockUser user);
    void save(MockUser mockUser);
}
