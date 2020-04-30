package com.ljw.gmall.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ljw.gmall.bean.PmsBaseCatalog1;
import com.ljw.gmall.bean.PmsBaseCatalog2;
import com.ljw.gmall.bean.PmsBaseCatalog3;
import com.ljw.gmall.service.CatagoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@CrossOrigin
public class CatalogController {

    @Reference
    CatagoryService catagoryService;

    @RequestMapping("getCatalog1")
    @ResponseBody   //表示返回的是json格式的数据
    public List<PmsBaseCatalog1> getCatelog1(){
        List<PmsBaseCatalog1> catalog1s = catagoryService.getCatalog1();
        return catalog1s;
    }

    @RequestMapping("getCatalog2")
    @ResponseBody   //表示返回的是json格式的数据
    public List<PmsBaseCatalog2> getCatelog2(String catalog1Id){
        List<PmsBaseCatalog2> catalog2s = catagoryService.getCatalog2(catalog1Id);
        return catalog2s;
    }

    @RequestMapping("getCatalog3")
    @ResponseBody
    public List<PmsBaseCatalog3> getCatalog3(String catalog2Id){
        List<PmsBaseCatalog3> catalog3s = catagoryService.getCatalog3(catalog2Id);
        return catalog3s;
    }


}
