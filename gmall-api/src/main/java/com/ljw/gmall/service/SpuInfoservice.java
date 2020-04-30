package com.ljw.gmall.service;

import com.ljw.gmall.bean.PmsProductImage;
import com.ljw.gmall.bean.PmsProductInfo;
import com.ljw.gmall.bean.PmsProductSaleAttr;

import java.util.List;

public interface SpuInfoservice {


    List<PmsProductInfo> spuList(String catalog3Id);

    void saveSpuInfo(PmsProductInfo pmsProductInfo);

    List<PmsProductImage> spuImageList(String spuId);

    List<PmsProductSaleAttr> spuSaleAttrList(String spuId);


    List<PmsProductSaleAttr> spuSaleAttrListCheckBySku(String productId, String skuid);
}
