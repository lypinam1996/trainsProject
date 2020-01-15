package com.tsystems.trainsProject.services.impl;

import com.tsystems.trainsProject.models.UserEntity;
import com.tsystems.trainsProject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class JwtUserDetailsService implements UserDetailsService
{
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        try {
            UserEntity user = userService.findByLogin(username);
            Set<GrantedAuthority> roles = new HashSet();
            roles.add(new SimpleGrantedAuthority(user.getRole().getTitle()));
            return new User(user.getLogin(), user.getPassword(), roles);
        }
        catch (Exception e){
            return new User("1","1", new HashSet<>());
        }
    }
}
