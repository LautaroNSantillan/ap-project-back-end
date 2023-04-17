package com.ap.portfolio.security.services;

import com.ap.portfolio.security.repositories.IUserRepository;
import com.ap.portfolio.security.roles.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService {
    @Autowired
    private IUserRepository iUserRepository;

    public Optional<IUser> getByUsername(String username){
        return this.iUserRepository.findByUsername(username);
    }
    public boolean existsByUsername(String username){
        return this.iUserRepository.existsByUsername(username);
    }
    public boolean existsByEmail(String email){
        return this.iUserRepository.existsByEmail(email);
    }
    public void save(IUser user){
        this.iUserRepository.save(user);
    }
}
