package com.superl.skyDriver.service.impl;

import com.superl.skyDriver.mapper.FileMapper;
import com.superl.skyDriver.mapper.ShareMapper;
import com.superl.skyDriver.mapper.UserCommonMapper;
import com.superl.skyDriver.mapper.UserLoginMapper;
import com.superl.skyDriver.pojo.User;
import com.superl.skyDriver.pojo.UserFile;
import com.superl.skyDriver.pojo.WPFile;
import com.superl.skyDriver.service.ShareService;
import com.superl.skyDriver.utils.CryptoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ShareServiceImpl implements ShareService {
    private static final String SALT = "superl";
    @Autowired
    private FileMapper fileMapper;
    @Autowired
    private ShareMapper shareMapper;
    @Autowired
    private UserCommonMapper userCommonMapper;

    @Override
    public String buildShareLink(Long userId, Long sourceId, String exPasswd, Long duration) {
        String endTime = new Date().getTime() + (duration * 24 * 3600 * 1000) + "";
        System.out.println("过期日期："+endTime);
        String info = userId + ";" + sourceId + ";" + exPasswd + ";" + endTime;
        String ciphertext = CryptoUtil.encode32(CryptoUtil.DEFAULT_SECRET_KEY + SALT, info);
        return ciphertext;
    }

    @Override
    public UserFile getSourceByFileId(Long userId, Long sourceId, String exPasswd, String passwd) {
        if (exPasswd.equals(passwd)){
            return fileMapper.loadByFileId(sourceId);
        }
        return null;
    }

    @Override
    public UserFile getSourceByFileIdNoPasswd(Long userId, Long sourceId) {
        return fileMapper.loadByFileId(sourceId);
    }

    @Override
    public List<UserFile> loadUserDir(User user, Long linkId) {

        return shareMapper.loadUserDir(user.getId(), linkId);
    }

    @Override
    public void saveShare(String account, Long fileId, String fileName, String parent, Long linkId) {
        UserFile userFile = new UserFile();
        WPFile wpFile = fileMapper.searchFile(fileId);
        userFile.setFile(wpFile);
        User user = userCommonMapper.findUserByAccount(account);
        userFile.setUser(user);
        userFile.setFilename(fileName);
        userFile.setParent(parent);
        userFile.setLinkId(linkId);
        fileMapper.insertFileUser(userFile);
    }

}
