package com.example.demopmxt.service;


import com.example.demopmxt.pojo.PageBeen;
import com.example.demopmxt.pojo.User;

public interface UserService {


    PageBeen page(Integer page, Integer pageSize, String username, Integer userId, String userType);

    void delUserById(Integer userId);

    void upUser(Integer userId, String username, String password, String address, String email, String userType,String image);

    void add(User user);

    void sent(String email);

    User login(User user);

    void llogin(Integer userId);

    User selectuser(String email, Integer userId);

    void forgot(String email, Integer userId, String randomString);

    void sentupdate(String email, Integer userId, String randomString);





}
