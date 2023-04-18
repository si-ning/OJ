package com.example.oj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.oj.dao.SubmitDao;
import com.example.oj.domain.Submit;
import com.example.oj.service.SubmitService;
import org.springframework.stereotype.Service;

@Service
public class SubmitServiceImpl extends ServiceImpl<SubmitDao, Submit> implements SubmitService {
}
