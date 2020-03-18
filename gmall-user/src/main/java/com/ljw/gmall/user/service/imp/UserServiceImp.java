package com.ljw.gmall.user.service.imp;

import com.ljw.gmall.user.mapper.UserMapper;
import com.ljw.gmall.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp  implements UserService {

    @Autowired
    UserMapper userMapper;
}
