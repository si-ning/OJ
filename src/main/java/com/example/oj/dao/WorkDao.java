package com.example.oj.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.oj.domain.Work;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WorkDao extends BaseMapper<Work> {
}
