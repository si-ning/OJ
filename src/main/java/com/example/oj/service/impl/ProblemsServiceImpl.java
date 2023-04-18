package com.example.oj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.oj.dao.ProblemsDao;
import com.example.oj.domain.Problems;
import com.example.oj.service.ProblemsService;
import org.springframework.stereotype.Service;

@Service
public class ProblemsServiceImpl extends ServiceImpl<ProblemsDao, Problems> implements ProblemsService {

//    public void test(){
//        query().eq().eq()
//    }
}
