package com.hansybx.clovedoctorbe.DTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DrugTradeDTO {
    private String outTradeNo;
    private BigDecimal totalAmount;
    private String subject;
    private Integer userId;
    private DrugTradeListDTO[] drugList;
    private String returnUrl;
}
