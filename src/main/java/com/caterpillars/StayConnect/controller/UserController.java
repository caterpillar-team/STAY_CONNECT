package com.caterpillars.StayConnect.controller;

import com.caterpillars.StayConnect.component.provider.JWTokenProvider;
import com.caterpillars.StayConnect.model.dto.UserSignUpDto;
import com.caterpillars.StayConnect.model.entities.Role;
import com.caterpillars.StayConnect.model.entities.User;
import com.caterpillars.StayConnect.model.repository.UserRepository;
import com.caterpillars.StayConnect.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Arrays;
import java.util.Optional;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private JWTokenProvider jwTokenProvider;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  @GetMapping("/myPage")
  public String editUser(Model model, HttpServletRequest request, Authentication authentication) {

    Cookie[] cookies = request.getCookies();
    String token = null;
    if (cookies != null) {
      token = Arrays.stream(cookies)
          .filter(cookie -> "jwt".equals(cookie.getName()))
          .map(Cookie::getValue)
          .findAny()
          .orElse(null);
    }
    log.info("token" + token);

    System.out.println("token : " + token);
    String username = jwTokenProvider.extractUsername(token);
    System.out.println("username : " + username);

    Optional<User> result = userRepository.findByUsername(username);

    log.info("mypage");

    if (result.isPresent()) {
      model.addAttribute("edit", result.get());
    } else {
      model.addAttribute("edit", new User()); // 빈 객체 추가
    }

//    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//    String user = userService.findUser(userDetails.getUsername());
//    model.addAttribute("user", user);
    return "pages/user/myPage";
  }

  @PostMapping("/myPage")
  public String editUserInfo(String username, String password, String phoneNumber, String email) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String currentUser = authentication.getName();
    Optional<User> optionalUser = userRepository.findByUsername(currentUser);
    if (optionalUser.isPresent()) {
      User user = optionalUser.get();
      user.setUsername(username);
      user.setPassword(passwordEncoder.encode(password));
      user.setPhoneNumber(phoneNumber);
      user.setEmail(email);

      userRepository.save(user);

      return "redirect:/";

    } else {

      return "errorPage";
    }
  }

}
