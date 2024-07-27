package com.example.demopmxt.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuIt {
    private Integer auctionItemId;
    private Integer auctionId;
    private Integer itemId;
    private BigDecimal reservePrice;
    private BigDecimal soldPrice;
    private String saleStatus;
}
