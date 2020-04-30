package com.ljw.gmall.service;

import com.ljw.gmall.bean.PmsSearchParam;
import com.ljw.gmall.bean.PmsSearchSkuInfo;

import java.util.List;

public interface SearchService {
    List<PmsSearchSkuInfo> list(PmsSearchParam pmsSearchParam);
}
