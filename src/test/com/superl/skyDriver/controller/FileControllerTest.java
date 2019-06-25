package com.superl.skyDriver.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-*.xml"})   //加载spring配置文件
public class FileControllerTest {

    @Autowired
    private FileController fileController;

    @Test
    public void testFileUpload(){


    }

}
