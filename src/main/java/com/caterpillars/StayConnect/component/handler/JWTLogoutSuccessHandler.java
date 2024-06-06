package com.caterpillars.StayConnect.component.handler;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTLogoutSuccessHandler implements LogoutSuccessHandler {

  @Override
  public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException, ServletException {
    Cookie cookie = new Cookie("jwt", null);
    cookie.setHttpOnly(true);
    cookie.setSecure(false); // HTTPS 환경에서만 작동하도록 설정
    cookie.setPath("/"); // 모든 경로에서 쿠키 접근 가능
    cookie.setMaxAge(0); // 쿠키 제거

    response.addCookie(cookie);
    response.setStatus(HttpServletResponse.SC_OK);
    response.sendRedirect("/auth/signin?logout"); // 로그아웃 후 리다이렉트
  }

}
