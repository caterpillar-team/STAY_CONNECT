package com.caterpillars.StayConnect.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.caterpillars.StayConnect.dto.UserSignUpDto;
import com.caterpillars.StayConnect.model.User;
import com.caterpillars.StayConnect.service.AuthService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {
  private final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private AuthService userService;

  @GetMapping("/signin")
  public String getSignIn() {
    return "pages/auth/signIn";
  }

  @GetMapping("/signup")
  public String getSignUp(Model model) {
    model.addAttribute("user", new UserSignUpDto());
    return "pages/auth/signUp";
  }

  @PostMapping("/signup")
  public String signUp(@ModelAttribute("user") @Valid UserSignUpDto signUpDto, BindingResult result, Model model,
      RedirectAttributes redirectAttributes) {

    if (result.hasErrors()) { // @Valid에 오류가 있을 시 formErrors 키워드로 에러 메시지들을 signUp에 반환
      model.addAttribute("formErrors", result.getAllErrors());
      return "pages/auth/signUp";
    }

    User user = userService.signUp(signUpDto);
    log.info(user.toString());

    redirectAttributes.addFlashAttribute("success", true);
    return "redirect:/auth/signin";
  }
}
