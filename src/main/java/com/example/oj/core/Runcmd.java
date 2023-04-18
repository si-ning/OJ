package com.example.oj.core;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Slf4j
//执行cmd命令
public class Runcmd {

    //返回1表示成功执行cmd命令，返回0表示运行超时
    public static int run(String cmd, String OutFile, String ErrorFile,String Infile, double t) throws IOException {
        //1.获取Runtime对象,用来执行命令
        Runtime runtime = Runtime.getRuntime();
        Process process=runtime.exec(cmd);
        //读取标准输入
        PrintStream ps = new PrintStream(process.getOutputStream());
        InputStream inputStream=new FileInputStream(Infile);
        int length = inputStream.available();
        byte bytes[] = new byte[length];
        inputStream.read(bytes);
        inputStream.close();
        if(t==0) {
            t=3;
        }

        //限定运行时间
        CompletableFuture<InputStream> future = CompletableFuture.supplyAsync(() -> {
            // 这里是要执行的方法
            try {
                ps.write(bytes);
                ps.close();
                InputStream stdout= process.getInputStream();
                process.waitFor();
                return stdout;
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }

        });
        //执行cmd命令
        try {
            InputStream stdout=future.get((int)t*1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            log.error("cmd命令运行出错1\n");
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            log.error("cmd命令运行出错2\n");
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            //运行超时
            return 0;
        }

        //将结果输出到指定位置
        if(OutFile!=null){
            InputStream stdout=null;
            OutputStream stdoutto=null;
            try {
                //获取输出结果
                stdout = process.getInputStream();
                //创建指定位置的输出到目的地的输出流
                stdoutto = new FileOutputStream(OutFile);

                //用来判断是否读结果到结尾
                byte[] b = new byte[512];
                int temp = -1;
                while ((temp = stdout.read(b)) != -1) {
                    //这样避免上次读取满了，下次读取不满，导致数组缓存到下次
                    stdoutto.write(b, 0, temp);
                }
            }finally {//确保资源关闭
                if(stdout!=null){
                    stdout.close();
                }
                if(stdoutto!=null){stdoutto.close();}
            }
        }

        //将错误信息输出到指定位置
        if(ErrorFile!=null){
            InputStream stdout=null;
            OutputStream stdoutto=null;
            try {
                //获取输出结果
                stdout = process.getErrorStream();
                //创建指定位置的输出到目的地的输出流
                stdoutto = new FileOutputStream(ErrorFile);

                //用来判断是否读结果到结尾
                byte[] b = new byte[512];
                int temp = -1;
                while ((temp = stdout.read(b)) != -1) {
                    //这样避免上次读取满了，下次读取不满，导致数组缓存到下次
                    stdoutto.write(b, 0, temp);
                }
            }finally {//确保资源关闭
                if(stdout!=null){
                    stdout.close();
                }
                if(stdoutto!=null){stdoutto.close();}
            }
        }
        return 1;
    }

    public static void compile(String cmd,String ErrorFile) throws IOException, InterruptedException {
        Runtime runtime = Runtime.getRuntime();
        Process process=runtime.exec(cmd);

        InputStream stdout=null;
        OutputStream stdoutto=null;
        try {
            //获取输出结果
            stdout = process.getErrorStream();
            //创建指定位置的输出到目的地的输出流
            stdoutto = new FileOutputStream(ErrorFile);

            //用来判断是否读结果到结尾
            byte[] b = new byte[512];
            int temp = -1;
            while ((temp = stdout.read(b)) != -1) {
                //这样避免上次读取满了，下次读取不满，导致数组缓存到下次
                stdoutto.write(b, 0, temp);
            }
        }finally {//确保资源关闭
            if(stdout!=null){
                stdout.close();
            }
            if(stdoutto!=null){stdoutto.close();}
        }
        process.waitFor();
    }
}
