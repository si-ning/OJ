package com.example.oj.domain.temp;

import com.example.oj.domain.Problems;
import lombok.Data;

import java.util.List;

@Data
public class ReturnProblemList {
    private int flag;
    private List<Problems> problems;
    private int page_num;
}
