package com.ap.portfolio.security.services;

import com.ap.portfolio.security.enums.RoleName;
import com.ap.portfolio.security.repositories.IRoleRepository;
import com.ap.portfolio.security.roles.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class RoleService {
    @Autowired
    private IRoleRepository roleRepository;

    public Optional<Role> getByRoleName(RoleName roleName){
        return this.roleRepository.findByRoleName(roleName);
    }
    public void save(Role role){
        this.roleRepository.save(role);
    }
}
