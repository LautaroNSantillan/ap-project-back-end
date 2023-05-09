package com.ap.portfolio.controllers;

import com.ap.portfolio.dtos.MockUserDTO;
import com.ap.portfolio.models.MockUser;
import com.ap.portfolio.services.MockUserService;
import com.ap.portfolio.utilities.Message;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/mock")
@CrossOrigin(origins = "https://portfoliotestfront.web.app")
public class MockUserController {
    @Autowired
    private MockUserService mockUserService;
    @GetMapping("all-users")
    public List<MockUser> findAllWebUsers(){
        return this.mockUserService.findAll();
    }
    @GetMapping("user/{id}")
    public ResponseEntity<?> findUserById(@PathVariable long id){
        return this.mockUserService.findByIdDTO(id);
    }
    @PostMapping("create-mock-user")
    public ResponseEntity<?> createMockUser(@RequestBody MockUserDTO mockUser){
        //VALIDACIONES
        this.mockUserService.save(this.mockUserService.createUser(mockUser));
        return new ResponseEntity<>(new Message("User Saved."), HttpStatus.CREATED);
    }
    @PostMapping("edit-mock-user")
    public ResponseEntity<?> editMockUser(@RequestBody MockUserDTO mockUser){
        Optional<MockUser> mockUserToModify = this.mockUserService.findById(mockUser.getId());

        if(mockUserToModify.isEmpty()){
            return new ResponseEntity<>(new Message("Mock user not found."), HttpStatus.BAD_REQUEST);
        }

        StringBuilder sb = new StringBuilder().append("Modified: ");
        if(StringUtils.isNotBlank(mockUser.getName())){
            mockUserToModify.get().setName(mockUser.getName());
            sb.append("name, ");
        }
        if(StringUtils.isNotBlank(mockUser.getLastName())){
            mockUserToModify.get().setLastName(mockUser.getLastName());
            sb.append("last name, ");
        }
        if(mockUser.getBirthdate()!=null){
            mockUserToModify.get().setBirthdate(mockUser.getBirthdate());
            sb.append("birthdate, ");
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append(".");

        this.mockUserService.save(mockUserToModify.get());

        return new ResponseEntity<>(new Message(sb.toString()), HttpStatus.CREATED);
    }
    @DeleteMapping("delete-mock-user/{id}")
    public ResponseEntity<?> editMockUser(@PathVariable int id){
       Optional<MockUser> mockUser = this.mockUserService.findById(id);

       if(mockUser.isEmpty()){
           return new ResponseEntity<>(new Message("User not found."), HttpStatus.BAD_REQUEST);
       }

       this.mockUserService.delete(mockUser.get());

        return new ResponseEntity<>(new Message("User deleted."), HttpStatus.CREATED);
    }
}
