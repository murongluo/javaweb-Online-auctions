package com.example.demopmxt.service;

import com.example.demopmxt.mapper.BidMapper;
import com.example.demopmxt.pojo.BidVo;
import com.example.demopmxt.pojo.Bidding;
import com.example.demopmxt.pojo.PageBeen;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class BidServielmpl implements BidService{


    @Autowired
    private BidMapper bidMapper;
    @Override
    public PageBeen page(Integer page, Integer pageSize,String status,Integer bidId,String name,String sellername,String bidname,Integer itemId,Integer bidderId) {
        PageHelper.startPage(page,pageSize);
        List<BidVo> bidVoList=bidMapper.bidslist(status,bidId,name,sellername,bidname,itemId,bidderId);
        Page<BidVo> bidVoPage=(Page<BidVo>) bidVoList;
        PageBeen pageBeen=new  PageBeen(bidVoPage.getTotal(),bidVoPage.getResult());
        return pageBeen;
    }

    @Override
    public void add(Bidding bidding) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = LocalDateTime.now().format(formatter);
        bidMapper.up(bidding.getItemId());
        bidMapper.add(bidding.getItemId(),bidding.getBidderId(),bidding.getBidAmount(),formattedDateTime);
    }

    @Override
    public Bidding sel(Integer itemId) {
        return bidMapper.sel( itemId);
    }


}
