package com.example.demopmxt.mapper;


import com.example.demopmxt.pojo.Pay;
import com.example.demopmxt.pojo.PayVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface PayMapper {



    void add(Pay pay);

    @Update("update pay set paymentMethod = #{paymentMethod},paymentStatus = #{paymentStatus},paymentDate=#{paymentDate} where paymentId =#{paymentId}")
    void up(Pay pay);
    @Insert("INSERT INTO pay (itemId, payerId, amount,  paymentDate) VALUES " +
            "(#{itemId}, #{payerId}, #{amount},  CURRENT_TIMESTAMP)")
    void insertPayment(Pay pay);

    List<PayVo> get(String paymentMethod, String paymentStatus, Integer payId,Integer payerId);
    @Select("select  * from  pay where itemId =#{itemId}")
    Pay findPaymentByItemId(Integer itemId);
}
