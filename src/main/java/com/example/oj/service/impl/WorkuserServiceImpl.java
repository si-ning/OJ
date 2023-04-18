package com.example.oj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.oj.dao.WorkuserDao;
import com.example.oj.domain.Workuser;
import com.example.oj.service.WorkuserService;
import org.springframework.stereotype.Service;

@Service
public class WorkuserServiceImpl extends ServiceImpl<WorkuserDao, Workuser> implements WorkuserService {
}
