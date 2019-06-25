package com.superl.skyDriver.service;

import com.superl.skyDriver.pojo.User;

public interface UserLoginService extends UserCommonService{
    User login(User user);

}
