package com.ljw.gmall.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ljw.gmall.bean.PmsBaseAttrInfo;
import com.ljw.gmall.bean.PmsBaseAttrValue;
import com.ljw.gmall.bean.PmsBaseSaleAttr;
import com.ljw.gmall.bean.PmsProductSaleAttr;
import com.ljw.gmall.service.PmsBaseAttrInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@CrossOrigin  //没有这个后端拿不到值  此注解解决跨域问题
public class AttrController  {

    @Reference
    PmsBaseAttrInfoService pmsBaseAttrInfoService;



    @RequestMapping("baseSaleAttrList")
    @ResponseBody

    public List<PmsBaseSaleAttr> baseSaleAttrList(){
        List<PmsBaseSaleAttr> pmsBaseSaleAttrs =  pmsBaseAttrInfoService.baseSaleAttrList();
        return pmsBaseSaleAttrs;
    }

    @RequestMapping("attrInfoList")
    @ResponseBody
    public List<PmsBaseAttrInfo> attrInfoList(String catalog3Id){
        List<PmsBaseAttrInfo> attrInfos =  pmsBaseAttrInfoService.attrInfoList(catalog3Id);
        return attrInfos;
    }

    @RequestMapping("saveAttrInfo")
    @ResponseBody
    public String saveAttrInfo(@RequestBody PmsBaseAttrInfo pmsBaseAttrInfo){

        String success = pmsBaseAttrInfoService.saveAttrInfo(pmsBaseAttrInfo);
        return "success";
    }

    //修改
//    @RequestMapping("getAttrValueList")
//    @ResponseBody
//    public String getAttrValueList(@RequestBody PmsBaseAttrInfo pmsBaseAttrInfo){
//
//        String success = pmsBaseAttrInfoService.saveAttrInfo(pmsBaseAttrInfo);
//        return "success";
//    }

    @RequestMapping("getAttrValueList")
    @ResponseBody
    public List<PmsBaseAttrValue> getAttrValueList(String attrId){

        List<PmsBaseAttrValue> pmsBaseAttrInfosValue = pmsBaseAttrInfoService.getAttrValueList(attrId);
        return pmsBaseAttrInfosValue;
    }

}
