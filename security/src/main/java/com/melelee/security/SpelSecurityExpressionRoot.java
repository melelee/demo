package com.melelee.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component(value = "spel")
public class SpelSecurityExpressionRoot {

    public boolean hasAuthority(String source) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(s -> Objects.equals(s, source));
    }
}
