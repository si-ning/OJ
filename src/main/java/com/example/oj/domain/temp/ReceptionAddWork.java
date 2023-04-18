package com.example.oj.domain.temp;

import lombok.Data;

import java.util.List;

@Data
public class ReceptionAddWork {
    private String wname;
    private List<String> pnames;
    private int uid;
}
