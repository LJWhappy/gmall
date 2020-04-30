package com.ljw.gmall.service;

import com.ljw.gmall.bean.OmsOrder;

public interface OrderService {
    String checkTradeCode(String memberId, String tradeCode);

    void saveOrder(OmsOrder omsOrder);

    String genTradeCode(String memberId);
}
