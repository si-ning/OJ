package com.example.oj.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.oj.domain.Submit;
import com.example.oj.domain.User;
import com.example.oj.domain.temp.ReceptionGetSubmit;
import com.example.oj.domain.temp.ReturnCommon;
import com.example.oj.domain.temp.ReturnGetSubmit;
import com.example.oj.service.SubmitService;
import com.example.oj.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;

@RestController
//@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private SubmitService submitService;

    @PostMapping("/login")
    public ReturnCommon Login(HttpServletResponse response , @RequestBody @NotNull User user){
        QueryWrapper<User> qw=new QueryWrapper<>();
        String username=user.getUsername();
        String password=user.getPassword();
        qw.eq("username",username);
        User one = userService.getOne(qw);
        System.out.println(one);
        System.out.println(user);
        ReturnCommon returnCommon=new ReturnCommon();
        if(one==null)
        {
            returnCommon.setFlag(true);
            returnCommon.setStatement("账号或者密码错误");
            return returnCommon;
        }
        if(!password.equals(one.getPassword())){
            returnCommon.setFlag(true);
            returnCommon.setStatement("账号或者密码错误");
            return returnCommon;
        }
        Cookie cookie = new Cookie("username", username);
        Cookie cookie1 = new Cookie("uid", one.getId().toString());
        response.addCookie(cookie);
        response.addCookie(cookie1);

        returnCommon.setFlag(false);
        returnCommon.setStatement("登录成功");
        return returnCommon;
    }

    @PostMapping("/user/add")
    public ReturnCommon Add(@RequestBody User user) throws InterruptedException {
        ReturnCommon returnCommon=new ReturnCommon();
        if("".equals(user.getUsername())||"".equals(user.getPassword())){
            returnCommon.setFlag(false);
            returnCommon.setStatement("请输入用户名或密码");
            return returnCommon;
        }
        //上锁，避免两个用户同时用同一个用户名注册成功
        //其实这个可以在数据库中做限制，但是我这里是用上锁来限制的
        synchronized (this) {
            QueryWrapper<User> qw = new QueryWrapper<>();
            String username = user.getUsername();
            qw.eq("username", username);
            User one = userService.getOne(qw);
//            TimeUnit.SECONDS.sleep(4);
            if (one != null) {
                returnCommon.setFlag(false);
                returnCommon.setStatement("用户名已被注册，请重新输入用户名");
                return returnCommon;
            }
            boolean save = userService.save(user);
            System.out.println("注册成功");
            if (save) {
                returnCommon.setFlag(true);
                returnCommon.setStatement("登录成功");
                return returnCommon;
            } else {
                returnCommon.setFlag(false);
                returnCommon.setStatement("注册失败，请联系管理员");
                return returnCommon;
            }
        }
    }

    @PostMapping("/user/getsubmit")
    public ReturnGetSubmit getsubmit(@RequestBody ReceptionGetSubmit receptionGetSubmit){
        ReturnGetSubmit returnGetSubmit = new ReturnGetSubmit();
        String strid=receptionGetSubmit.getId();
        if(null == strid||"".equals(strid)){
            returnGetSubmit.setMessage("请先登录");
            return returnGetSubmit;
        }
        QueryWrapper<Submit> qw=new QueryWrapper<>();
        qw.eq("id",receptionGetSubmit.getId());
        int all=submitService.count(qw);
        int pages=(all+receptionGetSubmit.getSize()-1)/receptionGetSubmit.getSize();
        if(receptionGetSubmit.getPage_num()>pages){
            receptionGetSubmit.setPage_num(pages);
        }
        if(receptionGetSubmit.getPage_num()<=0){
            receptionGetSubmit.setPage_num(1);
        }
        IPage page=new Page(receptionGetSubmit.getPage_num(),receptionGetSubmit.getSize());
        submitService.page(page,qw);
        returnGetSubmit.setFlag(1);
        returnGetSubmit.setSubmitList(page.getRecords());
        returnGetSubmit.setPage_num(receptionGetSubmit.getPage_num());
        return  returnGetSubmit;
    }
}
