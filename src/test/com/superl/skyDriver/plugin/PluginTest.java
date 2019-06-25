package com.superl.skyDriver.plugin;

import com.superl.skyDriver.mapper.PluginMapper;
import com.superl.skyDriver.pojo.User;
import com.superl.skyDriver.service.UserRegisterService;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-*.xml"})   //加载spring配置文件
public class PluginTest {

    @Autowired
    private PluginMapper pluginMapper;

    @Test
    public void sqlPlugin(){
//        SqlSession sqlSession = SqlSessionFactory
        pluginMapper.getUser(10015l);
    }


}
