package com.ap.portfolio.controllers;

import com.ap.portfolio.dtos.MockUserDTO;
import com.ap.portfolio.models.MockUser;
import com.ap.portfolio.services.MockUserService;
import com.ap.portfolio.utilities.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mock")
public class MockUserController {
    @Autowired
    private MockUserService webUserService;
    @GetMapping("all-users")
    public List<MockUser> findAllWebUsers(){
        return this.webUserService.findAll();
    }
    @GetMapping("user/{id}")
    public ResponseEntity<?> findUserById(@PathVariable long id){
        return this.webUserService.findByIdDTO(id);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("create-mock-user")
    public ResponseEntity<?> createMockUser(@RequestBody MockUserDTO mockUser){
        this.webUserService.save(this.webUserService.createUser(mockUser));
        return new ResponseEntity<>(new Message("User Saved"), HttpStatus.CREATED);
    }
}
