package com.superl.skyDriver.aspect;

import com.superl.skyDriver.pojo.User;
import com.superl.skyDriver.responseEntry.JsonObjectResponse;
import com.superl.skyDriver.utils.ControllerUtil;
import org.apache.taglibs.standard.lang.jstl.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Aspect
public class UserAspect {


    @Pointcut("execution(* com.superl.skyDriver.controller.*.*(..)) && !within(com.superl.skyDriver.controller.UserController) && !within(com.superl.skyDriver.controller.MainController)") // IndexController中写了登录方法
    public void checkLogin() {
    }
    @Pointcut("execution(* com.superl.skyDriver.controller.FileController.downloadFile(..))") // IndexController中写了登录方法
    public void checkDownload() {
    }

    @Pointcut("execution(* com.superl.skyDriver.controller.UserController.login())") // IndexController中写了登录方法
    public void login() {
    }
    @Around("checkLogin()")
    public Object auth(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取session中的用户信息
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String account = (String) request.getSession().getAttribute("account");

        if (account == null) {
            System.out.println("未登陆");
            return new ModelAndView("redirect:/loginPage.do");
        }
        System.out.println("account: " + account);
        return joinPoint.proceed();
    }

//    @Around("checkDownload()")
//    public JsonObjectResponse downloadLimit(ProceedingJoinPoint joinPoint) throws Throwable {
//
//    }

}
