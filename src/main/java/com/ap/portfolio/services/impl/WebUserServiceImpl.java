package com.ap.portfolio.services.impl;

import com.ap.portfolio.dtos.RegisterDTO;
import com.ap.portfolio.models.WebUser;
import com.ap.portfolio.repositories.WebUserRepository;
import com.ap.portfolio.security.dtos.NewUser;
import com.ap.portfolio.security.enums.RoleName;
import com.ap.portfolio.security.roles.IUser;
import com.ap.portfolio.security.roles.Role;
import com.ap.portfolio.security.services.RoleService;
import com.ap.portfolio.security.services.UserService;
import com.ap.portfolio.services.WebUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class WebUserServiceImpl implements WebUserService {
    @Autowired
    private WebUserRepository webUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;

    @Override
    public List<WebUser> getAll() {
        return this.webUserRepository.findAll();
    }


    @Override
    public Optional<WebUser> getById(int id) {
        return this.webUserRepository.findById(id);
    }

    @Override
    public Optional<WebUser> getByEmail(String email) {
        return this.webUserRepository.findByEmail(email);
    }

    @Override
    public void save(WebUser webUser) {
        this.webUserRepository.save(webUser);
    }

    @Override
    public boolean existsByEmail(String email) {
        return this.webUserRepository.existsByEmail(email);
    }

    @Override
    public boolean existsById(int id) {
        return this.webUserRepository.existsById(id);
    }

    @Override
    public void createUserAndAcc(RegisterDTO registerDTO) {
        String str = registerDTO.getName() + registerDTO.getLastName();
        String name = str.replaceAll("\\s+", "");
        NewUser newAcc = new NewUser(name, registerDTO.getUsername(), registerDTO.getPwd());

        IUser user = new IUser(newAcc.getName(), newAcc.getUsername(), this.passwordEncoder.encode(newAcc.getPassword()));

        Set<Role> roles = new HashSet<>();
        roles.add(this.roleService.getByRoleName(RoleName.ROLE_USER).get());

        user.setRoles(roles);

        this.userService.save(user);

        WebUser newWebUser = new WebUser(registerDTO.getName(),
                registerDTO.getLastName(),
                registerDTO.getEmail(),
                null,
                null,
                null);

        newWebUser.setAccount(user);
        user.setWebUser(newWebUser);

        this.save(newWebUser);
    }

}
