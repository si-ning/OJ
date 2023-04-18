package com.example.oj.core;


import java.io.IOException;

public interface CompileAndRun {

    public Resoult compileandrun(String code, int id, int pid, double t) throws IOException, InterruptedException;
}
