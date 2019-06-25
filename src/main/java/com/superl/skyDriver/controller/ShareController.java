package com.superl.skyDriver.controller;

import com.superl.skyDriver.pojo.User;
import com.superl.skyDriver.pojo.UserFile;
import com.superl.skyDriver.responseEntry.JsonObjectResponse;
import com.superl.skyDriver.service.FileService;
import com.superl.skyDriver.service.ShareService;
import com.superl.skyDriver.service.UserCommonService;
import com.superl.skyDriver.service.UserLoginService;
import com.superl.skyDriver.utils.ControllerUtil;
import com.superl.skyDriver.utils.CryptoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

@Controller
public class ShareController {

    @Autowired
    private ShareService shareService;
    @Autowired
    private UserLoginService userLoginService;
    @Autowired
    private FileService fileService;

    @RequestMapping(value = {"/buildShareLink.do"}, method = RequestMethod.GET)
    @ResponseBody
    public JsonObjectResponse buildShareLink(@RequestParam("userId") Long userId,
                                             @RequestParam("sourceId") Long sourceId,
                                             @RequestParam("exPasswd") String exPasswd,
                                             @RequestParam("duration") Long duration){

        String link = shareService.buildShareLink(userId, sourceId, exPasswd, duration);
        return ControllerUtil.packageJOR("200", "建立分享链接成功", link);
    }

    @RequestMapping(value = {"/getShareSource.do"}, method = RequestMethod.GET)
    public ModelAndView getShareSource(@RequestParam(value = "link", required = true) String link,
                                       @RequestParam(value = "passwd", required = false) String passwd,
                                       Model model){
        ModelAndView mv = new ModelAndView();
        String info = CryptoUtil.decode32(CryptoUtil.DEFAULT_SECRET_KEY + CryptoUtil.SALT, link);
        System.out.println(info);
        String[] infoArr = info.split(";");
        if (infoArr.length != 4){ // 链接无效
            mv.setViewName("error");
            model.addAttribute("tip", "链接无效");
            return mv;
        }
        Long userId = Long.parseLong(infoArr[0]);
        Long sourceId = Long.parseLong(infoArr[1]);
        String exPasswd = infoArr[2];
        Long endTime = Long.parseLong(infoArr[3]);
        Date now = new Date();
        System.out.println("当前日期"+now.getTime());
        // 如果文件未过期才操作
        if (now.getTime() < endTime){
            UserFile userFile = null;
            model.addAttribute("link", link);
            // 获取分享者信息
            User user = userLoginService.findUserById(userId);
            // 无密码
            if ("".equals(exPasswd)){
                userFile = shareService.getSourceByFileIdNoPasswd(userId, sourceId);
                mv.setViewName("shareFileList");
            }else { // 有密码
                // 如果当前未输入密码，则当成第一次访问链接，返回输入密码提取资源页面
                if (passwd == null){
                    mv.setViewName("expasswd");
                }else if (exPasswd.equals(passwd)){
                    userFile = shareService.getSourceByFileId(userId, sourceId, exPasswd, passwd);
                    mv.setViewName("shareFileList");
                    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                    String account = (String) request.getSession().getAttribute("account");
                    System.out.println(account);
//                    mv.addObject("shareFile", userFile);
                }else { // 密码错误
                    mv.setViewName("expasswd");
                }
            }
            model.addAttribute("shareFile", userFile);
            model.addAttribute("user", user);
        }else { // 如果文件已经过期，提示过期页面
            mv.setViewName("timeout");
        }
        return mv;
    }

    @RequestMapping(value = {"/saveShare.do"}, method = RequestMethod.GET)
    @ResponseBody
    public JsonObjectResponse saveShare(@RequestParam("account") String account,
                                        @RequestParam("fileId") Long fileId,
                                        @RequestParam("fileName") String fileName,
                                        @RequestParam("parent") String parent,
                                        @RequestParam("linkId") Long linkId){

        try {
            // 中文乱码
            fileName = new String(fileName.getBytes("iso-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        shareService.saveShare(account, fileId, fileName, parent, linkId);
        return ControllerUtil.packageJOR("200", "保存成功！", null);

    }

    @RequestMapping(value = {"/loadUserDir.do"}, method = RequestMethod.GET)
    @ResponseBody
    public JsonObjectResponse loadUserDir(@RequestParam("account") String account,
                                          @RequestParam("linkId") Long linkId){
        User user = userLoginService.findUserByAccount(account);
        UserFile userFile = new UserFile();  // 创建根目录
        userFile.setId(user.getId());
        userFile.setFilename("/");
        userFile.setParent("user");
        List<UserFile> userFileList = null;
        if (user != null){ // 找到对应的user
            // 根据user id 找到用户根目录下的全部文件夹
            userFileList = shareService.loadUserDir(user, linkId);
            if (linkId == -1){ // 如果linkId = -1 证明是加载根目录
                userFileList.add(0, userFile); // 将根目录拼接到最前面
            }
//                linkId = user.getId();
            System.out.println(linkId);
            return ControllerUtil.packageJOR("200", "文件夹加载成功！", userFileList);
        }
        return ControllerUtil.packageJOR("500", "文件夹加载失败！", null);
    }
}
