package com.melelee.oauth2;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {

    @GetMapping("/resource/test")
    @PreAuthorize("hasAuthority('sys:dept:list')")
    public String test() {
        return "test";
    }
}
