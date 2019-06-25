package com.superl.skyDriver.mapper;

import com.superl.skyDriver.pojo.User;

public interface UserCommonMapper {
    User findUserByAccount(String account);
    User findUserById(Long id);
}
