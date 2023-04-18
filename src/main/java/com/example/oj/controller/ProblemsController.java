package com.example.oj.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.oj.core.txtfile;
import com.example.oj.domain.Problems;
import com.example.oj.domain.temp.ReceptionList;
import com.example.oj.domain.temp.ReturnQuestion;
import com.example.oj.domain.temp.ReturnCommon;
import com.example.oj.domain.temp.ReturnProblemList;
import com.example.oj.service.ProblemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/problems")
public class ProblemsController {

    @Autowired
    private ProblemsService problemsService;
    @PostMapping("/page")
    public ReturnProblemList getpage(@RequestBody ReceptionList receptionProblemList){
        ReturnProblemList problemList=new ReturnProblemList();
        int all=problemsService.count();
        int pages=(all+receptionProblemList.getSize()-1)/receptionProblemList.getSize();
        if(receptionProblemList.getPage_num()>pages){
            receptionProblemList.setPage_num(pages);
        }
        if(receptionProblemList.getPage_num()<=0){
            receptionProblemList.setPage_num(1);
        }
        IPage page=new Page(receptionProblemList.getPage_num(), receptionProblemList.getSize());
        problemsService.page(page);
        problemList.setPage_num(receptionProblemList.getPage_num());
        problemList.setProblems(page.getRecords());
        problemList.setFlag(1);
        return problemList;
    }
    @PostMapping("/addproblem")
    public ReturnCommon addproble(@RequestParam(value = "stdin") MultipartFile[] stdin,
                                  @RequestParam(value = "stdout") MultipartFile[] stdout,
                                  @RequestParam(value = "stdin0") MultipartFile stdin0,
                                  @RequestParam(value = "stdout0") MultipartFile stdout0,
                                  @RequestParam(value = "pname") String pname,
                                  @RequestParam(value = "ptime") double ptime,
                                  @RequestParam(value = "question") String question,
                                  @RequestParam(value = "id") String id

    ) throws IOException {
        ReturnCommon returnCommon=new ReturnCommon();
//        System.out.println(pname);
//        System.out.println(ptime);
//        System.out.println(question);
//        System.out.println(stdin);
//        return returnCommon;
        System.out.println(id);
        System.out.println("-----------------------");
        if(!id.equals("1")){
            returnCommon.setFlag(false);
            returnCommon.setStatement("只有管理员才能上传题目");
            return  returnCommon;
        }
        if("".equals(pname)){
            returnCommon.setFlag(false);
            returnCommon.setStatement("题目名不能为空");
            return returnCommon;
        }
        Problems problems=new Problems();
        problems.setPtime(ptime);
        problems.setPname(pname);
        synchronized (this) {
            QueryWrapper<Problems> qw = new QueryWrapper<>();
            qw.eq("pname", pname);
            Problems one = problemsService.getOne(qw);
            if (one != null) {
                returnCommon.setFlag(false);
                returnCommon.setStatement("题目名字重复");
                return returnCommon;
            }
            boolean save = problemsService.save(problems);
            one=problemsService.getOne(qw);
            com.example.oj.core.save.dir("Problems/"+one.getPid());
            com.example.oj.core.save.dir("Problems/"+one.getPid()+"/stdin");
            com.example.oj.core.save.dir("Problems/"+one.getPid()+"/stdout");
            com.example.oj.core.save.txt("Problems/"+one.getPid()+"/question.txt");
            txtfile.writetxt(question,"Problems/"+one.getPid()+"/question.txt");
            com.example.oj.core.save.std("Problems/"+one.getPid()+"/stdin/","0",stdin0);
            com.example.oj.core.save.std("Problems/"+one.getPid()+"/stdout/","0",stdout0);
            for(int i=0;i<stdin.length;i++){
                Integer temp=i+1;
                com.example.oj.core.save.std("Problems/"+one.getPid()+"/stdin/",""+temp,stdin[i]);
            }
            for(int i=0;i<stdout.length;i++){
                Integer temp=i+1;
                com.example.oj.core.save.std("Problems/"+one.getPid()+"/stdout/",""+temp,stdout[i]);
            }
            System.out.println("题目提交成功");
            if (save) {
                returnCommon.setFlag(true);
                returnCommon.setStatement("提交成功");
                return returnCommon;
            } else {
                returnCommon.setFlag(false);
                returnCommon.setStatement("提交失败，请联系管理员");
                return returnCommon;
            }
        }

    }
    @PostMapping("/getquestion")
    public ReturnQuestion getquestion(@RequestBody String pid) throws IOException {
        ReturnQuestion returnQuestion=new ReturnQuestion();
        String str=pid.split(":")[1].split("}")[0];
        int temp=Integer.valueOf(str);
        Problems problem=problemsService.getById(temp);
        String stdin=txtfile.readtxt("Problems/"+str+"/stdin/0.txt");
        String stdout=txtfile.readtxt("Problems/"+str+"/stdout/0.txt");
        String question=txtfile.readtxt("Problems/"+str+"/question.txt");
        returnQuestion.setPname(problem.getPname());
        returnQuestion.setPtime(problem.getPtime());
        returnQuestion.setPid(temp);
        returnQuestion.setStdin(stdin);
        returnQuestion.setStdout(stdout);
        returnQuestion.setQue(question);
        return returnQuestion;
    }

}
