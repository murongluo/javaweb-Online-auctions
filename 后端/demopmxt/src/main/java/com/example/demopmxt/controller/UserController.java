package com.example.demopmxt.controller;

import com.example.demopmxt.pojo.PageBeen;
import com.example.demopmxt.pojo.Result;
import com.example.demopmxt.pojo.User;
import com.example.demopmxt.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getusers")
    public Result page(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer pageSize, String username,Integer userId,String userType) {
        PageBeen pageBeen =userService.page(page,pageSize,username,userId,userType);
        return Result.success(pageBeen);
    }

    @DeleteMapping("/deluser/{userId}")//根据用户id删除用户(注销一个用户)
    public Result deluserbyid(@PathVariable Integer userId){
        userService.delUserById(userId);
        return Result.success();
    }

    @PostMapping("/upuser")//修改用户信息
    public Result upUser(@RequestBody User u){
        userService.upUser(u.getUserId(),u.getUsername(),u.getPassword(),u.getAddress(),u.getEmail(),u.getUserType(),u.getImage());
        return Result.success();
    }

    @PostMapping("/login/adduser")//注册
    public Result add(@RequestBody User user){
        userService.add(user);
        userService.sent(user.getEmail());
        return Result.success();
    }

}
