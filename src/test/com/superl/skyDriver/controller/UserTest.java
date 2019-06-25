package com.superl.skyDriver.controller;

import com.superl.skyDriver.pojo.User;
import com.superl.skyDriver.responseEntry.JsonObjectResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-*.xml"})   //加载spring配置文件
public class UserTest {

    @Autowired
    private UserController userController;

    @Test
    public void register(){
        User user = new User();
        user.setUsername("liangjiegao");
        user.setAccount("15521168615");
        user.setPasswd("123456");
        JsonObjectResponse jsonObjectResponse = userController.register(user);
        System.out.println(jsonObjectResponse.getCode());
        System.out.println(jsonObjectResponse.getMessage());
    }
    @Test
    public void login(){
        User user = new User();
        user.setUsername("liangjiegao");
        user.setAccount("15521168615");
        user.setPasswd("123456");
        JsonObjectResponse response = userController.login(user);
        System.out.println(response.getCode());
        System.out.println(response.getMessage());
        System.out.println(((User)response.getObject()).getUsername());
    }
}
