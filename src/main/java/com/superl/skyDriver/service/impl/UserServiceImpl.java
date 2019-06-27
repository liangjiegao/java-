package com.superl.skyDriver.service.impl;

import com.superl.skyDriver.mapper.UserMapper;
import com.superl.skyDriver.pojo.User;
import com.superl.skyDriver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User changeUserInfo(String account, String username) {
        User user = userMapper.getUserInfoByAccount(account);
        user.setUsername(username);
        userMapper.changeUserInfo(user);
        return user;
    }

    @Override
    public User getUserInfoByAccount(String account) {
        return userMapper.getUserInfoByAccount(account);
    }

    @Override
    public String changePasswd(String account, String passwd, String newPasswd) {
        User user = userMapper.getUserInfoByAccount(account);
        if (user.getPasswd().equals(passwd)){
            user.setPasswd(newPasswd);
            userMapper.changePasswd(user);
            return user.getAccount();
        }
        return "";
    }
}
