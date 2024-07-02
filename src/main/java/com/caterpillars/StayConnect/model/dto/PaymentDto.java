package com.caterpillars.StayConnect.model.dto;

import lombok.Data;

@Data
public class PaymentDto {

    private String imp_uid;
    private String merchant_uid;
    private int paid_amount;
    private String pay_method;
    private Long userId;
    private Long roomInfoId;
}