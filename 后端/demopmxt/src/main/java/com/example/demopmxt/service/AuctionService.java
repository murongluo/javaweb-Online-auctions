package com.example.demopmxt.service;

import com.example.demopmxt.pojo.Auction;
import com.example.demopmxt.pojo.PageBeen;

public interface AuctionService {
    PageBeen getaction(Integer page, Integer pageSize, Integer auctionId, String description, String title, String startDate, String endDate, String status);

    void del(Integer id);

    void add(Auction auction);

    void upauction(Auction auction);




}
