package com.example.demopmxt.pojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bidding {
    private Integer bidId;
    private Integer itemId;
    private Integer bidderId;
    private BigDecimal bidAmount;
    private String bidTime;
    private String status;
}