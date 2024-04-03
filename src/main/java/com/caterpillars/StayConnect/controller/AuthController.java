package com.caterpillars.StayConnect.controller;

import com.caterpillars.StayConnect.dto.UserSignUpDto;
import com.caterpillars.StayConnect.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
        model.addAttribute("user", new UserSignUpDto());
        return "auth/signup";
    }

    @PostMapping("signup")
    public String signUp(@Valid @ModelAttribute("user") UserSignUpDto userSignUpDto, BindingResult bindingResult, Model model) {

            authService.register(userSignUpDto);
        return "redirect:/";
    }

    @GetMapping("/signin")
    public String signin() {
        return "auth/signin";
    }
}
