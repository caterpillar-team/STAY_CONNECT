package com.caterpillars.StayConnect.service;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.caterpillars.StayConnect.custom.PrincipalDetails;
import com.caterpillars.StayConnect.model.dto.UserSignUpDto;
import com.caterpillars.StayConnect.model.entities.Role;
import com.caterpillars.StayConnect.model.entities.User;
import com.caterpillars.StayConnect.model.repository.RoleRepository;
import com.caterpillars.StayConnect.model.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthService implements UserDetailsService,
    OAuth2UserService<OAuth2UserRequest, OAuth2User> {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private HttpServletRequest httpServletRequest;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Transactional
  public User signUp(UserSignUpDto signUpDto) {
    Role role = roleRepository.findByName("ROLE_USER").orElseThrow(NoSuchElementException::new);
    if (emailExist(signUpDto.getEmail())) {
      throw new RuntimeException("이미 해당 email로 등록된 계정이 존재합니다. : " + signUpDto.getEmail());
    }
    if (!signUpDto.getPassword().equals(signUpDto.getConfirmPassword())) {
      throw new RuntimeException("비밀번호가 일치하지 않습니다.");
    }

    User user = User.builder()
        .username(signUpDto.getUsername())
        .password(passwordEncoder.encode(signUpDto.getPassword()))
        .email(signUpDto.getEmail())
        .birth(signUpDto.getBirth())
        .gender(signUpDto.getGender())
        .phoneNumber(signUpDto.getPhoneNumber())
        .realName(signUpDto.getRealName())
        .role(role)
        .build();

    return userRepository.save(user);
  }

  private boolean emailExist(String email) {
    Optional<User> user = userRepository.findByEmail(email);
    return user.isPresent();
  }

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("user not found with username: " + username));

    return new PrincipalDetails(
        User.builder()
            .id(user.getId())
            .username(user.getUsername())
            .password(user.getPassword())
            .role(user.getRole())
            .build(),
        null);

    // return new PrincipalDetails(user, null);
  }

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);

    return processGoogleLogin(oAuth2User, userRequest);
  }

  @Transactional(readOnly = true)
  private OAuth2User processGoogleLogin(OAuth2User oAuth2User, OAuth2UserRequest userRequest) {
    String accessToken = userRequest.getAccessToken().getTokenValue();
    String phoneNumber = fetchGooglePhoneNumber(accessToken);

    Map<String, Object> attributes = new HashMap<>(oAuth2User.getAttributes());
    attributes.put("phone_number", phoneNumber);

    // log.info("OAuth2User attributes: {}", attributes);

    String realName = attributes.get("name").toString();

    httpServletRequest.setAttribute("realName", realName);
    httpServletRequest.setAttribute("phoneNumber", phoneNumber);

    User user = userRepository.findByRealNameAndPhoneNumber(realName, phoneNumber)
        .orElseThrow(() -> new OAuth2AuthenticationException("DB에 일치하는 유저가 없습니다."));

    return new PrincipalDetails(user, attributes);
  }

  private String fetchGooglePhoneNumber(String accessToken) {
    String phoneNumber = null;
    try {
      RestTemplate restTemplate = new RestTemplate();
      String url = "https://people.googleapis.com/v1/people/me?personFields=phoneNumbers";
      String bearerToken = "Bearer " + accessToken;

      HttpHeaders headers = new HttpHeaders();
      headers.add("Authorization", bearerToken);
      HttpEntity<String> entity = new HttpEntity<>(headers);

      ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
      String body = response.getBody();

      ObjectMapper objectMapper = new ObjectMapper();
      JsonNode rootNode = objectMapper.readTree(body);
      JsonNode phoneNumbersNode = rootNode.path("phoneNumbers");

      if (phoneNumbersNode.isArray() && phoneNumbersNode.size() > 0) {
        JsonNode firstPhoneNumberNode = phoneNumbersNode.get(0);
        phoneNumber = firstPhoneNumberNode.path("value").asText();
      }
    } catch (JsonProcessingException | RestClientException e) {
      log.error("Error fetching phone number", e);
    }
    return phoneNumber;
  }

}