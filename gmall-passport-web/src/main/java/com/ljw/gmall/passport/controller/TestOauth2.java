package com.ljw.gmall.passport.controller;

import com.alibaba.fastjson.JSON;
import com.ljw.gmall.util.HttpclientUtil;

import java.util.HashMap;
import java.util.Map;

public class TestOauth2 {
//    App Key：3308677658
//    App Secret：10340750fbe6aeabd8aa2fa026bcf22e

    public static String getCode(){
        //获得授权码
        //http://passport.gmall.com:8085/vlogin
        //code  ace5fcb7968b41311ca0d30b16902187
        String s1 = HttpclientUtil.doGet("https://api.weibo.com/oauth2/authorize?client_id=3308677658&response_type=code&redirect_uri=http://passport.gmall.com:8085/vlogin");
        System.out.println(s1);
        //在第一步和第二步返回回调地址之间，有一个用户操作授权的过程
        //返回授权码到回调地址
        return s1;
    }

    public static  String getAccess_token(){
        //3换取access_token
        //cilient_serect : =
         String s3 = "https://api.weibo.com/oauth2/access_token?";
         Map<String,String> paramMap = new HashMap<>();
         paramMap.put("client_id","3308677658");
         paramMap.put("cilient_serect","10340750fbe6aeabd8aa2fa026bcf22e");
         paramMap.put("grant_type","authorization_code");
         paramMap.put("redirect_uri","http://passport.gmall.com:8085/vlogin");
         paramMap.put("code","ace5fcb7968b41311ca0d30b16902187");//授权有效期内可以使用，每生成一次授权码，说明用户对第三方数据进行重启授权，之前的access_token和授权码全部过期
         String access_token_json = HttpclientUtil.doPost(s3,paramMap);

         Map<String,String>  access_map = JSON.parseObject(access_token_json,Map.class);
         System.out.println(access_map.get("access_token"));
         System.out.println(access_map.get("uuid"));

         return access_map.get("access_token");
    }
    public static Map<String,String>getUser_info(){
        //4 用access_token 查询用户信息
        String s4 = "https://api.weibo.com/2/users/show.json?access_token=2.00HMAs7H0p5_hMdbefcb34140Lydjf&uid=6809985023";

        String user_json = HttpclientUtil.doGet(s4);
        Map<String,String> user_map = JSON.parseObject(user_json,Map.class);

        System.out.println(user_map.get("1"));

        return user_map;
    }

    public static void main(String[] args) {
        getAccess_token();
        //getCode();

    }

}
