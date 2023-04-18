package com.example.oj.domain.temp;

import lombok.Data;

import java.util.List;

@Data
public class ReturnWorkFinish {
    private List<String> uname;
    private List<String> pname;
    private List<UserFinish> userfinishList;
}
