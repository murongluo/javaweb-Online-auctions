package com.example.demopmxt.controller;

import com.example.demopmxt.pojo.Bidding;
import com.example.demopmxt.pojo.PageBeen;
import com.example.demopmxt.pojo.Result;
import com.example.demopmxt.service.BidService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@Slf4j
public class BidController {
    @Autowired
    private BidService bidService;

    @GetMapping("/getbids")
    public Result page(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer pageSize, String status, Integer bidId,String name,String sellername,String bidname,Integer itemId,Integer bidderId) {
        PageBeen pageBeen = bidService.page(page, pageSize, status, bidId,name,sellername,bidname,itemId,bidderId);
        return Result.success(pageBeen);
    }
    @GetMapping("/getbidmaxprice/{itemId}")
    public Result sel(@PathVariable Integer itemId){
        Bidding bidding=bidService.sel(itemId);
        return Result.success(bidding);
    }

    @PostMapping("/addbids")
    public Result add(@RequestBody Bidding bidding) {
        bidService.add(bidding);
        return Result.success();
    }


}

