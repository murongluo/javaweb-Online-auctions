//package com.example.demopmxt.controller;
//
//import com.example.demopmxt.pojo.Messages;
//import com.example.demopmxt.pojo.PageBeen;
//import com.example.demopmxt.pojo.Result;
//import com.example.demopmxt.service.MessagesService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@CrossOrigin
//@RestController
//@Slf4j
//public class MessagesController {
//
//    @Autowired
//    private MessagesService messagesService;
//
//    @GetMapping("/getMes")
//    public Result page(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer pageSize){
//        PageBeen pageBeen=messagesService.page(page,pageSize);
//        return Result.success(pageBeen);
//    }
//
//    @PostMapping("/addmessage")
//    public Result addmessage(@RequestBody Messages messages){
//        messagesService.add(messages);
//        return Result.success();
//    }
//    @PostMapping("/read/{id}")
//    public Result read(@PathVariable Integer id){
//        messagesService.read(id);
//        return Result.success();
//    }
//
//    @DeleteMapping("/delmes/{id}")
//    public Result del(@PathVariable Integer id){
//        messagesService.del(id);
//        return Result.success();
//    }
//}
