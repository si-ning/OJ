package com.example.oj.domain.temp;

import lombok.Data;

import java.util.List;

@Data
public class UserFinish {
    private String uname;
    private List<Boolean> accept;
}
