package com.superl.skyDriver.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class FileExchangeUtil {

    public static boolean fileDownloadLimit(HttpServletRequest request){
        System.out.println("拦截下载开始");
        // 获取session中的用户信息
        String account = (String) request.getSession().getAttribute("account");
        System.out.println("账号："+account);
        Integer downNum = (Integer) request.getSession().getAttribute(account+"d");
        System.out.println("下载数："+downNum);
        if (downNum == null || downNum == 0){
            request.getSession().setAttribute(account+"d", 1);
        }else if (downNum > 0 && downNum <= 2){
            request.getSession().setAttribute(account+"d", downNum + 1);
        }else {
            return false;
        }
        return true;
    }

}
