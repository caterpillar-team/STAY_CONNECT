package com.caterpillars.StayConnect.component.handler;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * JWT 로그아웃 성공 핸들러 클래스입니다.
 * 로그아웃 성공 시 JWT 쿠키를 제거합니다.
 */
@Component
public class JWTLogoutSuccessHandler implements LogoutSuccessHandler {

  /**
   * 로그아웃 성공 시 호출되는 메서드입니다.
   *
   * @param request HTTP 요청
   * @param response HTTP 응답
   * @param authentication 인증 정보
   * @throws IOException 입출력 예외
   * @throws ServletException 서블릿 예외
   */
  @Override
  public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException, ServletException {
    Cookie jwtCookie = createLogoutCookie();
    response.addCookie(jwtCookie);
    response.setStatus(HttpServletResponse.SC_OK);
    response.sendRedirect("/auth/signin?logout");
  }

  /**
   * 로그아웃 시 JWT 쿠키를 제거하는 쿠키를 생성합니다.
   *
   * @return 제거된 쿠키
   */
  private Cookie createLogoutCookie() {
    Cookie cookie = new Cookie("jwt", null);
    cookie.setHttpOnly(true);
    cookie.setSecure(false); // HTTPS 환경에서만 작동하도록 설정
    cookie.setPath("/"); // 모든 경로에서 쿠키 접근 가능
    cookie.setMaxAge(0); // 쿠키 제거
    return cookie;
  }
}