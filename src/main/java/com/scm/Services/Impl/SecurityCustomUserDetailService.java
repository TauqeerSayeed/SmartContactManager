package com.scm.Services.Impl;

import com.scm.Repositories.UserRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityCustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepositories userRepositories;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //user ko load krana h
        return userRepositories.findByEmailId(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found with email : "+username));
    }
}
