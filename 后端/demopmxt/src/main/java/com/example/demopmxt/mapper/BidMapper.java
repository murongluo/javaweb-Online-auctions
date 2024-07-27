package com.example.demopmxt.mapper;

import com.example.demopmxt.pojo.BidVo;
import com.example.demopmxt.pojo.Bidding;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface BidMapper {


    List<BidVo> bidslist(String status,Integer bidId,String name,String sellername,String bidname,Integer itemId,Integer bidderId );

    void add(Integer itemId, Integer bidderId, BigDecimal bidAmount, String formattedDateTime);

    void up(Integer itemId);

    void del(Integer id);

    @Select(" select  itemId from  bid where  bidId =#{id}")
    Integer selectitemId(Integer id);

    @Select("SELECT * FROM bid WHERE bidAmount = (SELECT MAX(bidAmount) FROM bid WHERE ItemId = #{itemId})")
    Bidding sel(Integer itemId);
    @Select("SELECT * FROM bid WHERE itemId = #{itemId} ORDER BY bidAmount DESC LIMIT 1")
    Bidding findHighestBidForItem(Integer itemId);
}
