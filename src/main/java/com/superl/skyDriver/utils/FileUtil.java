package com.superl.skyDriver.utils;

import com.superl.skyDriver.pojo.User;
import com.superl.skyDriver.pojo.UserFile;
import com.superl.skyDriver.pojo.WPFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class FileUtil {
//    private static final String PATH = "E:/development/upload/";
    private static final String PATH = "/home/download/SkyDriverDownload/";

    public static String nowPath(){
        Calendar cale = Calendar.getInstance();
        String yearMonth = cale.get(Calendar.YEAR)+""+(cale.get(Calendar.MONTH)+1)+"/";
        return PATH+yearMonth;
    }
    /**
     * 保存文件到文件夹
     * @param file
     * @return
     */
    public static String saveFile(MultipartFile file){
        System.out.println("fileName："+file.getOriginalFilename());
        String nowPath = nowPath();
        File ymDir = new File(nowPath);
        if (!ymDir.exists()){
            ymDir.mkdir();
        }
        String path = nowPath+new Date().getTime()+file.getOriginalFilename();
        File newFile = new File(path);
        try {
            file.transferTo(newFile);
            return path;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 文件分类
     * @param suffix
     * @return
     */
    public static String distinguishFile(String suffix){
        switch (suffix){
            case "jpg":
            case "png":
            case "jpeg":
            case "gif":
            case "bmp":
                return "image";
            case "txt":
            case "doc":
            case "pdf":
            case "xlsx":
            case "docx":
                return "file";
            case "mp4":
            case "3gp":
            case "avi":
                return "video";
            case "mp3":
                return "music";
            case "dir":
                return "dir";
            case "zip":
                return "zip";
        }
        return "other";
    }

    /**
     *  private Long id;
     *     private User user;
     *     private WPFile file;
     *     private String filename;
     *     private String parent;
     *     private Long linkId;
     * @return
     */
    public static UserFile createUserFile(User user, WPFile file, String filename, String parent,Long linkId){
        UserFile userFile = new UserFile();
        userFile.setUser(user);
        userFile.setFile(file);
        userFile.setFilename(filename);
        userFile.setParent(parent);
        userFile.setLinkId(linkId);
        return userFile;
    }

    public static String getPATH() {
        return PATH;
    }
}
