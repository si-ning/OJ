package com.example.oj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import com.example.oj.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceImplTest {
    @Autowired
    private UserService userService;

    @Test
    void getByID(){
        System.out.println(userService.getById(1));
    }

    @Test
    void getByName(){
        QueryWrapper<User> qw=new QueryWrapper<User>();
        String name="root";
        qw.eq("username",name);
        User user=userService.getOne(qw);
        System.out.println(user);
    }

    @Test
    void save(){
        User user=new User();
        user.setPassword("123");
        user.setUsername("123");
        boolean save = userService.save(user);
        System.out.println(save);
    }
}
