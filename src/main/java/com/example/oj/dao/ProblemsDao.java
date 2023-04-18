package com.example.oj.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.oj.domain.Problems;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ProblemsDao extends BaseMapper<Problems> {

    @Select("select * from problems where pid = #{pid}")
    Problems getById(Integer pid);
}
