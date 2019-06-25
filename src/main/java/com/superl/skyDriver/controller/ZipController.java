package com.superl.skyDriver.controller;

import com.superl.skyDriver.pojo.UserFile;
import com.superl.skyDriver.responseEntry.JsonObjectResponse;
import com.superl.skyDriver.service.ZipService;
import com.superl.skyDriver.utils.ControllerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
public class ZipController {

    @Autowired
    private ZipService zipService;

    @RequestMapping(value = {"/zipFile.do"}, method = RequestMethod.GET)
    @ResponseBody
    public JsonObjectResponse zipFile(@RequestParam("fileIds") Long[] fileIds,
                                      @RequestParam("linkId") Long linkId,
                                      @RequestParam("zipName") String zipName,
                                      @RequestParam("userId") Long userId){
        try {
            zipName = new String(zipName.getBytes("ISO-8859-1"), "UTF-8");
            JsonObjectResponse jsonObjectResponse;
            UserFile userFile = zipService.zipFile(fileIds, linkId, zipName, userId);
            System.out.println(userFile);
            if (userFile != null){
                jsonObjectResponse = ControllerUtil.packageJOR("200", "压缩成功！", userFile);
                return jsonObjectResponse;
            }
            return ControllerUtil.packageJOR("500", "压缩失败！", null);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return ControllerUtil.packageJOR("500", "压缩失败！", null);
        }
    }

    @RequestMapping(value = {"/unzipFile.do"}, method = RequestMethod.GET)
    @ResponseBody
    public JsonObjectResponse unzipFile(@RequestParam("fileId") Long fileId,
                                        @RequestParam("linkId") Long linkId,
                                        @RequestParam("parent") String parent,
                                        @RequestParam("userId") Long userId){
        List<UserFile> theUnzipFile = zipService.unzipFile(fileId, linkId, parent, userId);
        if (theUnzipFile != null){
            return ControllerUtil.packageJOR("200", "解压成功！", theUnzipFile);
        }else {
            return ControllerUtil.packageJOR("500", "解压失败！", null);
        }
    }
}
