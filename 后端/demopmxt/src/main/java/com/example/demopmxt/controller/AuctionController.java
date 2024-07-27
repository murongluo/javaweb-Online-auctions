package com.example.demopmxt.controller;


import com.example.demopmxt.pojo.Auction;
import com.example.demopmxt.pojo.PageBeen;
import com.example.demopmxt.pojo.Result;
import com.example.demopmxt.service.AuctionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@Slf4j
public class AuctionController {
    @Autowired
    private AuctionService auctionService;

    @GetMapping("/getauction")
    public Result page(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer pageSize, Integer auctionId, String description, String title, String status, String startDate, String endDate ){
        PageBeen pageBeen= auctionService.getaction(page,pageSize,auctionId,description,title,startDate,endDate,status);
        return Result.success(pageBeen);
    }

    @PostMapping("/addauction")
    public Result add(@RequestBody Auction auction){
        auctionService.add(auction);
        return Result.success();
    }
    @DeleteMapping("/delaution/{id}")
    public Result delaution(@PathVariable Integer id){
        auctionService.del(id);
        return Result.success();
    }
    @PostMapping("/upauction")
    public Result upauction(@RequestBody Auction auction){
        auctionService.upauction(auction);
        return Result.success();
    }

}
