package com.melelee.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class DemoController {
    @GetMapping("/get")
    @PreAuthorize("hasAnyAuthority('admin','super','sys:dept:list')")
    public String get() {
        return "get";
    }

    @Autowired
    AuthenticationManager authenticationManager;

    //可以用redis的超时机制
    public static final Map<String, User> REDIS = new ConcurrentHashMap<>();

    @PostMapping("/demo/login")
    public String login(@RequestBody Map<String, String> map) {

        String username = map.get("username");
        String password = map.get("password");
        Authentication request = new UsernamePasswordAuthenticationToken(username, password);
        Authentication result = authenticationManager.authenticate(request);
        if (result == null) {
            return "fail";
        }
        Object principal = result.getPrincipal();
        User user = (User) principal;

        REDIS.put(user.getUsername(), user);

        return JWT.create().withClaim("username", username).withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 1000)).sign(Algorithm.HMAC256(password));
    }

    @PostMapping("/demo/logout")
    public String logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        String username = user.getUsername();
        REDIS.remove(username);
        return "success";
    }
}
