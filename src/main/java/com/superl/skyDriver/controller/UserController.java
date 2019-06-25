package com.superl.skyDriver.controller;

import com.superl.skyDriver.pojo.User;
import com.superl.skyDriver.responseEntry.JsonObjectResponse;
import com.superl.skyDriver.service.UserLoginService;
import com.superl.skyDriver.service.UserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    @Autowired
    private UserRegisterService userRegisterService;
    @Autowired
    private UserLoginService userLoginService;

    /**
     * 访问登录页面
     * @return
     */
    @RequestMapping(value = {"/loginPage.do"}, method = RequestMethod.GET)
    public String loginPage(){
        return "login";
    }

    /**
     * 访问注册页面
     * @return
     */
    @RequestMapping(value = {"/registerPage.do"}, method = RequestMethod.GET)
    public String registerPage(){
        return "register";
    }


    /**
     * 注册处理功能
     * @param user
     * @return
     */
    @RequestMapping(value = {"/register.do"}, method = RequestMethod.POST)
    @ResponseBody
    public JsonObjectResponse register(@RequestBody User user){
        // 查询账号是否已经注册
        User findUser = userRegisterService.findUserByAccount(user.getAccount());
        // 一个信息返回对象，封装了一些返回到一面用于判断是否请求成功的信息
        JsonObjectResponse jsonObjectResponse = new JsonObjectResponse();
        if (findUser == null){
           userRegisterService.register(user);
            jsonObjectResponse.setCode("200");
            jsonObjectResponse.setMessage("注册成功！");
            return jsonObjectResponse;
        }else {
            jsonObjectResponse.setCode("500");
            jsonObjectResponse.setMessage("账号已存在！");
            return jsonObjectResponse;
        }
    }

    /**
     * 登录功能
     * @param user
     * @return
     */
    @RequestMapping(value = {"/login.do"}, method = RequestMethod.POST)
    @ResponseBody
    public JsonObjectResponse login(@RequestBody User user){
        JsonObjectResponse jsonObjectResponse = new JsonObjectResponse();
        // 如果没有输入登录信息，直接返回
        if (user == null){
            jsonObjectResponse.setCode("500");
            jsonObjectResponse.setMessage("请输入登录信息！");
            return  jsonObjectResponse;
        }
        // 查询用户信息是否正确
        User outUser = userLoginService.login(user);
        // 如果不正确，返回错误信息
        if (outUser == null){
            jsonObjectResponse.setCode("500");
            jsonObjectResponse.setMessage("登录失败，用户名或密码错误！");
            return  jsonObjectResponse;
        }else {
            // 如果正确则登录成功
            jsonObjectResponse.setCode("200");
            jsonObjectResponse.setMessage("登录成功！");
            jsonObjectResponse.setObject(outUser);
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            request.getSession().setAttribute("account", user.getAccount());
            return jsonObjectResponse;
        }
    }

    /**
     * 退出登录
     * @return
     */
    @RequestMapping(value = {"/logout.do"}, method = RequestMethod.GET)
    public String logout(){
        // 获取Session保存的用户信息
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String account = (String)request.getSession().getAttribute("account");
        // 如果该账户有登录,则退出登录，清楚Session信息
        if (account != null){
            request.getSession().setAttribute("account", null);
        }
        return "home";
    }

    /**
     * 跳转用户主页
     * @param model
     * @return
     */
    @RequestMapping(value = {"/index.do"}, method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView mainPage(Model model){
        // 获取用户信息
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String account = (String)request.getSession().getAttribute("account");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("home");
        // 输入账号不为空
        if (account != null){
            User user = userLoginService.findUserByAccount(account);
            // 账号正确， 查询到用户
            if (user != null){
                mv.setViewName("index");
                model.addAttribute("user", user);
                return mv;
            }
        }
        return mv;
    }
}
