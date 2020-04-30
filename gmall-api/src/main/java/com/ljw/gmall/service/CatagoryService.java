package com.ljw.gmall.service;

import com.ljw.gmall.bean.PmsBaseCatalog1;
import com.ljw.gmall.bean.PmsBaseCatalog2;
import com.ljw.gmall.bean.PmsBaseCatalog3;

import java.util.List;

public interface CatagoryService {
    List<PmsBaseCatalog1> getCatalog1();

    List<PmsBaseCatalog2> getCatalog2(String catalog1Id);

    List<PmsBaseCatalog3> getCatalog3(String catalog2Id);
}
