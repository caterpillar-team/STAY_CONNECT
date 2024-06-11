package com.caterpillars.StayConnect.controller;

import com.caterpillars.StayConnect.component.provider.JWTokenProvider;
import com.caterpillars.StayConnect.model.entities.Role;
import com.caterpillars.StayConnect.model.entities.User;
import com.caterpillars.StayConnect.model.repository.UserRepository;
import com.caterpillars.StayConnect.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

  @GetMapping("/myPage")
  public String editUser(Model model, HttpServletRequest request) {

    Cookie[] cookies = request.getCookies();
    String token = null;
    if (cookies != null) {
      token = Arrays.stream(cookies)
          .filter(cookie -> "jwt".equals(cookie.getName()))
          .map(Cookie::getValue)
          .findAny()
          .orElse(null);
    }

    System.out.println("token : " + token);
    String username = jwTokenProvider.extractUsername(token);
    System.out.println("username : " + username);

    Optional<User> result = userRepository.findByUsername(username);

    log.info("mypage");

    if (result.isPresent()) {
      model.addAttribute("edit", result.get());
      return "pages/user/myPage";
    } else {
      return "pages/user/myPage";
    }

  }

  @PostMapping("/myPage")
  public String editUserInfo(String username, String password, String phoneNumber, String email) {
    User user = new User();

    user.setRealName(user.getRealName());
    user.setUsername(user.getUsername());
    user.setPassword(user.getPassword());
    user.setPhoneNumber(user.getPhoneNumber());
    user.setEmail(user.getEmail());
    user.setBirth(user.getBirth());
    user.setRole(Role.builder().build());

    userRepository.save(user);

    return "pages/user/myPage";
  }
}
