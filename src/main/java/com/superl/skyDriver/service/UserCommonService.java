package com.superl.skyDriver.service;

import com.superl.skyDriver.pojo.User;

public interface UserCommonService {
    User findUserByAccount(String account);
    User findUserById(Long id);
}
