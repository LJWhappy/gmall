//package com.ljw.gmall.payservice.controller;
//
//import com.alibaba.dubbo.config.annotation.Reference;
//import com.alibaba.fastjson.JSON;
//import com.alipay.api.AlipayApiException;
//import com.alipay.api.AlipayClient;
//import com.alipay.api.request.AlipayTradeAppPayRequest;
//import com.ljw.gmall.annotations.LoginRequired;
//import com.ljw.gmall.bean.OmsOrder;
//import com.ljw.gmall.bean.PaymentInfo;
//import com.ljw.gmall.payservice.config.AlipayConfig;
//import com.ljw.gmall.service.OrderService;
//import com.ljw.gmall.service.PaymentService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import javax.servlet.http.HttpServletRequest;
//import java.math.BigDecimal;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//@Controller
//public class PayserviceController {
//    @Autowired
//    AlipayClient alipayClient;
//    @Autowired
//    PaymentService paymentService;
//
//    @Reference
//    OrderService orderService;
//
//    @RequestMapping("alipay/submit")
//    @LoginRequired(loginSuccess = true)
//    public String alipay(String outTradeNo, BigDecimal totalAmount, HttpServletRequest request, ModelMap modelMap){
//        //获得一个支付宝请求的客户端（它不是一个链接而是一个封装好的http的表单请求）
//        String form = null;
//        AlipayTradeAppPayRequest alipayTradeAppPayRequest = new AlipayTradeAppPayRequest();
//
//        //回调函数
//        alipayTradeAppPayRequest.setReturnUrl(AlipayConfig.return_payment_url);
//        alipayTradeAppPayRequest.setNotifyUrl(AlipayConfig.notify_payment_url);
//
//        Map<String,Object> map = new HashMap<>();
//        map.put("out_trade_no",outTradeNo);
//        map.put("product_code","FAST_INSTANT_TRADE_PAY");
//        map.put("totalAmount",totalAmount);
//        map.put("subject","快付钱啦");
//
//        String param = JSON.toJSONString(map);
//        alipayTradeAppPayRequest.setBizContent(param);
//
//
//
//        try {
//            form = alipayClient.pageExecute(null).getBody();//调用sdk生成表单
//            System.out.println(form);
//        } catch (AlipayApiException e) {
//            e.printStackTrace();
//        }
//
//        //生成并且保存
//        OmsOrder omsOrder = orderService.getOrderByOutTradeNo();
//        PaymentInfo paymentInfo = new PaymentInfo();
//        paymentInfo.setCreateTime(new Date());
//        paymentInfo.setOrderId(omsOrder.getId());
//        paymentInfo.setOutTradeNo(outTradeNo);
//        paymentInfo.setPaymentStatus("未付款");
//
//
//
//        paymentService.=
//     return form;
//    }
//
//    @RequestMapping("index")
//    @LoginRequired(loginSuccess = true)
//    public String index(String outTradeNo, BigDecimal totalAmount, HttpServletRequest request, ModelMap modelMap){
//        String memberId  =(String)request.getAttribute("memberId");
//        String nickname = (String) request.getAttribute("nickname");
//        modelMap.put("totalAmount","totalAmount");
//        modelMap.put("nickname","nickname");
//        modelMap.put("outTradeNo","outTradeNo");
//        return "index";
//    }
//}
