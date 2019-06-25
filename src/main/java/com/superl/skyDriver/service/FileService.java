package com.superl.skyDriver.service;

import com.superl.skyDriver.pojo.UserFile;
import com.superl.skyDriver.pojo.WPFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    UserFile insertFile(MultipartFile file, String linkId, String parent, Long userId);
    List<UserFile> loadByUserId(Long userId);
    List<UserFile> loadByLinkId(Long linkId);
    List<UserFile> loadByUserType(Long userId, String type);
    List<UserFile> loadByUserIdLimit(Long userId, Long start, Long end);
    List<UserFile> loadByLinkIdLimit(Long linkId, Long start, Long end);
    List<UserFile> loadByUserTypeLimit(Long userId, String type, Long start, Long end);
    List<WPFile> loadAllWPFile();
    void deleteWPFileById(Long fileId);
    List<Long> loadAllUserFile();

    Long loadCountByUserId(Long userId);
    Long loadCountByLinkId(Long linkId);
    Long loadCountByUserType( Long userId, String type);

    UserFile buildDir(Long linkId, String parent, Long userId, String filename);
    boolean deleteFile(Long fileId);
    UserFile renameFile(String newFilename, Long id);
    UserFile downloadFile(Long fileId);


}
