package com.example.demopmxt.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pay {
    private Integer paymentId;
    private Integer itemId;
    private Integer payerId;
    private BigDecimal amount;
    private String paymentMethod;
    private String paymentStatus;
    private String paymentDate;
}
