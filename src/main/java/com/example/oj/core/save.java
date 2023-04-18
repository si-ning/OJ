package com.example.oj.core;


import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import java.io.File;
import java.io.IOException;

public class save {



    public static Boolean dir(String path) throws IOException {
        File directory = new File("");// 参数为空
        String courseFile = directory.getCanonicalPath();
        File file = new File(courseFile+"/src/main/resources/static/"+path);
//        System.out.println(file.getPath());
        if(file.exists()){
            return false;
        }
        if(file.mkdirs()){
        return true;
        }
        return false;
    }

    public static Boolean txt(String path) throws IOException {
        File directory = new File("");// 参数为空
        String courseFile = directory.getCanonicalPath();
        File file = new File(courseFile+"/src/main/resources/static/"+path);

        if(file.exists()){
            return false;
        }
        if(file.createNewFile()) {
            return true;
        }
        return false;
    }
    public static void std(String path, String name, MultipartFile file) throws IOException {
        File directory = new File("");// 参数为空
        String courseFile = directory.getCanonicalPath();
        File targetFile = new File(courseFile+"/src/main/resources/static/"+path,name+".txt");
//        System.out.println(file.getPath());
        FileUtils.writeByteArrayToFile(targetFile, file.getBytes());
    }

/*  删除文件，但是目前不需要
    public static Boolean remove(String path){
        File file = new File(path);
        return file.delete();
    }
    */

}
