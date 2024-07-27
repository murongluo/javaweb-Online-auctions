package com.example.demopmxt.service;


import com.example.demopmxt.pojo.AuIt;
import com.example.demopmxt.pojo.PageBeen;

public interface AuctionItemService {

    PageBeen page(Integer page, Integer pageSize, Integer auctionId,Integer auctionitemId,String name,String sellername,String title,Integer itemId);

    void add(AuIt auIt);

    void upauit(AuIt auIt);

    void del(Integer id);
}

