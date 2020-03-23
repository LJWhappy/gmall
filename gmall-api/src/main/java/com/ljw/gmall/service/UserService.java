package com.ljw.gmall.service;

import com.ljw.gmall.bean.UmsMember;
import com.ljw.gmall.bean.UmsMemberReceiveAddress;

import java.util.List;

public interface UserService {

    List<UmsMember> getAllUser();

    List<UmsMemberReceiveAddress> getReceiveAddressByUserMemberId(String memberId);

    int insertUser(UmsMember umsMember);
}
