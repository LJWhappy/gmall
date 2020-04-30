package com.ljw.gmall.manager.service.imp;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.ljw.gmall.bean.PmsBaseCatalog1;
import com.ljw.gmall.bean.PmsBaseCatalog2;
import com.ljw.gmall.bean.PmsBaseCatalog3;
import com.ljw.gmall.manager.mapper.PmsBaseCatalog1Mapper;
import com.ljw.gmall.manager.mapper.PmsBaseCatalog2Mapper;
import com.ljw.gmall.manager.mapper.PmsBaseCatalog3Mapper;
import com.ljw.gmall.service.CatagoryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class CatagoryServiceImp implements CatagoryService {

    @Autowired
    PmsBaseCatalog1Mapper pmsBaseCatalog1Mapper;
    @Autowired
    PmsBaseCatalog2Mapper pmsBaseCatalog2Mapper;
    @Autowired
    PmsBaseCatalog3Mapper pmsBaseCatalog3Mapper;

    /**
     * 查询一级目录的所有内容
     * @return
     */
    @Override
    public List<PmsBaseCatalog1> getCatalog1() {
        return pmsBaseCatalog1Mapper.selectAll();
    }

    @Override
    public List<PmsBaseCatalog2> getCatalog2(String catalog1Id) {

        PmsBaseCatalog2 pmsBaseCatalog2 =new PmsBaseCatalog2();
        pmsBaseCatalog2.setCatalog1Id(catalog1Id);
        List<PmsBaseCatalog2> catalog2s = pmsBaseCatalog2Mapper.select(pmsBaseCatalog2);

        return catalog2s;
    }

    @Override
    public List<PmsBaseCatalog3> getCatalog3(String catalog2Id) {
        PmsBaseCatalog3 pmsBaseCatalog3 = new PmsBaseCatalog3();
        pmsBaseCatalog3.setCatalog2Id(catalog2Id);
        List<PmsBaseCatalog3> catalog3s = pmsBaseCatalog3Mapper.select(pmsBaseCatalog3);
        return catalog3s;
    }
}
