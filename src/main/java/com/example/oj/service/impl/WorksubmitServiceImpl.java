package com.example.oj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.oj.dao.WorksubmitDao;
import com.example.oj.domain.Worksubmit;
import com.example.oj.service.WorksubmitService;
import org.springframework.stereotype.Service;

@Service
public class WorksubmitServiceImpl extends ServiceImpl<WorksubmitDao, Worksubmit> implements WorksubmitService {
}
