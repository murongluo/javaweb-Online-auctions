package com.example.demopmxt.mapper;


import com.example.demopmxt.pojo.AuIt;
import com.example.demopmxt.pojo.Auction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface AuctionMapper {
    List<Auction> autionList(Integer auctionId, String description, String title, String status, String startDate, String endDate);

    void del(Integer id);

    void addauction(String title, String description, String startDate, String endDate, String image);

    void upauction(Integer auctionId, String description, String title, String status, String startDate, String endDate,String image);
    @Select("SELECT * FROM auction WHERE status = '结束'")
    List<Auction> findEndedAuctions();
    @Update("UPDATE auction SET status = CASE " +
            "WHEN CURRENT_TIMESTAMP < startDate THEN '准备中' " +
            "WHEN CURRENT_TIMESTAMP > endDate THEN '结束' " +
            "ELSE '进行中' " +
            "END")
    void updateAuctionStatus();
//    List<Auction> autionList(Integer auctionId, String description, String title, String status, String startDate, String endDate);
//
//    @Update("UPDATE auctions SET status = CASE " +
//            "WHEN startDate > #{now} THEN '准备中' " +
//            "WHEN endDate < #{now} THEN '结束' " +
//            "ELSE '进行中' " +
//            "END")
//    void updateAuctionStatus(String now);



}
