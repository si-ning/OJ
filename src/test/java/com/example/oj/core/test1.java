package com.example.oj.core;

import java.util.Scanner;

public class test1 {
    public static void main(String []args) throws InterruptedException {
        Scanner input=new Scanner(System.in);
        String line1 = input.nextLine();
        String line2 = input.nextLine();
        System.out.println(line1);
        Thread.sleep(4000);
        System.out.println(line2);
    }
}
