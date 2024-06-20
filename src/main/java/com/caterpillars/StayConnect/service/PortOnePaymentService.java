package com.caterpillars.StayConnect.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Data
@Slf4j
public class PortOnePaymentService {

    private static final String PORTONE_API_URL = "https://api.portone.io/v1/payments/";

    private String apiKey = "2805168157373551";

//    public PaymentDto getPaymentDetails(String imp_uid) {
//        RestTemplate restTemplate = new RestTemplate();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer " + apiKey);
//
//        UriComponentsBuilder uriBuilder = UriComponentsBuilder
//                .fromHttpUrl(PORTONE_API_URL + imp_uid);
//
//        HttpEntity<String> entity = new HttpEntity<>(headers);
//
//        ResponseEntity<PaymentDto> response = restTemplate.exchange(
//                uriBuilder.toUriString(),
//                HttpMethod.GET,
//                entity,
//                PaymentDto.class);
//
//        if (response.getStatusCode() == HttpStatus.OK) {
//            return response.getBody();
//        } else {
//            throw new RuntimeException("실패하였습니다.");
//        }
//    }
}
