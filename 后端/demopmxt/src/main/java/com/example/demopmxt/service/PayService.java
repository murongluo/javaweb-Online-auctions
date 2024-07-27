package com.example.demopmxt.service;

import com.example.demopmxt.pojo.PageBeen;
import com.example.demopmxt.pojo.Pay;

public interface PayService {





    void up(Pay pay);

    PageBeen get(Integer page, Integer pageSize, String paymentMethod, String paymentStatus,Integer payId,Integer payerId);
}
