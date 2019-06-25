package com.superl.skyDriver.mapper;

import com.superl.skyDriver.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-*.xml"})   //加载spring配置文件
public class UserTest {

    @Autowired
    private UserRegisterMapper userRegisterMapper;
    @Autowired
    private UserCommonMapper userCommonMapper;
    @Autowired
    private UserLoginMapper userLoginMapper;

    @Test
    public void register(){
        User user = new User();
        user.setUsername("superl");
        user.setAccount("15521168615");
        user.setPasswd("123456");
        userRegisterMapper.register(user);
    }
    @Test
    public void find(){
        User user = userLoginMapper.findUserByAccount("15521168615");
        System.out.println(user);
    }
}
