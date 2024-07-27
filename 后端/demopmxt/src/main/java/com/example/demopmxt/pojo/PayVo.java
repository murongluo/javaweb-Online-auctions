package com.example.demopmxt.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayVo {
    private Integer paymentId;
    private ItemVo item;
    private User payer;
    private BigDecimal amount;
    private String paymentMethod;
    private String paymentStatus;
    private String paymentDate;
}