package com.example.demopmxt.service;


import com.example.demopmxt.mapper.AuctionItemMapper;
import com.example.demopmxt.mapper.AuctionMapper;
import com.example.demopmxt.mapper.BidMapper;
import com.example.demopmxt.mapper.PayMapper;
import com.example.demopmxt.pojo.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AuctionServicelmpl implements AuctionService {
    @Autowired
    private AuctionMapper auctionMapper;
    @Autowired
    private AuctionItemMapper auctionItemMapper;
    @Autowired
    private BidMapper bidMapper;
    @Autowired
    private PayMapper payMapper;


    @Override
    public PageBeen getaction(Integer page, Integer pageSize, Integer auctionId, String description, String title, String startDate, String endDate, String status) {
        PageHelper.startPage(page,pageSize);
        List<Auction> autionList= auctionMapper.autionList(auctionId,description,title,status,startDate,endDate);
        Page<Auction> autionPage=(Page<Auction>) autionList;
        PageBeen pageBeen=new PageBeen(autionPage.getTotal(),autionPage.getResult());
        return pageBeen;
    }

    @Override
    public void del(Integer id) {
        auctionMapper.del(id);
    }

///*
//    @Override
//    public PageBeen getaction(Integer page, Integer pageSize) {
//        PageHelper.startPage(page,pageSize);
//        List<Auction> autionList= auctionMapper.autionList();
//        Page<Auction> autionPage=(Page<Auction>) autionList;
//        PageBeen pageBeen=new PageBeen(autionPage.getTotal(),autionPage.getResult());
//        return pageBeen;
//    }
//*/
    @Override
    public void add(Auction a) {
        auctionMapper.addauction(a.getTitle(),a.getDescription(),a.getStartDate(),a.getEndDate(),a.getImage());
    }
    @Scheduled(cron = "0 * * * * ?") // 每分钟运行一次
    public void updateAuctionStatus() {
        //根据时间戳判定拍卖会状态
        auctionMapper.updateAuctionStatus();
        List<Auction> endedAuctions = auctionMapper.findEndedAuctions();
        for (Auction auction : endedAuctions) {
            //查拍卖会结束的拍卖
            List<AuIt> items = auctionItemMapper.findItemsByAuctionId(auction.getAuctionId());
            for (AuIt item : items) {
                //查最竞价是否匹配
                Bidding highestBid = bidMapper.findHighestBidForItem(item.getItemId());
                if (highestBid != null) {
                    auctionItemMapper.updateItemSaleStatus(item.getAuctionItemId(), highestBid.getBidAmount());
                    // 检查是否已经有支付记录
                    Pay existingPay = payMapper.findPaymentByItemId(item.getItemId());
                    if (existingPay == null) {
                        // 如果没有支付记录，创建新的支付记录
                        Pay newPayment = new Pay(null, item.getItemId(), highestBid.getBidderId(), highestBid.getBidAmount(), "Pending", "Unpaid", null);
                        payMapper.insertPayment(newPayment);
                    }
                }
            }
        }
        }



    @Override
    public void upauction(Auction auction) {
        auctionMapper.upauction(auction.getAuctionId(),auction.getDescription(),auction.getTitle(),auction.getStatus(),auction.getStartDate(),auction.getEndDate(),auction.getImage());
    }

//    @Override
//    public PageBeen getaction(Integer page, Integer pageSize, Integer auctionId, String description, String title, String startDate, String endDate, String status) {
//        PageHelper.startPage(page,pageSize);
//        List<Auction> autionList= auctionMapper.autionList(auctionId,description,title,status,startDate,endDate);
//        Page<Auction> autionPage=(Page<Auction>) autionList;
//        PageBeen pageBeen=new PageBeen(autionPage.getTotal(),autionPage.getResult());
//        return pageBeen;
//    }
}
