package com.example.oj.domain.temp;

import lombok.Data;

@Data
public class ReturnQuestion {
    private String pname;
    private String stdin;
    private String stdout;
    private String que;
    private double ptime;
    private int pid;
}
