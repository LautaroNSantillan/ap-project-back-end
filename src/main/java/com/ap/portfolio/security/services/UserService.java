package com.ap.portfolio.security.services;

import com.ap.portfolio.security.dtos.NewUser;
import com.ap.portfolio.security.enums.RoleName;
import com.ap.portfolio.security.repositories.IUserRepository;
import com.ap.portfolio.security.roles.IUser;
import com.ap.portfolio.security.roles.Role;
import com.ap.portfolio.utilities.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
    public void save(IUser user){
        this.iUserRepository.save(user);
    }
}
