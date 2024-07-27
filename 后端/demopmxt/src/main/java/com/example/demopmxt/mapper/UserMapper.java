package com.example.demopmxt.mapper;


import com.example.demopmxt.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper {

    List<User> userList(Integer userId, String username, String userType);

    void delUserById(Integer userId);

    void upUser(Integer userId, String username, String password, String address, String email, String userType,String image);

    void adduser(String username, String email, String password, String address, String image,String now);

    User getUserByEmail(String email);

    User login(User user);
    @Update("update user set lastLoginDate = #{formattedDateTime} where userId =#{userId}")
    void llogin(Integer userId, String formattedDateTime);

    void forgot(String email, String randomString, Integer userId);

    User select(String email, Integer userId);

}
