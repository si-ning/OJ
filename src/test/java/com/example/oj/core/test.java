package com.example.oj.core;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class test {

    @Test
    public void dir() throws IOException {

        Boolean flag = save.dir("Problems/1");
        System.out.println(flag);
    }

    @Test
    public void txt() throws IOException {
        Boolean flag =save.txt("Problems/1/question.txt");
        System.out.println(flag);
    }

    @Test
    public void read() throws IOException {
        String txt=txtfile.readtxt("Problems/1/question.txt");
        System.out.println(txt);
    }

    @Test
    public void put() throws IOException {
        String txt="123123\n"+"\n"+"uidhbiuabdiuahbdui\n"+"\n"+"end";
        System.out.println(txtfile.writetxt(txt,"Problems/1/question.txt"));
    }

    @Test
    public void run() throws IOException, InterruptedException {
        CompileAndRun compileAndRun=new CompileAndRunJava();
        String code="import java.util.Scanner;\n" +
                "\n" +
                "public class test1 {\n" +
                "    public static void main(String []args) throws InterruptedException {\n" +
                "        Scanner input=new Scanner(System.in);\n" +
                "        String line1 = input.nextLine();\n" +
                "        String line2 = input.nextLine();\n" +
                "        System.out.println(line1);\n" +
                "        Thread.sleep(4000);\n" +
                "        System.out.println(line2);\n" +
                "    }\n" +
                "}";
        Resoult re = compileAndRun.compileandrun(code, 1, 1, 2);
        System.out.println(re);
    }
}
