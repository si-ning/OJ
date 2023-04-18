package com.example.oj.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.oj.domain.*;
import com.example.oj.domain.temp.*;
import com.example.oj.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/work")
public class WorkController {
    @Autowired
    private WorkService workService;
    @Autowired
    private WorksubmitService worksubmitService;
    @Autowired
    private WorkuserService workuserService;
    @Autowired
    private ProblemsService problemsService;
    @Autowired
    private UserService userService;
    @Autowired
    private SubmitController submitController;
    @Autowired
    private WorknumService worknumService;

    @PostMapping("/home")
    public ReturnWorkList home(@RequestBody ReceptionList receptionList){
        ReturnWorkList returnWorkList=new ReturnWorkList();
        int all=worknumService.count();
        int pages=(all+receptionList.getSize()-1)/receptionList.getSize();
        if(receptionList.getPage_num()>pages){
            receptionList.setPage_num(pages);
        }
        if(receptionList.getPage_num()<=0){
            receptionList.setPage_num(1);
        }
        IPage page=new Page(receptionList.getPage_num(), receptionList.getSize());
        worknumService.page(page);
        returnWorkList.setPage_num(receptionList.getPage_num());
        returnWorkList.setWorks(page.getRecords());
        returnWorkList.setFlag(1);
        return returnWorkList;
    }

    @PostMapping("/add")
    public ReturnCommon add(@RequestBody ReceptionAddWork receptionAddWork){
        ReturnCommon returnCommon=new ReturnCommon();
        String wname=receptionAddWork.getWname();
        List<String> wnames=receptionAddWork.getPnames();
        System.out.println("---------------------------------");
        System.out.println(receptionAddWork.getPnames());
        System.out.println("---------------------------------");
        int uid=receptionAddWork.getUid();
        if(uid!=1){
            returnCommon.setFlag(false);
            returnCommon.setStatement("只有管理员可以创建作业");
            return returnCommon;
        }
        QueryWrapper<Work> qw=new QueryWrapper<>();
        qw.eq("wname",wname);
        synchronized (this){
            int one=workService.count(qw);
            if(one!=0){
                returnCommon.setFlag(false);
                returnCommon.setStatement("作业名字重复，请另取作业名字");
                return returnCommon;
            }
            List<Problems> problemsList=new ArrayList<>();
            for(int i=0;i<wnames.size();i++){
                QueryWrapper<Problems> queryWrapper=new QueryWrapper<>();
                queryWrapper.eq("pname",wnames.get(i));
                Problems problem=problemsService.getOne(queryWrapper);
                if(problem==null)
                {
                    returnCommon.setFlag(false);
                    returnCommon.setStatement("题目："+wnames.get(i)+"不存在，请检查是否为题目名字错误");
                    return returnCommon;
                }
                problemsList.add(problem);
            }
            for(int i=0;i<problemsList.size();i++){
                Work work=new Work();
                Problems problem=problemsList.get(i);
                work.setPid(problem.getPid());
                work.setWname(wname);
                work.setPname(problem.getPname());
                workService.save(work);
            }
            Worknum worknum=new Worknum();
            worknum.setWname(wname);
            worknumService.save(worknum);
            returnCommon.setFlag(true);
            returnCommon.setStatement("创建成功");
            return returnCommon;
        }
    }

    @PostMapping("/finish")
    public ReturnWorkFinish finish(@RequestBody ReceptionWnameAndUid receptionWnameAndUid){
        ReturnWorkFinish returnWorkFinish=new ReturnWorkFinish();
        System.out.println("-------------------------------------------");
        System.out.println(receptionWnameAndUid);
        System.out.println("-------------------------------------------");
        //获取题目列表
        QueryWrapper<Work> workQueryWrapper=new QueryWrapper<>();
        workQueryWrapper.eq("wname",receptionWnameAndUid.getWname());
        List<Work> works=workService.list(workQueryWrapper);
        System.out.println("-------------------------------------------");
        System.out.println(works);
        System.out.println("-------------------------------------------");
        Map<String,Integer> map=new HashMap<>();
        List<String> p=new ArrayList<>();
        for(int i=0;i<works.size();i++){
            map.put(works.get(i).getPname(),i);
            p.add(works.get(i).getPname());
        }
        returnWorkFinish.setPname(p);
        QueryWrapper<Workuser> workuserQueryWrapper=new QueryWrapper<>();
        workuserQueryWrapper.eq("wname",receptionWnameAndUid.getWname());
        List<Workuser> workusers=workuserService.list(workuserQueryWrapper);
        System.out.println("-------------------------------------------");
        System.out.println(workusers);
        System.out.println("-------------------------------------------");
        List<String> s=new ArrayList<>();
        List<UserFinish> userFinishList=new ArrayList<>();
        for(int i=0;i<workusers.size();i++){
            QueryWrapper<Worksubmit> worksubmitQueryWrapper=new QueryWrapper<>();
            worksubmitQueryWrapper.eq("uid",workusers.get(i).getUid());
            worksubmitQueryWrapper.eq("result",0);
            worksubmitQueryWrapper.eq("wname",receptionWnameAndUid.getWname());
            //记录参加该作业的用户
            s.add(workusers.get(i).getUname());
            int judge=worksubmitService.count(worksubmitQueryWrapper);
            if(judge==0){

            }
            Boolean[] accept=new Boolean[works.size()];
            for(int j=0;j<accept.length;j++){
                accept[j]=false;
            }
            UserFinish userFinish=new UserFinish();
            userFinish.setUname(workusers.get(i).getUname());
            List<Boolean> b=new ArrayList<>();
            //没有成功过的人
            if(judge==0){
                for(int j=0;j<accept.length;j++){
                    b.add(accept[j]);
                }
                userFinish.setAccept(b);
                userFinishList.add(userFinish);
                continue;
            }

            List<Worksubmit> worksubmitList=worksubmitService.list(worksubmitQueryWrapper);
            for(int j=0;j<worksubmitList.size();j++){
                String pname=worksubmitList.get(j).getPname();
                int temp=map.get(pname);
                accept[temp]=true;
            }
            for(int j=0;j<accept.length;j++){
                b.add(accept[j]);
            }
            userFinish.setAccept(b);
            userFinishList.add(userFinish);
        }
        returnWorkFinish.setUserfinishList(userFinishList);
        returnWorkFinish.setUname(s);
        return returnWorkFinish;
    }

    @PostMapping("/attend")
    public ReturnCommon attend(@RequestBody ReceptionWnameAndUid receptionWnameAndUid){
        ReturnCommon returnCommon=new ReturnCommon();
        User user=userService.getById(receptionWnameAndUid.getUid());
        if(user==null){
            returnCommon.setFlag(false);
            returnCommon.setStatement("该用户不存在");
            return returnCommon;
        }
        QueryWrapper<Work> workQueryWrapper=new QueryWrapper<>();
        workQueryWrapper.eq("wname",receptionWnameAndUid.getWname());
        if(workService.count(workQueryWrapper)==0){
            returnCommon.setFlag(false);
            returnCommon.setStatement("该作业不存在");
            return returnCommon;
        }
        Workuser workuser=new Workuser();
        workuser.setUid(user.getId());
        workuser.setUname(user.getUsername());
        workuser.setWname(receptionWnameAndUid.getWname());
        QueryWrapper<Workuser> workuserQueryWrapper=new QueryWrapper<>();
        workuserQueryWrapper.eq("wname",receptionWnameAndUid.getWname());
        workuserQueryWrapper.eq("uid",user.getId());
        workuserQueryWrapper.eq("uname",user.getUsername());
        if(workuserService.count(workuserQueryWrapper)!=0){
            returnCommon.setFlag(false);
            returnCommon.setStatement("之前已经加入过该作业");
            return returnCommon;
        }
        workuserService.save(workuser);
        returnCommon.setFlag(true);
        returnCommon.setStatement("加入成功");
        return returnCommon;
    }

    @PostMapping("/detail")
    public ReturnProblemList detail(@RequestBody ReceptionWnameAndUid receptionWnameAndUid){
        ReturnProblemList returnProblemList=new ReturnProblemList();
        QueryWrapper<Work> workQueryWrapper=new QueryWrapper<>();
        workQueryWrapper.eq("wname",receptionWnameAndUid.getWname());
        System.out.println("----------------------------------------");
        System.out.println(receptionWnameAndUid);
        System.out.println(receptionWnameAndUid.getWname());

        List<Problems> problemsList=new ArrayList<>();
        List<Work> workList=workService.list(workQueryWrapper);
        System.out.println(workList);
        for(int i=0;i<workList.size();i++){
            Problems problems=problemsService.getById(workList.get(i).getPid());
            problemsList.add(problems);
        }
        returnProblemList.setProblems(problemsList);
        returnProblemList.setFlag(1);
        return returnProblemList;
    }

    @PostMapping("/submit")
    public ReturnSubmit submit(@RequestBody ReceptionWorkSubmit receptionWorkSubmit) throws IOException, InterruptedException {
        ReturnSubmit returnSubmit=new ReturnSubmit();
        User user=userService.getById(receptionWorkSubmit.getId());
        if(user==null){
            returnSubmit.setFlag(2);
            return returnSubmit;
        }
        QueryWrapper<Workuser> workuserQueryWrapper=new QueryWrapper<>();
        workuserQueryWrapper.eq("uid",receptionWorkSubmit.getId());
        workuserQueryWrapper.eq("wname",receptionWorkSubmit.getWname());
        Workuser workuser=workuserService.getOne(workuserQueryWrapper);
        if(workuser==null){
            returnSubmit.setFlag(2);
            return returnSubmit;
        }
        ReceptionSubmit receptionSubmit=new ReceptionSubmit();
        receptionSubmit.setCode(receptionWorkSubmit.getCode());
        receptionSubmit.setId(receptionWorkSubmit.getId());
        receptionSubmit.setType(receptionWorkSubmit.getType());
        receptionSubmit.setPid(receptionWorkSubmit.getPid());
        returnSubmit=submitController.submit(receptionSubmit);
        Worksubmit worksubmit=new Worksubmit();
        worksubmit.setPid(receptionWorkSubmit.getPid());
        worksubmit.setWname(receptionWorkSubmit.getWname());
        worksubmit.setUid(receptionWorkSubmit.getId());
        Problems problems=problemsService.getById(receptionWorkSubmit.getPid());
        worksubmit.setPname(problems.getPname());
        worksubmit.setResult(returnSubmit.getResoult());
        worksubmitService.save(worksubmit);
        return returnSubmit;
    }
}
