package com.superl.skyDriver.service;

import com.superl.skyDriver.pojo.UserFile;

import java.util.List;

public interface ZipService {

    UserFile zipFile(Long[] fileIdList, Long linkId, String zipName, Long userId);
    List<UserFile> unzipFile(Long fileId, Long linkId, String parent, Long userId);
}
