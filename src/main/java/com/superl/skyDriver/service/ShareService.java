package com.superl.skyDriver.service;

import com.superl.skyDriver.pojo.User;
import com.superl.skyDriver.pojo.UserFile;

import java.util.List;

public interface ShareService {

    String buildShareLink(Long userId, Long sourceId, String exPasswd, Long endTime);

    UserFile getSourceByFileId(Long userId, Long sourceId, String exPasswd, String passwd);

    UserFile getSourceByFileIdNoPasswd(Long userId, Long sourceId);

    List<UserFile> loadUserDir(User user, Long linkId);

    void saveShare(String account, Long fileId, String fileName, String parent, Long linkId);


}
