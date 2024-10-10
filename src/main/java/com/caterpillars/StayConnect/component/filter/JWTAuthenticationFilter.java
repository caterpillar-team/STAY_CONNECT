package com.caterpillars.StayConnect.component.filter;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.caterpillars.StayConnect.component.provider.JWTokenProvider;
import com.caterpillars.StayConnect.custom.PrincipalDetails;
import com.caterpillars.StayConnect.model.entities.User;
import com.caterpillars.StayConnect.model.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * JWT 인증 필터 클래스입니다.
 * 요청에서 JWT 토큰을 추출하고 검증하여 인증을 설정합니다.
 */
@Slf4j
@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

  @Autowired
  private JWTokenProvider jwTokenProvider;

  @Autowired
  private UserRepository userRepository;

  /**
   * 요청을 필터링하여 JWT 토큰을 검증하고 인증을 설정합니다.
   *
   * @param request     HTTP 요청
   * @param response    HTTP 응답
   * @param filterChain 필터 체인
   * @throws ServletException 서블릿 예외
   * @throws IOException      입출력 예외
   */
  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {
    String token = getTokenFromRequest(request);

    if (token != null && jwTokenProvider.validateToken(token)) {
      Long userId = jwTokenProvider.extractSubject(token);
      Optional<User> user = userRepository.findById(userId);
      if (user.isPresent()) {
        PrincipalDetails userDetails = new PrincipalDetails(user.get(), null);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
            userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("successful authentication for user '{}'", userDetails.getUsername());
      }
    }
    try {
      filterChain.doFilter(request, response);
    } finally {
      SecurityContextHolder.clearContext();
    }
  }

  /**
   * 요청에서 JWT 토큰을 추출합니다.
   *
   * @param request HTTP 요청
   * @return JWT 토큰
   */
  private String getTokenFromRequest(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if ("jwt".equals(cookie.getName())) {
          return cookie.getValue();
        }
      }
    }
    return null;
  }
}