package com.example.demopmxt.service;

import com.example.demopmxt.pojo.Item;
import com.example.demopmxt.pojo.PageBeen;

public interface IteamService {
    PageBeen page(Integer page, Integer pageSize, Integer itemId, String name, String status, String sellername,Integer sellerId);

    void delitem(Integer id);

    void upitem(Item item);

    void add(Item item);



}
