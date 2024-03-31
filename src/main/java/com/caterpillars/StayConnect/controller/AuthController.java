package com.caterpillars.StayConnect.controller;

import com.caterpillars.StayConnect.model.User;
import com.caterpillars.StayConnect.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/signup")
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new User());
        return "auth/signup";
    }

    @PostMapping("signup")
    public String signUp(@ModelAttribute("user") User user) {
        authService.register(user);
        return "redirect:/";
    }
}
