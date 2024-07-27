package com.example.demopmxt.service;

import com.example.demopmxt.pojo.Bidding;
import com.example.demopmxt.pojo.PageBeen;

public interface BidService {
    PageBeen page(Integer page, Integer pageSize, String status, Integer bidId,String name,String sellername,String bidname,Integer itemId,Integer bidderId);

    void add(Bidding bidding);


    Bidding sel(Integer itemId);
}
