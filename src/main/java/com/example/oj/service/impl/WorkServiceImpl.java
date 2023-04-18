package com.example.oj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.oj.dao.WorkDao;
import com.example.oj.domain.Work;
import com.example.oj.service.WorkService;
import org.springframework.stereotype.Service;

@Service
public class WorkServiceImpl extends ServiceImpl<WorkDao, Work> implements WorkService{
}
