package com.superl.skyDriver.mapper;

import com.superl.skyDriver.pojo.UserFile;
import com.superl.skyDriver.pojo.WPFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FileMapper {

    void insertFile(UserFile file);

    void insertFileUser(UserFile userFile);

    void deleteFile(Long fileId);

    void deleteUserFile(Long user_fileId);

    void renameFile(@Param("newFilename") String newFilename, @Param("id") Long id);

    UserFile loadByFileId(Long id);

    List<UserFile> loadByUserId(Long userId);

    List<UserFile> loadByLinkId(Long linkId);

    List<UserFile> loadByUserType(@Param("userId") Long userId, @Param("type") String type);

    Long loadCountByUserId(Long userId);

    Long loadCountByLinkId(Long linkId);

    Long loadCountByUserType(@Param("userId") Long userId,@Param("type") String type);

    List<UserFile> loadByUserIdLimit(@Param("userId") Long userId, @Param("start") Long start, @Param("end") Long end);

    List<UserFile> loadByLinkIdLimit(@Param("linkId") Long linkId, @Param("start") Long start, @Param("end") Long end);

    List<UserFile> loadByUserTypeLimit(@Param("userId") Long userId, @Param("type") String type, @Param("start") Long start, @Param("end") Long end);

    List<WPFile> loadAllWPFile();
    void deleteWPFileById(Long id);
    List<Long> loadAllUserFile();

    UserFile downloadFile(@Param("fileId") Long fileId);

    List<UserFile> searchByFilename(String filename);

    WPFile searchFile(Long fileId);
}
