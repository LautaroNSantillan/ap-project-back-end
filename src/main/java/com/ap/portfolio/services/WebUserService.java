package com.ap.portfolio.services;

import com.ap.portfolio.dtos.RegisterDTO;
import com.ap.portfolio.models.WebUser;

import java.util.List;
import java.util.Optional;

public interface WebUserService {
    List<WebUser> getAll();
    Optional<WebUser> getById(int id);
    Optional<WebUser> getByEmail(String email);
    void save(WebUser webUser);
    boolean existsByEmail(String email);
    boolean existsById(int id);
    void createUserAndAcc(RegisterDTO registerDTO);
}
