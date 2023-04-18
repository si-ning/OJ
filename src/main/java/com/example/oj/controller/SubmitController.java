package com.example.oj.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.oj.core.CompileAndRun;
import com.example.oj.core.CompileAndRunJava;
import com.example.oj.core.Resoult;
import com.example.oj.domain.Problems;
import com.example.oj.domain.Submit;
import com.example.oj.domain.User;
import com.example.oj.domain.temp.ReceptionSubmit;
import com.example.oj.domain.temp.ReturnSubmit;
import com.example.oj.service.ProblemsService;
import com.example.oj.service.SubmitService;
import com.example.oj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/submit")
public class SubmitController {

    @Autowired
    private SubmitService submitService;
    @Autowired
    private ProblemsService problemsService;
    @Autowired
    private UserService userService;
    @PostMapping("/solve")
    public ReturnSubmit submit(@RequestBody ReceptionSubmit receptionSubmit) throws IOException, InterruptedException {
        User user=userService.getById(receptionSubmit.getId());
        ReturnSubmit returnSubmit=new ReturnSubmit();
        if(user==null){
            returnSubmit.setFlag(2);
            return returnSubmit;
        }
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取题目的运行时间
        Problems problem = problemsService.getById(receptionSubmit.getPid());
        CompileAndRun compileAndRun = null;

        //如果用户选择的java语言
        if (receptionSubmit.getType() == 2) {
            compileAndRun = new CompileAndRunJava();
        }
        if(problem==null){
            returnSubmit.setFlag(1);
            return returnSubmit;
        }
        //得到运行结果
        Resoult re = compileAndRun.compileandrun(receptionSubmit.getCode(), receptionSubmit.getId(), receptionSubmit.getPid(), problem.getPtime());
        Submit submit=new Submit();
        submit.setPid(receptionSubmit.getPid());
        submit.setResoult(re.getError());
        submit.setId(receptionSubmit.getId());
        //从前端或者自己模拟一个日期格式，转为String即可
        String dateStr = format.format(date);
        submit.setStime(dateStr);
        submit.setPname(problem.getPname());
        //保存提交记录
        submitService.save(submit);
        returnSubmit.setFlag(0);
        returnSubmit.setPname(problem.getPname());
        returnSubmit.setTime(dateStr);
        returnSubmit.setResoult(re.getError());
        returnSubmit.setPid(problem.getPid());
        return returnSubmit;
    }

}
