package com.example.demopmxt.controller;

import com.example.demopmxt.pojo.Result;
import com.example.demopmxt.pojo.User;
import com.example.demopmxt.service.UserService;
import com.example.demopmxt.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@CrossOrigin
@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/adminlogin")
    public Result adminlogin(@RequestBody User user){
        User u=userService.login(user);
        log.info(user.toString());
        if(u != null){
           if ("管理员".equals(u.getUserType())){
               userService.llogin(u.getUserId());
               Map<String, Object> claims=new HashMap<>();
               claims.put("userId",u.getUserId());
               claims.put("username",u.getUsername());
               claims.put("userTpye",u.getUserType());
               claims.put("imageUrl",u.getImage());
               claims.put("lastLoginDate",u.getLastLoginDate());
               String jwt= JwtUtils.generateJwt(claims);

               claims.put("token",jwt);
               return Result.success(claims);
           }else {
               return Result.error("权限不够");
           }
        }
        return Result.error("账户或者密码错误");
    }

    @PostMapping("/login")
    public Result login(@RequestBody User user){
        User u=userService.login(user);
        if(u != null){
            userService.llogin(u.getUserId());
            Map<String, Object> claims=new HashMap<>();
            claims.put("userId",u.getUserId());
            claims.put("username",u.getUsername());
            claims.put("userTpye",u.getUserType());
            claims.put("imageUrl",u.getImage());
            String jwt= JwtUtils.generateJwt(claims);
            claims.put("token",jwt);
            return Result.success(claims);
        }
        return Result.error("账户或者密码错误");
    }

    @PostMapping("/login/forgot")
    public Result forgot(@RequestBody User u){
        User user=userService.selectuser(u.getEmail(),u.getUserId());
        if (user!=null){
            String uuid = UUID.randomUUID().toString();
            // 截取UUID的前8个字符，因为UUID通常是以-分隔的
            String randomString = uuid.substring(0, 8);
            userService.forgot(u.getEmail(),u.getUserId(),randomString);
            userService.sentupdate(u.getEmail(),u.getUserId(),randomString);
            return Result.success();
        }
        else
            return Result.error("用户名和邮箱不匹配！");

    }



}
