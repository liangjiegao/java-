package com.superl.skyDriver.service.impl;

import com.superl.skyDriver.mapper.FileMapper;
import com.superl.skyDriver.pojo.User;
import com.superl.skyDriver.pojo.UserFile;
import com.superl.skyDriver.pojo.WPFile;
import com.superl.skyDriver.service.FileService;
import com.superl.skyDriver.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class FileServiceImpl implements FileService {

    @Autowired
    private FileMapper fileMapper;

    @Override
    public UserFile insertFile(MultipartFile file, String linkId, String parent, Long userId) {
        UserFile userFile = null;
        if (file != null){  // 上传文件非空
            // 保存文件到硬盘中
            String path = FileUtil.saveFile(file);
            String[] nameArr = file.getOriginalFilename().split("\\.");
            // 获取文件标志
            String suffix = nameArr[nameArr.length - 1];
            String type = FileUtil.distinguishFile(suffix);
            // 如果保存成功，则保存文件路径到数据库中
            if (path != null){
                userFile = new UserFile();
                userFile.setFilename(file.getOriginalFilename());
                userFile.setParent(parent);
                WPFile wpFile = new WPFile();
                wpFile.setFilePath(path);
                wpFile.setType(type);
                wpFile.setSize(file.getSize());
                wpFile.setCreateDate(new Date());
                userFile.setFile(wpFile);
                userFile.setLinkId(Long.parseLong(linkId));
                User user = new User();
                user.setId(userId);
                userFile.setUser(user);
                fileMapper.insertFile(userFile);
                fileMapper.insertFileUser(userFile);
            }
        }
        return userFile;
    }
    @Override
    public UserFile buildDir(Long linkId, String parent, Long userId, String filename) {
        UserFile userFile = new UserFile();
        userFile.setFilename(filename);
        userFile.setParent(parent);
        WPFile wpFile = new WPFile();
        wpFile.setFilePath("");
        wpFile.setType("dir");
        wpFile.setSize(0l);
        wpFile.setCreateDate(new Date());
        userFile.setFile(wpFile);
        userFile.setLinkId(linkId);
        User user = new User();
        user.setId(userId);
        userFile.setUser(user);
        fileMapper.insertFile(userFile);
        fileMapper.insertFileUser(userFile);
        return userFile;
    }

    @Override
    public boolean deleteFile(Long fileId) {
        UserFile userFile = fileMapper.loadByFileId(fileId);
        // 文件不存在，删除失败
        if (userFile == null) return false;
        System.out.println(userFile.getFile());
        String path = userFile.getFile().getFilePath();
        //文件存在
        if (path != null && !"".equals(path)){
            // 是文件而非文件夹，要删除硬盘中的文件
            File file = new File(path);
            // 文件在硬盘中存在
            if (file.isFile() && file.exists()){
//                file.delete(); // 需求更改，暂时不删除硬盘中的数据
                // 删除数据库中的字段
                fileMapper.deleteUserFile(fileId);
                return true;
            }
        }else {
            // 文件为文件夹，递归删除文件夹下的文件
            Long id = userFile.getId();
            List<UserFile> fileList = fileMapper.loadByLinkId(id);
            for (UserFile uf:fileList) {
                this.deleteFile(uf.getId());
            }
            // 删除外键关联
            fileMapper.deleteUserFile(fileId);
            // 再删除文件夹
//            fileMapper.deleteFile(fileId);  // 暂时不删除真正文件
            return true;
        }
        return false;
    }

    @Override
    public UserFile renameFile(String newFilename, Long id) {
        UserFile userFile = fileMapper.loadByFileId(id);
        fileMapper.renameFile(newFilename, id);
        userFile.setFilename(newFilename);
        return userFile;
    }

    @Override
    public UserFile downloadFile(Long fileId) {
        UserFile userFile = fileMapper.downloadFile(fileId);
        return userFile;
    }

    @Override
    public List<UserFile> loadByUserId(Long userId) {
        return fileMapper.loadByUserId(userId);
    }

    @Override
    public List<UserFile> loadByLinkId(Long linkId) {
        return fileMapper.loadByLinkId(linkId);
    }

    @Override
    public List<UserFile> loadByUserType(Long userId, String type) {
        return fileMapper.loadByUserType(userId, type);
    }

    @Override
    public List<UserFile> loadByUserIdLimit(Long userId, Long start, Long end) {
        return fileMapper.loadByUserIdLimit(userId, start, end);
    }

    @Override
    public List<UserFile> loadByLinkIdLimit(Long linkId, Long start, Long end) {
        return fileMapper.loadByLinkIdLimit(linkId, start, end);
    }

    @Override
    public List<UserFile> loadByUserTypeLimit(Long userId, String type, Long start, Long end) {
        return fileMapper.loadByUserTypeLimit(userId, type, start, end);
    }

    @Override
    public List<WPFile> loadAllWPFile() {
        return fileMapper.loadAllWPFile();
    }

    @Override
    public void deleteWPFileById(Long fileId) {
        fileMapper.deleteWPFileById(fileId);
    }

    @Override
    public List<Long> loadAllUserFile() {
        return fileMapper.loadAllUserFile();
    }

    @Override
    public Long loadCountByUserId(Long userId) {
        return fileMapper.loadCountByUserId(userId);
    }

    @Override
    public Long loadCountByLinkId(Long linkId) {
        return fileMapper.loadCountByLinkId(linkId);
    }

    @Override
    public Long loadCountByUserType(Long userId, String type) {
        return fileMapper.loadCountByUserType(userId, type);
    }


}
