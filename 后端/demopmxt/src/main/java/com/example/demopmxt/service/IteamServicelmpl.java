package com.example.demopmxt.service;


import com.example.demopmxt.mapper.ItemMapper;
import com.example.demopmxt.pojo.Item;
import com.example.demopmxt.pojo.ItemVo;
import com.example.demopmxt.pojo.PageBeen;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class IteamServicelmpl implements IteamService {
    @Autowired
    private ItemMapper itemMapper;

    @Override
    public PageBeen page(Integer page, Integer pageSize, Integer itemId, String name, String status, String sellername,Integer sellerId) {
        PageHelper.startPage(page,pageSize);
        List<ItemVo> itemVoList=itemMapper.itemlist(itemId,name,status,sellername,sellerId);
        Page<ItemVo> p=(Page<ItemVo>) itemVoList;
        PageBeen pageBeen=new PageBeen(p.getTotal(),p.getResult());
        return pageBeen;
    }

    @Override
    public void delitem(Integer id) {
        itemMapper.delitem(id);
    }

    @Override
    public void upitem(Item item) {
        itemMapper.upitem(item);
    }

    @Override
    public void add(Item item) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = LocalDateTime.now().format(formatter);
        itemMapper.additem(item.getSellerId(),item.getName(),item.getDescription(),item.getCategory(),item.getStartingPrice(),item.getImage(), formattedDateTime);
    }
//
////    @Override
////    public PageBeen page(Integer page, Integer pageSize, Integer itemId, String content, String conditin, String mastername, Date startingDate, Date endDate) {
////        PageHelper.startPage(page,pageSize);
////        List<ItemVo> itemVoList=itemMapper.itemlist(itemId,content,conditin,mastername,startingDate,endDate);
////        Page<ItemVo> p=(Page<ItemVo>) itemVoList;
////        PageBeen pageBeen=new PageBeen(p.getTotal(),p.getResult());
////        return pageBeen;
////    }

//
//    @Override
//    public void add(Item item) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        String formattedDateTime = LocalDateTime.now().format(formatter);
//        itemMapper.additem(item.getSellerId(),item.getName(),item.getDescription(),item.getCategory(),item.getStartingPrice(),item.getImageUrl(), formattedDateTime);
//    }
//
//    @Override
//    public PageBeen page(Integer page, Integer pageSize, Integer itemId, String name, String status) {
//        PageHelper.startPage(page,pageSize);
//        List<ItemVo> itemVoList=itemMapper.itemlist(itemId,name,status);
//        Page<ItemVo> p=(Page<ItemVo>) itemVoList;
//        PageBeen pageBeen=new PageBeen(p.getTotal(),p.getResult());
//        return pageBeen;
//    }
//
//   /* @Override
//    public PageBeen page(Integer page, Integer pageSize) {
//        PageHelper.startPage(page,pageSize);
//        List<ItemVo> itemVoList= itemMapper. itemlist();
//        Page<ItemVo> p=(Page<ItemVo>) itemVoList;
//        PageBeen pageBeen=new PageBeen(p.getTotal(),p.getResult());
//        return pageBeen;
//    }*/
}
