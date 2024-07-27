package com.example.demopmxt.mapper;


import com.example.demopmxt.pojo.Item;
import com.example.demopmxt.pojo.ItemVo;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface ItemMapper {
    List<ItemVo> itemlist(Integer itemId, String name, String status, String sellername,Integer sellerId);

    void delitem(Integer id);

    void upitem(Item item);

    void additem(Integer sellerId, String name, String description, String category, BigDecimal startingPrice, String image, String formattedDateTime);



}
