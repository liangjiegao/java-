package com.superl.skyDriver.mapper;

import com.superl.skyDriver.pojo.User;

public interface UserMapper {

    void changeUserInfo(User user);

    User getUserInfoByAccount(String account);

    void changePasswd(User user);


}
