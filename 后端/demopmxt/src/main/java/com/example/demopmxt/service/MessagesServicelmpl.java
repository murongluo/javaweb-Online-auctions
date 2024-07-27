//package com.example.demopmxt.service;
//
//import com.example.demopmxt.mapper.MessagesMapper;
//import com.example.demopmxt.pojo.Messages;
//import com.example.demopmxt.pojo.MessagesVo;
//import com.example.demopmxt.pojo.PageBeen;
//import com.github.pagehelper.Page;
//import com.github.pagehelper.PageHelper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.List;
//
//@Service
//public class MessagesServicelmpl implements MessagesService{
//
//    @Autowired
//    private MessagesMapper messagesMapper;
//    @Override
//
//    public PageBeen page(Integer page, Integer pageSize) {
//        PageHelper.startPage(page,pageSize);
//        List<MessagesVo> messagesVoList=messagesMapper.messageList();
//        Page<MessagesVo> messagesVoPage=(Page<MessagesVo>) messagesVoList;
//        PageBeen pageBeen=new PageBeen(messagesVoPage.getTotal(),messagesVoPage.getResult());
//        return pageBeen;
//    }
//
//    @Override
//    public void add(Messages messages) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        String formattedDateTime = LocalDateTime.now().format(formatter);
//        messagesMapper.add(messages.getSenderId(),messages.getReceiverId(),messages.getMassageText(),formattedDateTime);
//    }
//
//    @Override
//    public void read(Integer id) {
//        messagesMapper.read(id);
//    }
//
//    @Override
//    public void del(Integer id) {
//        messagesMapper.del(id);
//    }
//}
