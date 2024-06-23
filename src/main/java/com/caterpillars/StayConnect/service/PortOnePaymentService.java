package com.caterpillars.StayConnect.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@Data
@Slf4j
public class PortOnePaymentService {

    @Value("${PORTONE_API_URL}")
    private String apiUrl;

    @Value("${PORTONE_API_KEY}")
    private String apiKey;

    @Value("${PORTONE_SECRET_KEY}")
    private String apiSecret;

    public String getAccessToken() {

        RestTemplate restTemplate = null;
        PortOneTokenResponse portOneTokenResponse = null;

        String tokenUrl = apiUrl + "/users/getToken";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        MultiValueMap params = new LinkedMultiValueMap();
        params.put("imp_key", apiKey);
        params.put("imp_secret", apiSecret);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

        RestTemplate rt = new RestTemplate();
        ResponseEntity<PortOneTokenResponse> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, entity, PortOneTokenResponse.class);

        log.info(response.getBody().toString());
        portOneTokenResponse = response.getBody();

        return portOneTokenResponse.toString();
    }

    // AccessToken 발급 Class
    @Data
    private static class TokenResponse {
        public String access_token;
        public int now;
        public int expired_at;
    }

    @Data
    private static class PortOneTokenResponse {
        public int code;
        public Object message;
        public TokenResponse response;
    }
}
