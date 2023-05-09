package com.ap.portfolio.controllers;

import com.ap.portfolio.dtos.ExperienceDTO;
import com.ap.portfolio.dtos.MeDTO;
import com.ap.portfolio.dtos.RegisterDTO;
import com.ap.portfolio.dtos.WebUserDTO;
import com.ap.portfolio.models.Experience;
import com.ap.portfolio.models.Skill;
import com.ap.portfolio.models.WebUser;
import com.ap.portfolio.security.controller.AuthController;
import com.ap.portfolio.security.dtos.NewUser;
import com.ap.portfolio.security.enums.RoleName;
import com.ap.portfolio.security.roles.IUser;
import com.ap.portfolio.security.roles.Role;
import com.ap.portfolio.security.services.UserService;
import com.ap.portfolio.services.WebUserService;
import com.ap.portfolio.utilities.Message;
import com.ap.portfolio.utilities.Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/web-user")
@CrossOrigin(origins = "https://portfoliotestfront.web.app")
public class WebUserController {
    @Autowired
    private WebUserService webUserService;
    @Autowired
    private UserService userService;
    @GetMapping("all-users")
    public ResponseEntity<List<WebUserDTO>> getAllDTO() {
        List<WebUserDTO> list = this.webUserService.getAll().stream().map(WebUserDTO::new).collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    @GetMapping("get-web-user/{id}")
    public ResponseEntity<?> getUser(@PathVariable int id) {
        if(!this.webUserService.existsById(id)){
            return new ResponseEntity<>(new Message("User not found"), HttpStatus.BAD_REQUEST);
        }
        WebUserDTO webUserDTO = new WebUserDTO((this.webUserService.getById(id).get()));
        return new ResponseEntity<>(webUserDTO, HttpStatus.OK);
    }
    @GetMapping("get-me")
    public ResponseEntity<?> getUser() {
        MeDTO meDTO = new MeDTO(this.webUserService.getByEmail("launsantillan@gmail.com").get());
        return new ResponseEntity<>(meDTO, HttpStatus.OK);
    }
    @GetMapping("get-current-user-id")
    public ResponseEntity<Integer> getUserId(){
        return new ResponseEntity<>(Utils.getCurrentUser(this.userService).getWebUser().getId(), HttpStatus.OK);
    }
    @GetMapping("get-is-admin")
    public ResponseEntity<?> getIsAdmin(){
        IUser currentUser = Utils.getCurrentUser(this.userService);
        boolean isAdmin = currentUser.getRoles().stream()
                .map(Role::getRoleName)
                .anyMatch(roleName -> roleName == RoleName.ROLE_ADMIN);
        return new ResponseEntity<>(isAdmin, HttpStatus.OK);
    }

    @PostMapping("create-web-user")
    public ResponseEntity<?> createUser(@RequestBody  RegisterDTO registerDTO){
        if(StringUtils.isBlank(registerDTO.getName())){
            return new ResponseEntity<>(new Message("Missing Name"),HttpStatus.BAD_REQUEST);
        }if(StringUtils.isBlank(registerDTO.getLastName())){
            return new ResponseEntity<>(new Message("Missing Name"),HttpStatus.BAD_REQUEST);
        }
        if(this.userService.existsByUsername(registerDTO.getUsername())){
            return new ResponseEntity<>(new Message("Username already in use"),HttpStatus.BAD_REQUEST);
        }
        if(StringUtils.isBlank(registerDTO.getUsername())){
            return new ResponseEntity<>(new Message("Missing Username"),HttpStatus.BAD_REQUEST);
        }
        if(StringUtils.isBlank(registerDTO.getEmail())){
            return new ResponseEntity<>(new Message("Missing Email"),HttpStatus.BAD_REQUEST);
        }
        if(this.webUserService.existsByEmail(registerDTO.getEmail())){
            return new ResponseEntity<>(new Message("Email already in use"),HttpStatus.BAD_REQUEST);
        }
        if(StringUtils.isBlank(registerDTO.getPwd())){
            return new ResponseEntity<>(new Message("Missing Password"),HttpStatus.BAD_REQUEST);
        }

        this.webUserService.createUserAndAcc(registerDTO);

        return new ResponseEntity<>(new Message("User Registered"), HttpStatus.CREATED);
    }

    @PatchMapping("/update-web-user/{id}")
    public ResponseEntity<?> modifyExp (@PathVariable int id, @RequestBody WebUserDTO webUserDTO) {

        StringBuilder sb = new StringBuilder();
        sb.append("Modified:  ");

        if(!this.webUserService.existsById(id)){
            return new ResponseEntity<>(new Message("User not found"), HttpStatus.BAD_REQUEST);
        }

        WebUser webUser = this.webUserService.getById(id).get();

        if(!StringUtils.isBlank(webUserDTO.getName())&& !Objects.equals(webUser.getName(),(webUserDTO.getName()))){
            webUser.setName(webUserDTO.getName());
            sb.append("name, ");
        }
        if(!StringUtils.isBlank(webUserDTO.getLastName())&& !Objects.equals(webUser.getLastName(),(webUserDTO.getLastName()))){
            webUser.setLastName(webUserDTO.getLastName());
            sb.append("last name, ");
        }
        if(!StringUtils.isBlank(webUserDTO.getEmail()) && !Objects.equals(webUser.getEmail(),(webUserDTO.getEmail()))){
            if(this.webUserService.existsByEmail(webUserDTO.getEmail())){
                return new ResponseEntity<>(new Message("Email already in use"), HttpStatus.BAD_REQUEST);
            }
            webUser.setName(webUserDTO.getEmail());
            sb.append("email, ");
        }
        if(!StringUtils.isBlank(webUserDTO.getImg())&& !Objects.equals(webUser.getImg(), webUserDTO.getImg())){
            webUser.setImg(webUserDTO.getImg());
            sb.append("image, ");
        }
        if(!StringUtils.isBlank(webUserDTO.getAbout())&& !Objects.equals(webUser.getAbout(),(webUserDTO.getAbout()))){
            webUser.setAbout(webUserDTO.getAbout());
            sb.append("about, ");
        }

        sb.setLength(sb.length() - 2);

        this.webUserService.save(webUser);

        return new ResponseEntity<>(new Message(sb.toString()), HttpStatus.OK);
    }
    @GetMapping("current-web-user")
    public ResponseEntity<?> getCurrentUser(){
        WebUserDTO webUserDTO = new WebUserDTO(this.userService.findWebUserByUsername(Utils.getCurrentUser(this.userService).getUsername()));
        return new ResponseEntity<>(webUserDTO, HttpStatus.OK);
    }
}
