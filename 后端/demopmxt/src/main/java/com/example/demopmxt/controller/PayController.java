package com.example.demopmxt.controller;


import com.example.demopmxt.pojo.PageBeen;
import com.example.demopmxt.pojo.Pay;
import com.example.demopmxt.pojo.Result;
import com.example.demopmxt.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@Slf4j
public class PayController {

    @Autowired
    private PayService payService;

    @GetMapping("/getpay")
    public Result page(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer pageSize,String paymentMethod,String paymentStatus,Integer paymentId,Integer payerId){
        PageBeen pageBeen=payService.get(page,pageSize,paymentMethod,paymentStatus,paymentId,payerId);
        return Result.success(pageBeen);
    }

    @PostMapping("/uppay")
    public Result up(@RequestBody Pay pay){
        payService.up(pay);
        return Result.success();
    }


}
