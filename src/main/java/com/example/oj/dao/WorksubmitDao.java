package com.example.oj.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.oj.domain.Worksubmit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface WorksubmitDao extends BaseMapper<Worksubmit> {

    @Select("select * from worksubmit where wname = #{wname} and uid = #{uid} and result = 0")
    List<Worksubmit> getSuccess(String wname, Integer uid);
}
