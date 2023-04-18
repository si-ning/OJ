package com.example.oj.core;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class txtfile {
    public static String readtxt(String path) throws IOException {
        File directory = new File("");// 参数为空
        String courseFile = directory.getCanonicalPath();
        File file = new File(courseFile+"/src/main/resources/static/"+path);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        FileInputStream in=null;
        in = new FileInputStream(file);
        in.read(filecontent);
        return new String(filecontent, "UTF-8");
    }
    public static Boolean writetxt(String content,String path) throws IOException {
        File directory = new File("");// 参数为空
        String courseFile = directory.getCanonicalPath();
        String filePath=courseFile+"/src/main/resources/static/"+path;
        FileWriter fwriter = null;
        try {
            // true表示不覆盖原来的内容，而是加到文件的后面。若要覆盖原来的内容，直接省略这个参数就好
            fwriter = new FileWriter(filePath);
            fwriter.write(content);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                fwriter.flush();
                fwriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return true;
    }

    public static Boolean compare(String path1,String path2) throws IOException {
        File directory = new File("");// 参数为空
        String courseFile = directory.getCanonicalPath();
        String file1=courseFile+"/src/main/resources/static/"+path1;
        String file2=path2;
        BufferedInputStream bis1 = null;
        BufferedInputStream bis2 = null;
        FileInputStream fis1 = null;
        FileInputStream fis2 = null;

        try {
            // 获取文件输入流
            fis1 = new FileInputStream(file1);
            fis2 = new FileInputStream(file2);
            // 将文件输入流包装成缓冲流
            bis1 = new BufferedInputStream(fis1);
            bis2 = new BufferedInputStream(fis2);

            // 获取文件字节总数
            int len1 = bis1.available();
            int len2 = bis2.available();

            // 判断两个文件的字节长度是否一样,长度相同则比较具体内容
            if (len1 == len2) {
                // 建立字节缓冲区
                byte[] data1 = new byte[len1];
                byte[] data2 = new byte[len2];

                // 将文件写入缓冲区
                bis1.read(data1);
                bis2.read(data2);
                // 依次比较文件中的每个字节
                for (int i = 0; i < len1; i++) {
                    if (data1[i] != data2[i]) {
                        //System.out.println("文件内容不一致");
                        return false;
                    }
                }
                //System.out.println("文件内容一致");
                return true;
            } else {
                //System.out.println("文件长度不一致，内容不一致");
                return false;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis1 != null) {
                try {
                    bis1.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bis2 != null) {
                try {
                    bis2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis1 != null) {
                try {
                    fis1.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis2 != null) {
                try {
                    fis2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}
