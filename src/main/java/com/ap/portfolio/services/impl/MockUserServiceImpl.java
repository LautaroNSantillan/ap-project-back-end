package com.ap.portfolio.services.impl;

import com.ap.portfolio.dtos.MockUserDTO;
import com.ap.portfolio.models.MockUser;
import com.ap.portfolio.repositories.MockUserRepository;
import com.ap.portfolio.services.MockUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MockUserServiceImpl implements MockUserService {
    @Autowired
    private MockUserRepository webUserRepository;

    @Override
    public Optional<MockUser> findById(long id) {
        return this.webUserRepository.findById(id);
    }
    @Override
    public ResponseEntity<?> disableUser(long id) {
        Optional<MockUser> userToDisable = this.webUserRepository.findById(id);
        if (userToDisable.isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        } else {
            userToDisable.get().setActive(false);
        }
        return new ResponseEntity<>("User disabled", HttpStatus.OK);
    }
    @Override
    public List<MockUser> findAll() {
        return this.webUserRepository.findAll();
    }
    @Override
    public MockUser createUser(MockUserDTO mockUserDTO) {
        return new MockUser(mockUserDTO.getName(), mockUserDTO.getLastName(), mockUserDTO.getBirthdate());
    }
    @Override
    public ResponseEntity<?> register(MockUser user) {
        try {
            this.webUserRepository.save(user);
            return new ResponseEntity<>("User Registered Successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.FORBIDDEN);
        }
    }
    @Override
    public ResponseEntity<?> findByIdDTO(long id){
        Optional<MockUser> user = this.webUserRepository.findById(id);
        if(user.isEmpty()){
            return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<>(new MockUserDTO(user.get()), HttpStatus.OK);
        }
    }
    @Override
    public void save(MockUser mockUser){
        this.webUserRepository.save(mockUser);
    }

    @Override
    public void delete(MockUser mockUser) {
        this.webUserRepository.delete(mockUser);
    }
}
