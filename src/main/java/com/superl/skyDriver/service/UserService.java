package com.superl.skyDriver.service;

import com.superl.skyDriver.pojo.User;

public interface UserService {

    User changeUserInfo(String account, String username);

    User getUserInfoByAccount(String account);

    String changePasswd(String account, String passwd, String newPasswd);
}
