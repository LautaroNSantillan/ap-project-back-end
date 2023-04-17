package com.ap.portfolio.security.services;

import com.ap.portfolio.security.roles.IUser;
import com.ap.portfolio.utilities.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        IUser user = this.userService.getByUsername(username).get();
        return Utils.buildMainUser(user);
    }
}