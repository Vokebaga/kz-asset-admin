package com.example.demo3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home() {
        return "index";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }
}
