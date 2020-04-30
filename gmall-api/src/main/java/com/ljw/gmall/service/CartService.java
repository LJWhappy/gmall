package com.ljw.gmall.service;

import com.ljw.gmall.bean.OmsCartItem;
import com.ljw.gmall.bean.OmsOrderItem;

import java.util.List;

public interface CartService {
    OmsCartItem ifCartExistByUser(String memberId, String skuId);

    void addCart(OmsCartItem omsCartItem);

    void updateCart(OmsCartItem omsCartItemFromDb);

    void flushCartCache(String memberId);

    void checkCart(OmsCartItem omsCartItem);

    List<OmsCartItem> cartList(String memberId);

    void delCart(OmsOrderItem orderItem);
}
