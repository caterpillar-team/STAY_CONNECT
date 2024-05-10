package com.caterpillars.StayConnect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.caterpillars.StayConnect.dto.UserSignUpDto;
import com.caterpillars.StayConnect.service.UserService;

import jakarta.validation.Valid;

@Controller
public class UserController {
  // private final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private UserService userService;

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

    userService.signUp(signUpDto);
    redirectAttributes.addFlashAttribute("success", true);
    return "redirect:/signin";
  }
}
