package com.caterpillars.StayConnect.service;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Data
@Getter
@Setter
public class PortOnePaymentService {

    private static final String PORTONE_API_URL = "https://api.portone.io/v1/payments/";

    public int getPaymentAmount(String paymentId) {
        RestTemplate restTemplate = new RestTemplate();
        PortOnePaymentResponse response = restTemplate.getForObject(PORTONE_API_URL + paymentId, PortOnePaymentResponse.class);
        return response.getAmount();
    }

    static class PortOnePaymentResponse {
        private String paymentId;
        private int amount;
        private String currency;
        private String status;

        public int getAmount() {
            return amount;
        }
    }
}
