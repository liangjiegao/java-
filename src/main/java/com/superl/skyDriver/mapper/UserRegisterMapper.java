package com.superl.skyDriver.mapper;

import com.superl.skyDriver.pojo.User;

public interface UserRegisterMapper extends UserCommonMapper {

    void register(User user);

}
