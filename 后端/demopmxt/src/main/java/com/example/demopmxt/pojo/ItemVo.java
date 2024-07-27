package com.example.demopmxt.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemVo {
    private Integer itemId;
    private String name;
    private User seller;
    private String description;
    private String category;
    private BigDecimal startingPrice;
    private String listingDate;
    private String status;
    private String image;
}