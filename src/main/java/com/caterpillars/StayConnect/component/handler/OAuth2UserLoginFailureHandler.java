package com.caterpillars.StayConnect.component.handler;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class OAuth2UserLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException exception) throws IOException, ServletException {

    String realName = request.getAttribute("realName").toString();
    String phoneNumber = request.getAttribute("phoneNumber").toString();

    request.setAttribute("realName", realName);
    request.setAttribute("phoneNumber", phoneNumber);

    request.getRequestDispatcher("/auth/signup").forward(request, response);
  }
}
