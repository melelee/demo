package com.melelee.swagger.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class DemoController {

    @GetMapping("/date")
    public LocalDateTime test(@RequestParam Long time) {
        return LocalDateTime.now().plusDays(time);
    }
}
