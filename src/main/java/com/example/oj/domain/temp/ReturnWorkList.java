package com.example.oj.domain.temp;

import com.example.oj.domain.Work;
import lombok.Data;

import java.util.List;

@Data
public class ReturnWorkList {
    private int flag;
    private List<Work> works;
    private int page_num;
}
