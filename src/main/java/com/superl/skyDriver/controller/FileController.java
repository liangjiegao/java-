package com.superl.skyDriver.controller;

import com.superl.skyDriver.pojo.UserFile;
import com.superl.skyDriver.responseEntry.JsonObjectResponse;
import com.superl.skyDriver.responseEntry.PageingObject;
import com.superl.skyDriver.service.FileService;
import com.superl.skyDriver.utils.ControllerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@Controller
public class FileController {

    @Autowired
    private FileService fileService;


    @RequestMapping(value = {"/uploadFile.do"}, method = RequestMethod.POST)
    @ResponseBody
    public JsonObjectResponse uploadFile(@RequestParam("file") MultipartFile file,
                             @RequestParam("linkId") String linkId,
                             @RequestParam("parent") String parent,
                                         @RequestParam("userId") Long userId){

        JsonObjectResponse jsonObjectResponse = null;
        UserFile userFile = fileService.insertFile(file, linkId, parent, userId);
        if (userFile != null) {
            jsonObjectResponse = ControllerUtil.packageJOR("200", "文件上传成功！", userFile);
        }else {
            jsonObjectResponse = ControllerUtil.packageJOR("500", "文件上传失败！", null);
        }
        return jsonObjectResponse;
    }

    /**
     * 创建文件夹
     * @param linkId
     * @param parent
     * @param userId
     * @param filename
     * @return
     */
    @RequestMapping(value = {"/buildNewDir.do"}, method = RequestMethod.GET)
    @ResponseBody
    public JsonObjectResponse buildNewDir(Long linkId, String parent, Long userId, String filename){
        JsonObjectResponse jsonObjectResponse = null;
        try {
            filename = new String(filename.getBytes("ISO-8859-1"), "UTF-8");
            UserFile newDir = fileService.buildDir(linkId, parent, userId, filename);
            jsonObjectResponse = ControllerUtil.packageJOR("200", "文件上传成功！", newDir);
        } catch (UnsupportedEncodingException e) {
            jsonObjectResponse = ControllerUtil.packageJOR("500", "文件上传失败！", null);
            e.printStackTrace();
        }
        return jsonObjectResponse;
    }

    @RequestMapping(value = {"/loadFile.do"}, method = RequestMethod.GET)
    @ResponseBody
    public PageingObject loadFile(Long linkId, String parent, Long userId, String type, Long start, Long end){
        PageingObject<List<UserFile>> pageingObject = new PageingObject<>();
        List<UserFile> userFileList = null;
        Long total = 0l;
        Long nowPage = (start / 20) + 1;  // 从1开始
        // 如果不是按照个人分类来查询，而是按照目录级别查询
        if (userId == -1){
            // 根目录
            if (parent.equals("user")){
                userFileList = fileService.loadByUserIdLimit(linkId, start, end);
                total = fileService.loadCountByUserId(linkId);
                System.out.println("总数："+total);
            }
            // 次级文件
            else{
                userFileList = fileService.loadByLinkIdLimit(linkId, start, end);
                total = fileService.loadCountByLinkId(linkId);
            }
        }else {
            // 如果按照分类查询
            userFileList = fileService.loadByUserTypeLimit(userId, type, start, end);
            total = fileService.loadCountByUserType(userId, type);
        }
        pageingObject.setObj(userFileList);
        pageingObject.setTotal(total);
        pageingObject.setNowPage(nowPage);
        return pageingObject;
    }


    @RequestMapping(value = {"/deleteFile.do"}, method = RequestMethod.GET)
    @ResponseBody
    public JsonObjectResponse deleteFile(Long fileId){
        boolean result = fileService.deleteFile(fileId);
        JsonObjectResponse jsonObjectResponse = null;
        if (result){
            jsonObjectResponse = ControllerUtil.packageJOR("200", "删除成功！", null);
        }else {
            jsonObjectResponse = ControllerUtil.packageJOR("500", "删除失败！", null);
        }
        return jsonObjectResponse;
    }
    @RequestMapping(value = {"/rename.do"}, method = RequestMethod.GET)
    @ResponseBody
    public JsonObjectResponse rename(@RequestParam("newFilename") String newFilename,
                                     @RequestParam("fileId") Long fileId){
        JsonObjectResponse jsonObjectResponse = null;
        try {
            newFilename = new String(newFilename.getBytes("ISO-8859-1"), "UTF-8");
            UserFile userFile = fileService.renameFile(newFilename, fileId);
            if (userFile != null){
                jsonObjectResponse = ControllerUtil.packageJOR("200", "重命名成功！", userFile);
            }else {
                jsonObjectResponse = ControllerUtil.packageJOR("500", "重命名失败！", null);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return jsonObjectResponse;
    }

    @RequestMapping(value = {"/downloadFile.do"}, method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public void downloadFile(@RequestParam("fileId") Long fileId,
                                HttpServletRequest request,
                                HttpServletResponse response){

        UserFile userFile = fileService.downloadFile(fileId);
        if (userFile != null){
            String path = userFile.getFile().getFilePath();
            String fileName = null;
            try {
                fileName = new String( userFile.getFilename().getBytes("UTF-8"), "iso-8859-1");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            File file = new File(path);
            if (file.isFile() && file.exists()){
                try {
                    InputStream is = new BufferedInputStream(new FileInputStream(file));
                    response.reset();
                    response.setCharacterEncoding("utf-8");
                    response.setContentType("application/force-download");
                    response.setHeader("Content-Disposition", "attachment;fileName="+fileName);
                    OutputStream os = new BufferedOutputStream(response.getOutputStream());
                    // 检查用户同时下载数是否超出限制
                    byte[] buffer = new byte[1024*1024*10];
                    int len = -1;
                    while ((len = is.read(buffer)) != -1){
                        os.write(buffer, 0, len);
                        System.out.println(len);
                    }
                    os.flush();
                    os.close();
                    is.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    // 数据传输异常，有可能是用户取消下载导致中断
                    e.printStackTrace();
                }
            }
//            System.out.println("拦截下载结束");
            // 获取实时数量，不能用之前那个，因为多线程会同时修改该变量
//            String account = (String) request.getSession().getAttribute("account");
//            Integer downNumNow = (Integer) request.getSession().getAttribute(account+"d");
//            request.getSession().setAttribute(account+"d", downNumNow - 1);
        }
    }

}
