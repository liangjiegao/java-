package com.superl.skyDriver.service.impl;

import com.superl.skyDriver.mapper.UserLoginMapper;
import com.superl.skyDriver.pojo.User;
import com.superl.skyDriver.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserLoginServiceImpl implements UserLoginService {

    @Autowired
    private UserLoginMapper userLoginMapper;

    @Override
    public User login(User user) {
        return userLoginMapper.login(user);
    }

    @Override
    public User findUserByAccount(String account) {
        return userLoginMapper.findUserByAccount(account);
    }

    @Override
    public User findUserById(Long id) {
        return userLoginMapper.findUserById(id);
    }
}
