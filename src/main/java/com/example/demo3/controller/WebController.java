package com.example.demo3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/update")
    public String secureUpdate() {
        return "update";
    }
    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }
}
