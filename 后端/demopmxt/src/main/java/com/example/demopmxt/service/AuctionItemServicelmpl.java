package com.example.demopmxt.service;


import com.example.demopmxt.mapper.AuctionItemMapper;
import com.example.demopmxt.pojo.AuIt;
import com.example.demopmxt.pojo.AuItVo;
import com.example.demopmxt.pojo.PageBeen;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuctionItemServicelmpl implements AuctionItemService{


    @Autowired
    private AuctionItemMapper auctionItemMapper;
    @Override
    public PageBeen page(Integer page, Integer pageSize ,Integer auctionId,Integer auctionitemId,String name,String sellername,String title,Integer itemId) {
        PageHelper.startPage(page,pageSize);
        List<AuItVo> auItVoList=auctionItemMapper.auitlist(auctionId, auctionitemId, name, sellername, title,itemId);
        Page<AuItVo> auItVoPage=(Page<AuItVo>) auItVoList;
        PageBeen pageBeen=new PageBeen(auItVoPage.getTotal(),auItVoPage.getResult());
        return pageBeen;
    }

    @Override
    public void add(AuIt auIt) {
        auctionItemMapper.add(auIt.getAuctionId(),auIt.getItemId(),auIt.getReservePrice() );
    }

    @Override
    public void upauit(AuIt auIt) {
        auctionItemMapper.up(auIt.getSoldPrice(),auIt.getSaleStatus(),auIt.getAuctionItemId(),auIt.getReservePrice());
    }

    @Override
    public void del(Integer id) {
        auctionItemMapper.del(id);
    }
//
//    @Override
//    public List<ItemVo> select(Integer auctionId) {
//        List<ItemVo> itemVoList=auctionItemMapper.select(auctionId);
//        return itemVoList;
//    }


}
