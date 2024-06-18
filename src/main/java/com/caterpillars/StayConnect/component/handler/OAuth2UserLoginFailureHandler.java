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

    log.info(request.getAttribute("realName").toString());
    log.info(request.getAttribute("phoneNumber").toString());

    String realName = request.getAttribute("realName").toString();
    String phoneNumber = request.getAttribute("phoneNumber").toString();

    log.info(realName);
    log.info(phoneNumber);

    request.setAttribute("realName", realName);
    request.setAttribute("phoneNumber", phoneNumber);

    request.getRequestDispatcher("/auth/signup").forward(request, response);
  }

}
