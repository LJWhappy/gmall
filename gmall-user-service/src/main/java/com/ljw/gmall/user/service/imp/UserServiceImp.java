package com.ljw.gmall.user.service.imp;

import com.alibaba.dubbo.config.annotation.Service;
import com.ljw.gmall.bean.UmsMember;
import com.ljw.gmall.bean.UmsMemberReceiveAddress;
import com.ljw.gmall.service.UserService;
import com.ljw.gmall.user.mapper.UmsMemberReceiveAddressMapper;
import com.ljw.gmall.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class UserServiceImp  implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UmsMemberReceiveAddressMapper umsMemberReceiveAddressMapper;

    @Override
    public List<UmsMember> getAllUser() {
        List<UmsMember> umsMember=userMapper.selectAll(); //userMapper.selectAllUser();
        return umsMember;
    }

    /**
     * 查询条件映射到了对象umsMemberReceiveAddresses
     * @param memberId
     * @return
     */
    @Override
    public List<UmsMemberReceiveAddress> getReceiveAddressByUserMemberId(String memberId) {
//        UmsMemberReceiveAddress umsMemberReceiveAddress= new UmsMemberReceiveAddress();
//        umsMemberReceiveAddress.setMember_id(memberId);
//        List<UmsMemberReceiveAddress> umsMemberReceiveAddresses = umsMemberReceiveAddressMapper.select(umsMemberReceiveAddress);
        Example example = new Example(UmsMemberReceiveAddress.class);
        example.createCriteria().andEqualTo("memberId",memberId);
        List<UmsMemberReceiveAddress> umsMemberReceiveAddresses= umsMemberReceiveAddressMapper.selectByExample(example);
        return umsMemberReceiveAddresses;
    }

    @Override
    public int insertUser(UmsMember umsMember) {
//        umsMember.setBirthday();
        return 0;
    }
}
