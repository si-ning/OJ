package com.example.oj.service;

import com.example.oj.domain.Problems;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProblemsServiceImplTest {

    @Autowired
    private ProblemsService problemsService;

    @Test
    void getById(){
        System.out.println(problemsService.getById(1));
    }

    @Test
    void save(){

        Problems problems=new Problems();
        problems.setPname("5");
        problems.setPtime(2);
        System.out.println(problemsService.save(problems));
    }
}
