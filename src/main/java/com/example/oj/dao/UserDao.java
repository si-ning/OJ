package com.example.oj.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.oj.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao extends BaseMapper<User> {
}
