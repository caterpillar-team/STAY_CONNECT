package com.caterpillars.StayConnect.controller;

import java.util.Arrays;
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

import com.caterpillars.StayConnect.component.provider.JWTokenProvider;
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
    private JWTokenProvider jwTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // @Autowired
    // private ReservationRepository reservationRepository;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private UserService userService;

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
        log.info("token: " + token);

        String username = jwTokenProvider.extractUsername(token);

        Optional<User> result = userRepository.findByUsername(username);

        if (result.isPresent()) {
            User user = result.get();
            model.addAttribute("edit", user);
            model.addAttribute("userId", user.getId());

            // 사용자의 예약 목록 추가하기
            List<ReservationDto> reservations = reservationService.getReservationsByUserId(user.getId());
            model.addAttribute("reservation", reservations);
        } else {
            model.addAttribute("edit", new User()); // 빈 객체 추가
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

            // 비밀번호가 입력되었고 두 비밀번호가 일치하는 경우에만 비밀번호 변경
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
            SecurityContextHolder.clearContext(); // 로그아웃 처리

            // JWT 쿠키 삭제
            Cookie cookie = new Cookie("jwt", null);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setMaxAge(0); // 쿠키 유효 기간을 0으로 설정하여 즉시 만료
            response.addCookie(cookie);

            return "redirect:/"; // 회원 탈퇴 후 메인 페이지로 리다이렉트
        } else {
            model.addAttribute("error", "회원 탈퇴 중 오류가 발생했습니다.");
            return "pages/user/myPage";
        }
    }

}