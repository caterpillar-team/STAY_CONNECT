package com.caterpillars.StayConnect.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.caterpillars.StayConnect.custom.PrincipalDetails;
import com.caterpillars.StayConnect.model.dto.ReservationDto;
import com.caterpillars.StayConnect.model.entities.User;
import com.caterpillars.StayConnect.model.repository.UserRepository;
import com.caterpillars.StayConnect.service.ReservationService;
import com.caterpillars.StayConnect.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private UserService userService;

    @GetMapping("/myPage")
    public String editUser(Model model, HttpServletRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalDetails userDetails = (PrincipalDetails) authentication.getPrincipal();

        Optional<User> result = userRepository.findById(userDetails.getId());

        if (result.isPresent()) {
            User user = result.get();
            model.addAttribute("edit", user);
            model.addAttribute("userId", user.getId());

            List<ReservationDto> reservations = reservationService.getReservationsByUserId(user.getId());
            model.addAttribute("reservation", reservations);
        } else {
            model.addAttribute("edit", new User());
            model.addAttribute("userId", null);
        }

        return "pages/user/myPage";
    }

    @PostMapping("/myPage")
    public String editUserInfo(HttpServletRequest request, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = authentication.getName();
        Optional<User> optionalUser = userRepository.findByUsername(currentUser);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String repassword = request.getParameter("repassword");
            String phoneNumber = request.getParameter("phoneNumber");
            String email = request.getParameter("email");

            user.setUsername(username);

            if (password != null && !password.isEmpty() && password.equals(repassword)) {
                user.setPassword(passwordEncoder.encode(password));
            }

            user.setPhoneNumber(phoneNumber);
            user.setEmail(email);

            userRepository.save(user);

            return "redirect:/user/myPage";
        } else {
            model.addAttribute("error", "잘못된 접근입니다.");
            return "pages/user/myPage";
        }
    }

    @PostMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response,
            Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent() && optionalUser.get().getUsername().equals(currentUsername)) {
            userService.deleteUserById(id);
            SecurityContextHolder.clearContext();

            Cookie cookie = new Cookie("jwt", null);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setMaxAge(0);
            response.addCookie(cookie);

            return "redirect:/";
        } else {
            model.addAttribute("error", "회원 탈퇴 중 오류가 발생했습니다.");
            return "pages/user/myPage";
        }
    }

}