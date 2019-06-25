package com.superl.skyDriver.service.impl;

import com.superl.skyDriver.mapper.FileMapper;
import com.superl.skyDriver.pojo.User;
import com.superl.skyDriver.pojo.UserFile;
import com.superl.skyDriver.pojo.WPFile;
import com.superl.skyDriver.service.ZipService;
import com.superl.skyDriver.utils.FileUtil;
import com.superl.skyDriver.utils.ZipCompress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class ZipServiceImpl implements ZipService {

    @Autowired
    private FileMapper fileMapper;

    /**
     * 压缩文件
     * @param fileIdList
     * @param linkId
     * @param zipName
     * @return
     */
    @Override
    public UserFile zipFile(Long[] fileIdList, Long linkId, String zipName, Long userId) {
        // 记录当前被压缩文件的位置，是在根目录，还是文件夹里面
        String parent = "";
        List<File> fileList = new ArrayList<>();
        System.out.println(fileIdList.length);
        Map<Long, List<File>> fileMap = new HashMap<>(); // 记录全部文件
        // 先查询出要压缩文件的路径
        for (Long fileId : fileIdList) {
            System.out.println("fid="+fileId);
            UserFile userFile = fileMapper.loadByFileId(fileId);
            System.out.println("f="+userFile);
            if (StringUtils.isEmpty(userFile.getFile().getFilePath())){  // 如果是文件夹
                fileList.add(new File(userFile.getFilename()+"?"+userFile.getId()));
//                System.out.println("插入d："+fileList.get(fileList.size() - 1).getName());
                findAllFileInDir(fileMap, userFile.getId());
            }else {
                fileList.add(new File(userFile.getFile().getFilePath()));
//                System.out.println("插入f："+userFile.getFilePath());
                List<File> list = new ArrayList<>();
                list.add(new File(userFile.getFile().getFilePath()));
                parent = userFile.getParent();
                fileMap.put(fileId, list);
            }
        }
        UserFile newZipFile = new UserFile();
        // 时间戳
        String prefix = new Date().getTime()+"";
        // 要保存zip文件的全路径名
        String saveFilePath = FileUtil.nowPath()+prefix+zipName+".zip";
        // 使用工具类进行压缩
        System.out.println("fileMap.21273 = "+fileMap.get(21273l));
        ZipCompress zipCompress = new ZipCompress(saveFilePath, fileMap);
        // 获取生成的文件, 主要是为了获取文件大小
        File file = new File(saveFilePath);
        try {
            // 保存文件到数据库中
            zipCompress.zip(fileList);
            WPFile wpFile = new WPFile();
            wpFile.setFilePath(saveFilePath);
            wpFile.setType("zip");
            wpFile.setSize(file.length());
            wpFile.setCreateDate(new Date());
            User user = new User();
            user.setId(userId);
            newZipFile = FileUtil.createUserFile(user, wpFile, zipName+".zip", parent, linkId);
            fileMapper.insertFile(newZipFile);
            fileMapper.insertFileUser(newZipFile);
            System.out.println("压缩结束");
            return newZipFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 用于递归文件夹中的文件
     * @param fileMap
     * @param linkId
     */
    public void findAllFileInDir(Map<Long, List<File>> fileMap, Long linkId){
        // 找出数据库中文件夹（对应linkId）下的所有文件
        List<UserFile> userFileList = fileMapper.loadByLinkId(linkId);
        // 如果该文件夹没有文件，则添加一个空的列表，到时候真正创建压缩包时会创建一个空的文件夹，避免报空指针
        if (userFileList.size() == 0){
            fileMap.put(linkId, new ArrayList<File>());
            return;
        }
        // 如果文件夹不为空，则遍历文件夹下的所有文件（包括文件夹中的文件夹）
        for (UserFile userFile : userFileList) {
            // 获取文件夹下的存放文件列表
            List<File> fileList = fileMap.get(linkId);
            // 如果该列表还没创建，则创建一个
            if (fileList == null){
                fileList = new ArrayList<>();
            }else {
                // 如果已经创建好了列表
                if (StringUtils.isEmpty(userFile.getFile().getFilePath())) { // 文件夹, 则需要递归
//                    System.out.println("文件夹"+userFile.getFilename()+"被添加");
                    // 如果是文件夹，将自己的Id作为存入文件的路径，查找如果发现是Id类型的路径，
                    // 则可以认为是文件夹，再从Map中用id作为key找list，这个list中的文件就是该文件夹下的文件
                    fileList.add(new File(userFile.getFilename()+"?"+userFile.getId()));
                    Long id1 = userFile.getId();
                    findAllFileInDir(fileMap, id1);
                }else {
//                    System.out.println("文件"+userFile.getFilename()+"被添加");
                    fileList.add(new File(userFile.getFile().getFilePath()));
                }
            }
        }


    }
    @Override
    public List<UserFile> unzipFile(Long fileId, Long linkId, String parent, Long userId) {
        List<UserFile> theUnzipFiles = new ArrayList<>();
        UserFile userFile = fileMapper.loadByFileId(fileId);
        // 如果文件不是压缩文件，返回内容为null的List，表示压缩失败
        if (!userFile.getFile().getType().equals("zip")){
            return theUnzipFiles;
        }
        File file = new File(userFile.getFile().getFilePath());
        // 要保存的文件夹1561013806831新建压缩文件
        String path = FileUtil.nowPath();
        ZipCompress zipCompress = new ZipCompress("", null);
        List<File> fileNameList =  zipCompress.unzip(file, path);
        // 遍历创建并插入文件数据
        for (File file1:fileNameList) {
            String type = FileUtil.distinguishFile(file1.getName().split("\\.")[1]);
            UserFile userFile1;
            WPFile wpFile = new WPFile();
            wpFile.setFilePath(file1.getPath());
            wpFile.setType(type);
            wpFile.setSize(file1.length());
            wpFile.setCreateDate(new Date());
            User user = new User();
            user.setId(userId);
            userFile1 = FileUtil.createUserFile(user, wpFile, file1.getName().substring(13, file1.getName().length()), parent, linkId);
            fileMapper.insertFile(userFile1);
            fileMapper.insertFileUser(userFile1);
            theUnzipFiles.add(userFile1);
        }
        return theUnzipFiles;
    }
}
