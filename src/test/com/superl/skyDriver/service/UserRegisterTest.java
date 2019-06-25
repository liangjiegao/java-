package com.superl.skyDriver.service;

import com.superl.skyDriver.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-*.xml"})   //加载spring配置文件
public class UserRegisterTest {

    @Autowired
    private UserRegisterService userRegisterService;

    @Test
    public void register(){
        User user = new User();
        user.setId(10011);
        user.setUsername("superl1");
        user.setAccount("15521168615");
        user.setPasswd("123456");
        userRegisterService.register(user);
    }
    @Test
    public void findUserByUsername(){
        User user = userRegisterService.findUserByAccount("liangjiegao");
        System.out.println(user.getUsername()+" " + user.getAccount());
    }

}
