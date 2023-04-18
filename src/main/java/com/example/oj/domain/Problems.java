package com.example.oj.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Problems {
    private String pname;
    @TableId(value = "pid",type = IdType.AUTO)
    private Integer pid;
    private double ptime;
}
