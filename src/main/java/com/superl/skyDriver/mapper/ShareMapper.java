package com.superl.skyDriver.mapper;

import com.superl.skyDriver.pojo.User;
import com.superl.skyDriver.pojo.UserFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShareMapper {

    List<UserFile> loadUserDir(@Param("userId") Long userId, @Param("linkId") Long linkId);

}
