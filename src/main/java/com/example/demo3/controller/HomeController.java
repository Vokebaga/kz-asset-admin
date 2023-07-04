package com.example.demo3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home() {
        // Здесь можно добавить логику для отображения главной страницы после успешной аутентификации.
        return "index";
    }

    @GetMapping("/logout")
    public String logout() {
        // Здесь выполняется выход пользователя. Вы можете добавить свою логику выхода.

        // После выхода, перенаправляем на страницу логина.
        return "redirect:/login";
    }
}
