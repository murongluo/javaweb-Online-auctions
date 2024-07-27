package com.example.demopmxt.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Auction {
    private Integer auctionId;
    private String title;
    private String description;
    private String startDate;
    private String endDate;
    private String status;
    private String image;
}
