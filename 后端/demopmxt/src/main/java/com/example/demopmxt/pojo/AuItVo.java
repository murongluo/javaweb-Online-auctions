package com.example.demopmxt.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuItVo {
    private Integer auctionItemId;
    private Auction auction;
    private ItemVo item;
    private BigDecimal reservePrice;
    private BigDecimal soldPrice;
    private String saleStatus;
}
