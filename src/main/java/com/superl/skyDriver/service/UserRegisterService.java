package com.superl.skyDriver.service;

import com.superl.skyDriver.pojo.User;
import org.springframework.stereotype.Service;


public interface UserRegisterService extends UserCommonService{

    void register(User user);

}
