package com.example.demopmxt.service;

import com.example.demopmxt.mapper.PayMapper;
import com.example.demopmxt.pojo.PageBeen;
import com.example.demopmxt.pojo.Pay;
import com.example.demopmxt.pojo.PayVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PayServicelmpl implements PayService{

    @Autowired
    private PayMapper payMapper;
    @Override
    public PageBeen get(Integer page, Integer pageSize, String paymentMethod, String paymentStatus,Integer payId,Integer payerId) {
        PageHelper.startPage(page,pageSize);
        List<PayVo> payVoList=payMapper.get(paymentMethod,paymentStatus,payId,payerId);
        Page<PayVo> payVoPage=(Page<PayVo>) payVoList;
        PageBeen pageBeen=new PageBeen(payVoPage.getTotal(),payVoPage.getResult());
        return pageBeen;
    }

    @Override
    public void up(Pay pay) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        pay.setPaymentDate(LocalDateTime.now().format(formatter));
        payMapper.up( pay);
    }


}
