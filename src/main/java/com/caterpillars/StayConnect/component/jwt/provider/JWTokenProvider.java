package com.caterpillars.StayConnect.component.jwt.provider;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.caterpillars.StayConnect.model.entities.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Getter
public class JWTokenProvider {

  private final SecretKey secretKey;

  @Value("${jwt.expiration}")
  private int expiration; // 30min 1800000

  public JWTokenProvider(@Value("${jwt.secret}") String JWTokenStringKey) {
    this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(JWTokenStringKey));
  }

  // @PostConstruct
  // protected void init(@Value("${jwt.secret}") String JWTokenStringKey) {
  // this.secretKey=Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(JWTokenStringKey));
  // }

  public String generateToken(Authentication authentication) {
    return createToken(authentication);
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
      return true;
    } catch (MalformedJwtException e) {
      log.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      log.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      log.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      log.error("JWT claims string is empty: {}", e.getMessage());
    }
    return false;
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  public String extractRole(String token) {
    return extractClaim(token, claims -> claims.get("role", String.class));
  }

  // public Authentication getAuthentication(String token) {
  // String username = extractUsername(token);
  // User user = authService.loadUserByUsername(username);
  // return new UsernamePasswordAuthenticationToken(user, "",
  // user.getAuthorities());
  // }

  private Claims extractAllClaims(String token) {
    return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
  }

  // private Boolean isTokenExpired(String token) {
  // return extractExpiration(token).before(new Date());
  // }

  private String createToken(Authentication authentication) {

    User user = (User) authentication.getPrincipal();

    return Jwts.builder()
        .subject(user.getUsername())
        .claim("role", user.getRole().getName())
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + expiration))
        .signWith(secretKey)
        .compact();
  }

  // @Override
  // public Authentication authenticate(Authentication authentication) throws
  // AuthenticationException {
  // String token = (String) authentication.getCredentials();
  // if (validateToken(token)) {
  // return getAuthentication(token);
  // } else {
  // throw new BadCredentialsException("Invalid JWT token");
  // }
  // }

  // @Override
  // public boolean supports(Class<?> authentication) {
  // return
  // UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
  // }
}
