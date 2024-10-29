package com.caterpillars.StayConnect.component.interceptor;

import java.net.HttpCookie;
import java.util.List;
import java.util.Map;

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
import com.caterpillars.StayConnect.custom.PrincipalDetails;
import com.caterpillars.StayConnect.model.repository.UserRepository;

@Component
public class JwtHandShakeInterceptor implements HandshakeInterceptor {

  @Autowired
  private JWTokenProvider jwTokenProvider;

  @Autowired
  private UserRepository userRepository;

  @Override
  public boolean beforeHandshake(@NonNull ServerHttpRequest request,
      @NonNull ServerHttpResponse response,
      @NonNull WebSocketHandler wsHandler,
      @NonNull Map<String, Object> attributes) throws Exception {

    String jwt = getJwtCookie(request);
    if (jwt != null && jwTokenProvider.validateToken(jwt)) {
      Long userId = jwTokenProvider.extractSubject(jwt);
      if (userRepository.findById(userId).isPresent()) {
        PrincipalDetails principalDetails = new PrincipalDetails(userRepository.findById(userId).get(), null);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            principalDetails.getUsername(), null, principalDetails.getAuthorities());
        attributes.put("authentication", authentication);
        return true;
      }
    }
    response.setStatusCode(HttpStatus.UNAUTHORIZED);
    return false;
  }

  @Override
  public void afterHandshake(
      @NonNull ServerHttpRequest request,
      @NonNull ServerHttpResponse response,
      @NonNull WebSocketHandler wsHandler,
      @Nullable Exception exception) {
    throw new UnsupportedOperationException("Unimplemented method 'afterHandshake'");
  }

  private String getJwtCookie(@NonNull ServerHttpRequest request) {
    List<String> cookieHeaders = request.getHeaders().get(HttpHeaders.COOKIE);
    if (cookieHeaders != null) {
      return cookieHeaders.stream()
          .flatMap(cookieHeader -> HttpCookie.parse(cookieHeader).stream())
          .filter(cookie -> "jwt".equals(cookie.getName()))
          .map(HttpCookie::getValue)
          .findFirst()
          .orElse(null);
    }
    throw (new RuntimeException("No cookie found"));
  }
}
