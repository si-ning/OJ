package com.example.oj.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.oj.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserDaoTese {
    @Autowired
    private UserDao userDao;

    @Test
    void testGetById(){
        System.out.println(userDao.selectById(1));
    }

    @Test
    void testGetByName(){
        QueryWrapper<User> qw=new QueryWrapper<>();
        String name="admin";
        qw.eq("username",name);
        System.out.println(userDao.selectOne(qw));
    }

    @Test
    void testInsert(){
        User user=new User();
        user.setPassword("admin");
        user.setUsername("admin");
        System.out.println(userDao.insert(user));
    }
}
