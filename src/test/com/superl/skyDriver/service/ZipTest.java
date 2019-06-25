package com.superl.skyDriver.service;

import com.superl.skyDriver.utils.ZipCompress;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-*.xml"})
public class ZipTest {

    @Autowired
    private ZipService zipService;
    @Test
    public void zipFile(){
       Long[] fileIdList = new Long[2];
        fileIdList[0] = 21282l;
        fileIdList[1] = 21273l;
        zipService.zipFile(fileIdList, 10015l, "测试压缩文件夹", 10015l);
    }

    @Test
    public void unZip(){

        ZipCompress zipCompress = new ZipCompress(null, null);
        File file = new File("E:/development/upload/20196/测试.zip");
        zipCompress.unzip(file, "E:/development/upload/20196/");
//        zipService.unzipFile()
    }
}
