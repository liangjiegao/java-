package com.superl.skyDriver;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CreateSQL {

    public static void main(String[] args) throws IOException {
        insertFile();
    }
    public static void insertUserFile() throws IOException {
        File file = new File("sql_insert_user_file.sql");
        if (!file.exists()){
            file.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(file, true);
        fileWriter.write("INSERT INTO user_file VALUES ");
        for (int i = 69; i < 1000000; i++) {
            if (i % 10000 == 0){
                fileWriter.write("(NULL, 10015, "+i+", '文件名', 'user', 10015);\n");
                if (i != 999999)
                    fileWriter.write("INSERT INTO user_file VALUES ");
            }else{
                fileWriter.write("(NULL, 10015, "+i+", '文件名', 'user', 10015),");
            }
        }
        fileWriter.flush();
        fileWriter.close();
    }
    public static void insertFile() throws IOException {
        File file = new File("sql_insert_file.sql");
        if (!file.exists()){
            file.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(file, true);
        fileWriter.write("INSERT INTO file VALUES ");
        for (int i = 69; i < 1000000; i++) {
            if (i % 10000 == 0){
                fileWriter.write("("+i+", '',  'dir', 0, '2019-06-25 21:08:56');\n");
                if (i != 999999)
                    fileWriter.write("INSERT INTO file VALUES ");
            }else{
                fileWriter.write("("+i+", '',  'dir', 0, '2019-06-25 21:08:56'),");
            }
        }
        fileWriter.flush();
        fileWriter.close();
    }
}
