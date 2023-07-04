package com.example.demo3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {

    @GetMapping("/login")
    public String login() {
        return "login"; // returns the login page (login.html or login.jsp, depending on your setup)
    }
    @GetMapping("/file")
    public String secureFileList() {
        return "uploadForm"; // returns the login page (login.html or login.jsp, depending on your setup)
    }
    @GetMapping("/")
    public String home() {
        return "redirect:/login"; // Redirects to the login page on the home URL ("/")
    }
}
