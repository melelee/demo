package com.melelee.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class DemoController {
    @GetMapping("/get")
    public String get() {
        return "get";
    }

    @Autowired
    AuthenticationManager authenticationManager;

    public static final Map<String, String> MAP = new ConcurrentHashMap<>();

    @PostMapping("/demo/login")
    public String get(@RequestBody Map<String, String> map) {

        Authentication request = new UsernamePasswordAuthenticationToken(map.get("username"), map.get("password"));
        Authentication result = authenticationManager.authenticate(request);
        if (result == null) {
            return "fail";
        }
        Object principal = result.getPrincipal();
        User user = (User) principal;

        String token = UUID.randomUUID().toString();

        MAP.put(token, user.getUsername());
        return token;
    }
}
