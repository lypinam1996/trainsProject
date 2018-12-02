package com.tsystems.trainsProject.services.impl;

import com.tsystems.trainsProject.models.UserEntity;
import com.tsystems.trainsProject.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    private static final Logger logger = Logger.getLogger(UserDetailsServiceImpl.class);

    public UserDetails loadUserByUsername(String email) {
        try {
            UserEntity user = userService.findByLogin(email);
            Set<GrantedAuthority> roles = new HashSet();
            roles.add(new SimpleGrantedAuthority(user.getRole().getTitle()));
            UserDetails userDetails =
                    new org.springframework.security.core.userdetails.User(user.getLogin(),
                            user.getPassword(),
                            roles);
            return userDetails;
        } catch (UsernameNotFoundException unfe) {
            logger.info(unfe.getMessage());
            return null;
        }
    }
}