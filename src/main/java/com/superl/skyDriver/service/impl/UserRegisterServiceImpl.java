package com.superl.skyDriver.service.impl;

import com.superl.skyDriver.mapper.UserRegisterMapper;
import com.superl.skyDriver.pojo.User;
import com.superl.skyDriver.service.UserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRegisterServiceImpl implements UserRegisterService {

    @Autowired
    private UserRegisterMapper userRegisterMapper;

    @Override
    public void register(User user) {
        userRegisterMapper.register(user);
    }

    @Override
    public User findUserByAccount(String account) {
        return userRegisterMapper.findUserByAccount(account);
    }

    @Override
    public User findUserById(Long id) {
        return userRegisterMapper.findUserById(id);
    }

}
