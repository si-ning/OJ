package com.example.oj.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.oj.domain.Problems;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SpringBootTest
public class ProblemsDaoTest {

    @Autowired
    private ProblemsDao problemsDao;

    @Test
    void testGetById(){
        System.out.println(problemsDao.getById(1));
    }

    @Test
    void testInsert(){
        Problems problems=new Problems();
        problems.setPname("4");
        problems.setPtime(2);
        System.out.println(problemsDao.insert(problems));
    }

    @Test
    void testPage(){
        //第一页，一页有3个数据，无任何限制
        IPage page=new Page(1,3);
        problemsDao.selectPage(page,null);
        //获取得到的数据的，类型是一个list
        List<Problems> records = page.getRecords();
        System.out.println(records);

    }
}
