package com.superl.skyDriver.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 负责清除硬盘中没有被数据库连接到的文件
 */
public class HDClearer {

    public static boolean clearHD(List<String> paths){
        // 用于存放硬盘中文件的路径
        List<File> fileList = new ArrayList<>();
        // 存放文件的根路径
        String root = FileUtil.getPATH();
        findAllFile(fileList, root);    // 递归查询出所有文件
        for (File file : fileList) { // 遍历所有文件
            for (String path: paths) {
                // 如果文件的路径与记录的路径相同，则删除该文件
                if (file.getPath().replaceAll("\\\\","/").equals(path)){
                    file.delete();
                    break;
                }
            }
        }
        return true;
    }
    private static void findAllFile(List<File> fileList, String path){
        // 根据路径找出目录
        File root = new File(path);
        // 获取目录下的所有文件
        File[] files = root.listFiles();
        // 遍历文件数组
        for (int i = 0; i < files.length; i++) {
            // 如果遍历到的文件时文件夹，则递归该文件夹的文件
            if (files[i].isDirectory()){
                findAllFile(fileList, files[i].getPath());
            }else {
                // 如果是文件，则记录起来
                fileList.add(files[i]);
            }
        }
    }

}
