package com.caterpillars.StayConnect.component.interceptor;

import java.net.HttpCookie;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.caterpillars.StayConnect.component.provider.JWTokenProvider;
import com.caterpillars.StayConnect.custom.CustomUserDetails;
import com.caterpillars.StayConnect.model.entities.User;
import com.caterpillars.StayConnect.model.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtHandshakeInterceptor implements HandshakeInterceptor {

  @Autowired
  private JWTokenProvider jwTokenProvider;

  @Autowired
  private UserRepository userRepository;

  @Override
  public boolean beforeHandshake(@NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response,
      @NonNull WebSocketHandler wsHandler, @NonNull Map<String, Object> attributes) throws Exception {

    String jwToken = getJwtTokenFromCookies(request);
    if (jwToken != null && jwTokenProvider.validateToken(jwToken)) {
      Long userId = jwTokenProvider.extractSubject(jwToken);
      Optional<User> user = userRepository.findById(userId);
      if (user.isPresent()) {
        CustomUserDetails userDetails = new CustomUserDetails(user.get());
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
            userDetails.getAuthorities());
        attributes.put("principal", authentication);
        return true;
      }
    }
    response.setStatusCode(HttpStatus.UNAUTHORIZED);
    return false;
  }

  @Override
  public void afterHandshake(@NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response,
      @NonNull WebSocketHandler wsHandler, @Nullable Exception exception) {
  }

  private String getJwtTokenFromCookies(@NonNull ServerHttpRequest request) {
    List<String> cookieHeaders = request.getHeaders().get(HttpHeaders.COOKIE);
    if (cookieHeaders != null) {
      return cookieHeaders.stream()
          .flatMap(cookieHeader -> HttpCookie.parse(cookieHeader).stream())
          .filter(cookie -> "jwt".equals(cookie.getName()))
          .map(HttpCookie::getValue)
          .findFirst()
          .orElse(null);
    }
    return null;
  }
}
