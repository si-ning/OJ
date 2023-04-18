package com.example.oj.dao;

import com.example.oj.domain.Submit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
public class SubmitDaoTest {

    @Autowired
    private SubmitDao submitDao;

    @Test
    void testGetById(){
        System.out.println(submitDao.selectById(1));
    }

    @Test
    void testInsert(){
        Submit submit=new Submit();
        submit.setId(1);
        submit.setPid(2);
        submit.setResoult(1);
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //从前端或者自己模拟一个日期格式，转为String即可
        String dateStr = format.format(date);
        submit.setStime(dateStr);
        System.out.println(submitDao.insert(submit));
    }
}
