package com.example.oj.service;

import com.example.oj.domain.Submit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SubmitServiceImplTest {

    @Autowired
    private SubmitService submitService;

//    得用list方法接收，因为一个人可能有多个提交记录
    @Test
    void testGetById(){
        System.out.println(submitService);
    }


}
