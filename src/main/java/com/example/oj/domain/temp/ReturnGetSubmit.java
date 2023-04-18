package com.example.oj.domain.temp;

import com.example.oj.domain.Submit;
import lombok.Data;

import java.util.List;

@Data
public class ReturnGetSubmit {
    private int flag;
    private String message;
    private List<Submit> submitList;
    private int page_num;
}
