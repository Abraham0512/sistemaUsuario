package com.demo.login.iniciosesion.service;

import org.springframework.security.core.userdetails.UserDetails;


public interface JwtService {

    String getUsername(String jwt);

    boolean valideToken(String jwt, UserDetails userDetails);
}