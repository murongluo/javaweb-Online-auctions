package com.example.demopmxt.service;

import com.example.demopmxt.mapper.UserMapper;
import com.example.demopmxt.pojo.PageBeen;
import com.example.demopmxt.pojo.User;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;



@Service
public class UserServicelmpl implements UserService{

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public PageBeen page(Integer page, Integer pageSize, String username, Integer userId, String userType) {
        PageHelper.startPage(page,pageSize);
        List<User> userList=userMapper.userList(userId,username,userType);
        Page<User> userPage=(Page<User>)userList;
        PageBeen pageBeen=new PageBeen(userPage.getTotal(),userPage.getResult());
        return pageBeen;
    }

    @Override
    public void delUserById(Integer userId) {
        userMapper.delUserById(userId);
    }

    @Override
    public void upUser(Integer userId, String username, String password, String address, String email, String userType,String image) {
        userMapper.upUser(userId,username,password,address,email,userType,image);
    }

    @Override
    public void add(User user) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = LocalDateTime.now().format(formatter);
        userMapper.adduser(user.getUsername(),user.getEmail(),user.getPassword(),user.getAddress(), user.getImage(),formattedDateTime);
    }

    @Override
    public void sent(String email) {
        User u=userMapper.getUserByEmail(email);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("2519444891@qq.com");
        message.setTo(email);
        message.setSubject("槐序拍卖平台注册信息");
        String mes = "您(" + email + ")注册用户成功！用户ID：" + u.getUserId()+"用户密码："+u.getPassword()+"用户名："+u.getUsername();
        message.setText(mes);
        mailSender.send(message);
    }

    @Override
    public User login(User user) {
        return userMapper.login(user);
    }

    @Override
    public void llogin(Integer userId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = LocalDateTime.now().format(formatter);
        userMapper.llogin(userId,formattedDateTime);
    }

    @Override
    public User selectuser(String email, Integer userId) {
        return userMapper.select(email,userId);
    }

    @Override
    public void forgot(String email, Integer userId, String randomString) {
        userMapper.forgot(email,randomString,userId);
    }

    @Override
    public void sentupdate(String email, Integer userId, String randomString) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("2519444891@qq.com");
        message.setTo(email);
        message.setSubject("槐序拍卖平台重置密码信息");
        String mes = "您(" + email + ")注册用户成功！用户ID：" + userId+"重置后密码："+randomString;
        message.setText(mes);
        mailSender.send(message);
    }

}

