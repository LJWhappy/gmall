package com.ljw.gmall.service;

import com.ljw.gmall.bean.PmsBaseAttrInfo;
import com.ljw.gmall.bean.PmsBaseAttrValue;
import com.ljw.gmall.bean.PmsBaseSaleAttr;

import java.util.List;
import java.util.Set;

public interface PmsBaseAttrInfoService {

    List<PmsBaseAttrInfo> attrInfoList(String catalog3Id);

    String saveAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo);

    List<PmsBaseAttrValue> getAttrValueList(String attrId);

    List<PmsBaseSaleAttr> baseSaleAttrList();

    List<PmsBaseAttrInfo> getAttrValueListByValueId(Set<String> valueIdSet);
}
