package com.example.oj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.oj.dao.WorknumDao;
import com.example.oj.domain.Worknum;
import com.example.oj.service.WorknumService;
import org.springframework.stereotype.Service;

@Service
public class WorknumServiceImpl extends ServiceImpl<WorknumDao, Worknum> implements WorknumService {
}
