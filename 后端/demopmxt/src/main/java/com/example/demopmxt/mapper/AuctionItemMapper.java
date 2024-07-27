package com.example.demopmxt.mapper;

import com.example.demopmxt.pojo.AuIt;
import com.example.demopmxt.pojo.AuItVo;
import com.example.demopmxt.pojo.ItemVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface AuctionItemMapper {
    List<AuItVo> auitlist(Integer auctionId,Integer auctionitemId,String name,String sellername,String title,Integer itemId);

    void add(Integer auctionId, Integer itemId, BigDecimal reservePrice);

    void up(BigDecimal soldPrice, String saleStatus, Integer auctionItemId,BigDecimal reservePrice);

    void del(Integer id);

    @Select("SELECT * FROM auctionitem WHERE auctionId = #{auctionId}")
    List<AuIt> findItemsByAuctionId(Integer auctionId);

    @Update("UPDATE auctionitem SET soldPrice = #{soldPrice}, saleStatus = '已出售' WHERE auctionItemId = #{auctionItemId}")
    void updateItemSaleStatus(@Param("auctionItemId") Integer auctionItemId, @Param("soldPrice") BigDecimal soldPrice);
    @Select("SELECT * FROM auctionitem WHERE auctionId = #{auctionId}")
    List<AuIt> findByAuctionId(Integer auctionId);


}
