package com.ljw.gmall.user.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ljw.gmall.bean.UmsMember;
import com.ljw.gmall.bean.UmsMemberReceiveAddress;
import com.ljw.gmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class UserController {

    //@Autowired  同一个容器可以注入
    //选dubbo的reference
    @Reference
    UserService userService;

    @RequestMapping("index")
    @ResponseBody
    public String index(){
        return "hello gmall";
    }

    @RequestMapping("getAllUser")
    @ResponseBody
    public List<UmsMember> getAllUser(){
        List<UmsMember> umsMember=userService.getAllUser();

        return umsMember;
    }

    /**
     * @RequestBody String memberId这句话可以接收前端的json数据
     * @param memberId
     * @return
     */
    @RequestMapping("getReceiveAddressByUserMemberId")
    @ResponseBody
    public List<UmsMemberReceiveAddress> getReceiveAddressByUserMemberId( String memberId){
        List<UmsMemberReceiveAddress> umsMemberReceiveAddresses = userService.getReceiveAddressByUserMemberId(memberId);
        return umsMemberReceiveAddresses;
    }

    @RequestMapping("insertUser")
    @ResponseBody
    public int insertUser (UmsMember umsMember){
        int result=userService.insertUser(umsMember);
        return result;
    }

}
