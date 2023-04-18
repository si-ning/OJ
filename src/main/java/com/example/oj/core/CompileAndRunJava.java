package com.example.oj.core;

import com.baomidou.mybatisplus.extension.api.R;
import com.example.oj.core.Runcmd;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static com.example.oj.core.Runcmd.*;

public class CompileAndRunJava implements CompileAndRun{

    @Override
    public Resoult compileandrun(String code, int id, int pid, double t) throws IOException, InterruptedException {
        //地址
        File directory = new File("");// 参数为空
        String courseFile = directory.getCanonicalPath();
        courseFile = courseFile+"/src/main/resources/static/";
        //编译错误日志的路径
        String errortxt="User/"+id+"/"+pid+"/"+"error_"+pid+".txt";
        //需要运行的java文件的路径
        String javatxt="User/"+id+"/"+pid+"/"+"test"+pid+".java";
        //java文件运行的输出文件的路径
        String outtxt="User/"+id+"/"+pid+"/"+"out_"+pid+".txt";

        //在User用户下创建id用户的文件夹，在id用户下创建pid题目的文件夹用来记录关于这个题目的提交信息
        save.dir("User/"+id+"/"+pid+"/");
        //创建错误日志
        save.txt(errortxt);
        //创建要运行的java文件
        save.txt(javatxt);
        //将代码保存到java文件中
        txtfile.writetxt(code,javatxt);
        //创建输出内容保存的txt文件
        save.txt(outtxt);
        //构建编译命令
        String cmd="javac "+courseFile+javatxt+" -d "+courseFile+"User/"+id+"/"+pid+"/";
        //编译java文件
        Runcmd.compile(cmd,courseFile+errortxt);
        //读取编译错误
        //如果编译错误不为空
        String compoile_err=txtfile.readtxt(errortxt);
        if(!"".equals(compoile_err)){
            Resoult resoult=new Resoult();
            resoult.setError(1);
            resoult.setReason(compoile_err);
            return resoult;
        }

        //运行java文件
        File stdin=new File(courseFile+"Problems/"+pid+"/stdin/");
        File stdout=new File(courseFile+"Problems/"+pid+"/stdout/");
        //读取题目的标准输入和标准输出
        File[] stdins=stdin.listFiles();
        File[] stdouts=stdout.listFiles();
        //跳过第一个输入输出样例
        for(int i=1;i<stdins.length;i++){
            //构建执行命令
//            cmd="java -classpath "+courseFile+javatxt+" "+courseFile+"User/"+id+"/"+pid+"/"+"test"+pid;
            cmd="java -classpath "+courseFile+"User/"+id+"/"+pid+"/ "+"test"+pid;
            //执行命令
            int re=Runcmd.run(cmd,courseFile+outtxt,courseFile+errortxt,stdins[i].getPath(),t);
            //超时的情况
            if(re==0){
                Resoult resoult=new Resoult();
                resoult.setError(4);
                resoult.setReason("运行超时");
                return resoult;
            }
            //运行出错的情况
            String runerr=txtfile.readtxt(errortxt);
            if(!"".equals(runerr)){
                Resoult resoult=new Resoult();
                resoult.setError(3);
                resoult.setReason(runerr);
                return resoult;
            }
            //对比结果
            Boolean flag=txtfile.compare(outtxt,stdouts[i].getPath());
            if(!flag){
                Resoult resoult=new Resoult();
                resoult.setError(2);
                resoult.setReason("结果出错");
                return resoult;
            }
        }
        Resoult resoult=new Resoult();
        resoult.setError(0);
        resoult.setReason("运行成功");
        return resoult;
    }
}
