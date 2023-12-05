package com.melelee.thymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController {

    @GetMapping(value = "/demo")
    public String demo(Model model) {
        model.addAttribute("title", "后台返回的标题");
        return "demo";
    }

    @GetMapping(value = "/path")
    public String path(Model model) {
        return "path";
    }
}
