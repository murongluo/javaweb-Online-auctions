package com.example.demopmxt.pojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BidVo {
    private Integer bidId;
    private ItemVo item;
    private User bidder;
    private BigDecimal bidAmount;
    private String bidTime;
    private String status;
}