package com.example.demopmxt.controller;

import com.example.demopmxt.pojo.AuIt;

import com.example.demopmxt.pojo.PageBeen;
import com.example.demopmxt.pojo.Result;
import com.example.demopmxt.service.AuctionItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@Slf4j
public class AuctionItemController {
    @Autowired
    private AuctionItemService auctionItemService;

    @GetMapping("/getauit")
    public Result page(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer pageSize,Integer auctionId,Integer auctionitemId,String name,String sellername,String title ,Integer itemId){
        PageBeen pageBeen= auctionItemService.page(page,pageSize,auctionId, auctionitemId, name, sellername, title,itemId);
        return Result.success(pageBeen);
    }

    @PostMapping("/addauit")
    public Result add(@RequestBody AuIt auIt){
        auctionItemService.add(auIt);
        return Result.success();
    }

    @PostMapping("/upauit")
    public Result upauit(@RequestBody AuIt auIt){
        auctionItemService.upauit(auIt);
        return Result.success();
    }
    @DeleteMapping("/delauit/{id}")
    public Result del(@PathVariable Integer id){
        auctionItemService.del(id);
        return Result.success();
    }


}
