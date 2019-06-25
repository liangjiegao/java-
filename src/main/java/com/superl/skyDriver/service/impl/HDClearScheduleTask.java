package com.superl.skyDriver.service.impl;

import com.superl.skyDriver.pojo.WPFile;
import com.superl.skyDriver.service.FileService;
import com.superl.skyDriver.utils.HDClearer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class HDClearScheduleTask {

    @Autowired
    private FileService fileService;

    /**
     * 定时执行的方法
     */
    public void clearHD(){
        // 获取数据库中file表的全部记录
        List<WPFile> wpFiles = fileService.loadAllWPFile();
        // 获取user_file表中的全部关联file表的file_id
        List<Long> userFiles = fileService.loadAllUserFile();
        // 用来记录file表中存在，但是user_file表中没有关联记录的路径，指向硬盘的一个文件，要删除
        List<String> paths = new ArrayList<>();
        boolean content;
        for (WPFile wpfile : wpFiles) {
            content = false;
            for (Long fileId : userFiles) {
                if (fileId.equals(wpfile.getId())){
                    content = true;
                    break;
                }
            }
            // 如果UserFile表中没有关联File的记录，记录filepath
            if (!content){
                // 将文件的路劲存进list中
                if (!StringUtils.isEmpty(wpfile.getFilePath())){
                    // 删除file中user_file表中没有关联的记录
                    fileService.deleteWPFileById(wpfile.getId());
                    paths.add(wpfile.getFilePath());

                }
            }
        }
        // 清除硬盘在路径list不包含的文件
        HDClearer.clearHD(paths);
    }

}
