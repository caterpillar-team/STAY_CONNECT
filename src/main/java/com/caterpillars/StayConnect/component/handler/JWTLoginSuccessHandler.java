package com.caterpillars.StayConnect.component.handler;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.caterpillars.StayConnect.component.provider.JWTokenProvider;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * JWT 로그인 성공 핸들러 클래스입니다.
 * 인증 성공 시 JWT 토큰을 생성하고 쿠키에 추가합니다.
 */
@Component
public class JWTLoginSuccessHandler implements AuthenticationSuccessHandler {

  @Autowired
  private JWTokenProvider jwTokenProvider;

  private static final int MILLISECONDS_IN_SECOND = 1000;

  /**
   * 인증 성공 시 호출되는 메서드입니다.
   *
   * @param request HTTP 요청
   * @param response HTTP 응답
   * @param authentication 인증 정보
   * @throws IOException 입출력 예외
   * @throws ServletException 서블릿 예외
   */
  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    String token = jwTokenProvider.generateToken(authentication);
    Cookie jwtCookie = createJwtCookie(token);
    response.addCookie(jwtCookie);
    response.sendRedirect("/");
  }

  /**
   * JWT 쿠키를 생성합니다.
   *
   * @param token JWT 토큰
   * @return 생성된 쿠키
   */
  private Cookie createJwtCookie(String token) {
    Cookie cookie = new Cookie("jwt", token);
    cookie.setHttpOnly(true);
    cookie.setSecure(false);
    cookie.setPath("/");
    cookie.setMaxAge(jwTokenProvider.getExpiration() / MILLISECONDS_IN_SECOND);
    return cookie;
  }
}