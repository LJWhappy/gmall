package com.ljw.gmall.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ljw.gmall.bean.PmsProductImage;
import com.ljw.gmall.bean.PmsProductInfo;
import com.ljw.gmall.bean.PmsProductSaleAttr;
import com.ljw.gmall.manager.util.PmsUploadUtil;
import com.ljw.gmall.service.SpuInfoservice;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@CrossOrigin
public class SpuCotroller {

    @Reference
    SpuInfoservice spuInfoservice;

    @RequestMapping("spuSaleAttrList")
    @ResponseBody
    public List<PmsProductSaleAttr> spuSaleAttrList(String spuId){

        List<PmsProductSaleAttr> pmsProductSaleAttrs =  spuInfoservice.spuSaleAttrList(spuId);

        return  pmsProductSaleAttrs;
    }


    @RequestMapping("spuImageList")
    @ResponseBody
    public List<PmsProductImage> spuImageList(String spuId){

        List<PmsProductImage> pmsProductImages =  spuInfoservice.spuImageList(spuId);

        return  pmsProductImages;
    }


    @RequestMapping("spuList")
    @ResponseBody
    public List<PmsProductInfo> spuList(String catalog3Id){

        List<PmsProductInfo> pmsProductInfos =  spuInfoservice.spuList(catalog3Id);

        return  pmsProductInfos;
    }

    @RequestMapping("saveSpuInfo")
    @ResponseBody
    public String saveSpuInfo(@RequestBody  PmsProductInfo pmsProductInfo){

        spuInfoservice.saveSpuInfo(pmsProductInfo);
        return  "success";
    }

    //图片上传
    @RequestMapping("fileUpload")
    @ResponseBody
    public String fileUpload(@RequestParam("file") MultipartFile multipartFile){
        //将图片或者音视频上传到分布式的文件存储系统

        //将图片的存储路径返回给页面
        String imageUrl= PmsUploadUtil.uploadImage(multipartFile);
        System.out.println(imageUrl);
        return  imageUrl;
    }
}
