package com.ljw.gmall.user.service.imp;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.ljw.gmall.bean.UmsMember;
import com.ljw.gmall.bean.UmsMemberReceiveAddress;
import com.ljw.gmall.service.UserService;
import com.ljw.gmall.user.mapper.UmsMemberReceiveAddressMapper;
import com.ljw.gmall.user.mapper.UserMapper;
import com.ljw.gmall.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class UserServiceImp  implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UmsMemberReceiveAddressMapper umsMemberReceiveAddressMapper;

    @Autowired
    RedisUtil redisUtil;

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

    @Override
    public UmsMember login(UmsMember umsMember) {
        Jedis jedis = null;
        try{
            jedis = redisUtil.getJedis();

            if (jedis!=null){
                String umsMemberStr = jedis.get("user:"+umsMember.getPassword()+":info");
                if (StringUtils.isNotBlank(umsMemberStr)){
                    //密码正确
                    UmsMember umsMemberFromCache = JSON.parseObject(umsMemberStr, UmsMember.class);
                    return umsMemberFromCache;
                }
            }
            //连接redis失败  密码错误  缓存没有  开数据库
            UmsMember umsMemberFromDB = loginFromDB(umsMember);
            if (umsMemberFromDB!=null){
                jedis.setex("user:"+umsMember.getPassword()+":info",60*60*24,JSON.toJSONString(umsMemberFromDB));

            }

            return umsMemberFromDB;

        }finally {
            jedis.close();
        }


    }

    @Override
    public void addUserToken(String token, String memberId) {
        Jedis jedis = redisUtil.getJedis();
        jedis.setex("user:"+memberId+":token",60*60*2,token);
        jedis.close();
    }

    @Override
    public void addOauthUser(UmsMember umsMember) {
        userMapper.insertSelective(umsMember);
    }

    @Override
    public UmsMember checkOauthUser(UmsMember umsCheck) {
        UmsMember umsMember = userMapper.selectOne(umsCheck);
        return umsMember;
    }

    @Override
    public UmsMemberReceiveAddress getReceiveAddressById(String receiveAddressId) {
        UmsMemberReceiveAddress umsMemberReceiveAddress = new UmsMemberReceiveAddress();
        umsMemberReceiveAddress.setId(receiveAddressId);
        UmsMemberReceiveAddress umsMemberReceiveAddress1 = umsMemberReceiveAddressMapper.selectOne(umsMemberReceiveAddress);
        return umsMemberReceiveAddress1;
    }

    private UmsMember loginFromDB(UmsMember umsMember) {


        List<UmsMember> umsMembers = userMapper.select(umsMember);
        if (umsMembers!=null){
            return umsMembers.get(0);
        }

        return null;
    }
}
