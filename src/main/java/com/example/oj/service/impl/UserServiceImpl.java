package com.example.oj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.oj.dao.UserDao;
import com.example.oj.domain.User;
import com.example.oj.service.UserService;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {
}
