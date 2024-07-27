package com.example.demopmxt.controller;

import com.example.demopmxt.pojo.Item;
import com.example.demopmxt.pojo.PageBeen;
import com.example.demopmxt.pojo.Result;
import com.example.demopmxt.service.IteamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@CrossOrigin
@RestController
@Slf4j
public class ItemController {
    @Autowired
    private IteamService iteamService;

    @GetMapping("/getitem")
    public Result page(@RequestParam (defaultValue = "9")Integer pageSize,@RequestParam (defaultValue = "1" )Integer page,Integer itemId,String name,String status,String sellername,Integer sellerId){
        PageBeen pageBeen = iteamService.page(page,pageSize,itemId,name,status,sellername,sellerId);
                return Result.success(pageBeen);
    }

    @DeleteMapping("/delitem/{id}")
    public Result delitem(@PathVariable Integer id){
        iteamService.delitem(id);
        return Result.success();
    }

    @PostMapping("/upitem")
    public Result upitem(@RequestBody Item item){
        iteamService.upitem(item);
        return Result.success();
    }

    @PostMapping("/additem")
    public Result additem(@RequestBody Item item){
        iteamService.add(item);
        return Result.success();
    }

}
