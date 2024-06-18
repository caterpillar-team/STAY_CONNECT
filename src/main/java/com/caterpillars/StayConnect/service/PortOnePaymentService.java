package com.caterpillars.StayConnect.service;

import com.caterpillars.StayConnect.model.dto.PaymentDto;
import lombok.Data;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.caterpillars.StayConnect.model.dto.PaymentDto;

import lombok.Data;

@Service
@Data
public class PortOnePaymentService {

    private static final String PORTONE_API_URL = "https://api.portone.io/v1/payments/";

    private String apiKey = "2805168157373551";
    private String secretKey = "UEQSqfpm3glxMqD9XMgtW3f0vqDOLFlRbLDf6YWJeEgKtp6Ai4J4zT8Ox2X7E1T4IrhZ6isBHUuKJ67e";

    public PaymentDto getPaymentDetails(String imp_uid) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromHttpUrl(PORTONE_API_URL + imp_uid);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<PaymentDto> response = restTemplate.exchange(
                uriBuilder.toUriString(),
                HttpMethod.GET,
                entity,
                PaymentDto.class // 응답을 DTO 객체로 받음
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("실패하였습니다.");
        }
    }
}
