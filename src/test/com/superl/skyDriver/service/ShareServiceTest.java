package com.superl.skyDriver.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-*.xml"})
public class ShareServiceTest {

    @Autowired
    private ShareService shareService;

    @Test
    public void buildLink(){
        Long userId = 10015l;
        Long sourceId = 5l;
        String exPasswd = "123456";
        Long d = new Date().getTime() + 7 * 24 * 3600 * 1000;
        String link = shareService.buildShareLink(userId, sourceId, exPasswd, d);
        System.out.println(link);
    }

    @Test
    public void decodeLink(){

//        shareService.getFileByLink("2BBASXOEN3DLSC4EO4SKG64CYGNEWI5KSWARRLA");
        Date date = new Date();
        System.out.println(date.getTime());
        try {
            Thread.sleep(1000l);
            Date date1 = new Date();
            System.out.println(date1.getTime());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
