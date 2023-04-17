package com.ap.portfolio.controllers;

import com.ap.portfolio.dtos.MockUserDTO;
import com.ap.portfolio.models.MockUser;
import com.ap.portfolio.services.MockUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class IUserController {
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
    public String createMockUser(@RequestBody MockUserDTO mockUser){
        this.webUserService.save(this.webUserService.createUser(mockUser));
        return "User Saved";
    }
}
