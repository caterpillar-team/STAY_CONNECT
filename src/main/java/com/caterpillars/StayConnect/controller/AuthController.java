package com.caterpillars.StayConnect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.caterpillars.StayConnect.model.dto.UserSignUpDto;
import com.caterpillars.StayConnect.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/signin")
    public ModelAndView getSignIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            return new ModelAndView("redirect:/");
        }
        return new ModelAndView("pages/auth/signIn");
    }

    @GetMapping("/signup")
    public String getSignUp(HttpServletRequest httpServletRequest, Model model) {

        String realName = httpServletRequest.getAttribute("realName") != null
                ? httpServletRequest.getAttribute("realName").toString()
                : "";
        String phoneNumber = httpServletRequest.getAttribute("phoneNumber") != null
                ? httpServletRequest.getAttribute("phoneNumber").toString()
                : "";

        UserSignUpDto userSignUpDto = new UserSignUpDto();

        if (realName != null && phoneNumber != null) {
            userSignUpDto.setRealName(realName);
            userSignUpDto.setPhoneNumber(phoneNumber);
        }

        model.addAttribute("user", userSignUpDto);

        return "pages/auth/signup";
    }

    @PostMapping("/signup")
    public String signUp(@ModelAttribute("user") @Valid UserSignUpDto signUpDto, BindingResult result, Model model,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("formErrors", result.getAllErrors());
            return "pages/auth/signup";
        }

        authService.signUp(signUpDto);

        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:/auth/signIn";
    }
}
