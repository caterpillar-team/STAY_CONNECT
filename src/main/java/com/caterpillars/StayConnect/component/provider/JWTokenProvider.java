package com.caterpillars.StayConnect.component.provider;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.caterpillars.StayConnect.custom.CustomUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * JWT 토큰을 생성하고 검증하는 클래스입니다.
 */
@Slf4j
@Component
@Getter
public class JWTokenProvider {

  private final SecretKey secretKey;

  @Value("${JSON_WEB_TOKEN_EXPIRATION_TIME}")
  private int expiration;

  @Value("${JSON_WEB_TOKEN_ISSUER}")
  private String issuer;

  /**
   * 주어진 비밀 키를 사용하여 JWTokenProvider를 초기화합니다.
   *
   * @param JWTokenStringKey 비밀 키 문자열
   */
  public JWTokenProvider(@Value("${JSON_WEB_TOKEN_SECRET_KEY}") String JWTokenStringKey) {
    this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(JWTokenStringKey));
  }

  /**
   * 인증 정보를 사용하여 JWT 토큰을 생성합니다.
   *
   * @param authentication 인증 정보
   * @return 생성된 JWT 토큰
   */
  public String generateToken(Authentication authentication) {
    return createToken(authentication);
  }

  /**
   * 주어진 JWT 토큰이 유효한지 검증합니다.
   *
   * @param token JWT 토큰
   * @return 토큰이 유효하면 true, 그렇지 않으면 false
   */
  public boolean validateToken(String token) {
    try {
      Jwts.parser().verifyWith(secretKey)
          .requireIssuer(issuer)
          .build()
          .parseSignedClaims(token);
      return true;
    } catch (MalformedJwtException e) {
      log.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      log.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      log.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      log.error("JWT claims string is empty: {}", e.getMessage());
    } catch (SignatureException e) {
      log.error("Invalid JWT signature: {}", e.getMessage());
    } catch (Exception e) {
      log.error("Invalid JWT: {}", e.getMessage());
    }
    return false;
  }

  /**
   * JWT 토큰에서 사용자 이름을 추출합니다.
   *
   * @param token JWT 토큰
   * @return 사용자 이름
   */
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  /**
   * JWT 토큰에서 만료 날짜를 추출합니다.
   *
   * @param token JWT 토큰
   * @return 만료 날짜
   */
  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  /**
   * JWT 토큰에서 역할을 추출합니다.
   *
   * @param token JWT 토큰
   * @return 역할
   */
  public String extractRole(String token) {
    return extractClaim(token, claims -> claims.get("role", String.class));
  }

  /**
   * JWT 토큰이 만료되었는지 확인합니다.
   *
   * @param token JWT 토큰
   * @return 토큰이 만료되었으면 true, 그렇지 않으면 false
   */
  public Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  /**
   * 인증 정보를 사용하여 JWT 토큰을 생성합니다.
   *
   * @param authentication 인증 정보
   * @return 생성된 JWT 토큰
   */
  private String createToken(Authentication authentication) {

    CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

    return Jwts.builder()
        .subject(userDetails.getUsername())
        .issuer(issuer)
        .claim("role", userDetails.getRole().getName())
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + expiration))
        .signWith(secretKey)
        .compact();
  }

  /**
   * JWT 토큰에서 모든 클레임을 추출합니다.
   *
   * @param token JWT 토큰
   * @return 클레임
   */
  private Claims extractAllClaims(String token) {
    return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
  }

  /**
   * JWT 토큰에서 특정 클레임을 추출합니다.
   *
   * @param token          JWT 토큰
   * @param claimsResolver 클레임을 추출하는 함수
   * @param <T>            클레임의 타입
   * @return 추출된 클레임
   */
  private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

}