package com.superl.skyDriver.mapper;

import com.superl.skyDriver.pojo.User;

public interface UserLoginMapper extends UserCommonMapper {

    User login(User user);

}
