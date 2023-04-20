package com.ap.portfolio.security.controller;

import com.ap.portfolio.security.dtos.JwtDTO;
import com.ap.portfolio.security.dtos.LoginUser;
import com.ap.portfolio.security.dtos.NewUser;
import com.ap.portfolio.security.enums.RoleName;
import com.ap.portfolio.security.jwt.JWTProvider;
import com.ap.portfolio.security.roles.IUser;
import com.ap.portfolio.security.roles.Role;
import com.ap.portfolio.security.services.RoleService;
import com.ap.portfolio.security.services.UserService;
import com.ap.portfolio.utilities.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private JWTProvider jwtProvider;
    @Transactional
    @PostMapping("register")
    public ResponseEntity<?> newUser (@Valid @RequestBody NewUser newUser, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(new Message("Invalid Credentials"), HttpStatus.BAD_REQUEST);
        }
        if(userService.existsByUsername(newUser.getUsername())){
            return new ResponseEntity<>(new Message("User already exists"), HttpStatus.BAD_REQUEST);
        }
        IUser user = new IUser(newUser.getName(), newUser.getUsername(),passwordEncoder.encode(newUser.getPassword()));

        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getByRoleName(RoleName.ROLE_USER).get());

        if(newUser.getRoles().contains("ADMIN")){
            roles.add(roleService.getByRoleName(RoleName.ROLE_ADMIN).get());
        }

        user.setRoles(roles);

        this.userService.save(user);

        return new ResponseEntity<>(new Message("User saved"), HttpStatus.CREATED);
    }
    @Transactional
    @PostMapping("login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginUser loginUser, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(new Message("Invalid Credentials"), HttpStatus.BAD_REQUEST);
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginUser.getUsername(), loginUser.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);

        UserDetails userDetails =(UserDetails) authentication.getPrincipal();

        JwtDTO jwtDTO = new JwtDTO(jwt, userDetails.getUsername(), userDetails.getAuthorities());

        Authentication authentication2 = SecurityContextHolder.getContext().getAuthentication();
        System.out.print(authentication2.getName());

        return new ResponseEntity<>(jwtDTO, HttpStatus.OK);
    }
}
