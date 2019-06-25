package com.superl.skyDriver.mapper;

import com.superl.skyDriver.pojo.UserFile;
import com.superl.skyDriver.pojo.WPFile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-*.xml"})   //加载spring配置文件
public class FileMapperTest {

    @Autowired
    private FileMapper fileMapper;

    @Test
    public void downloadTest(){

        UserFile userFile = fileMapper.downloadFile(95l);
        System.out.println(userFile.getFile().getFilePath());

    }

    @Test
    public void count(){
        Long total = fileMapper.loadCountByUserId(10015l);
        System.out.println(total);
    }
    @Test
    public void searchByFilename(){

        List<UserFile> list = fileMapper.searchByFilename("A");
        System.out.println(list.size());
    }
}
