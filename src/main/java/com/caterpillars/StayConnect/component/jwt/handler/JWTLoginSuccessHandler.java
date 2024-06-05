package com.caterpillars.StayConnect.component.jwt.handler;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.caterpillars.StayConnect.component.jwt.provider.JWTokenProvider;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTLoginSuccessHandler implements AuthenticationSuccessHandler {

  @Autowired
  private JWTokenProvider jwTokenProvider;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    String token = jwTokenProvider.generateToken(authentication);
    Cookie cookie = new Cookie("jwt", token);
    cookie.setHttpOnly(true);
    cookie.setSecure(false);
    cookie.setPath("/");
    cookie.setMaxAge(jwTokenProvider.getExpiration() / 1000);

    response.addCookie(cookie);
    response.sendRedirect("/");
  }

}
