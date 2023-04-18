package com.example.oj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/page")
public class PageController {
    @RequestMapping("/problems/{pid}")
    public ModelAndView code(@PathVariable(value = "pid") String pid) {

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("/problem/question");
        modelAndView.addObject("pid",pid);
        return modelAndView;
    }

    @RequestMapping("/work/detail/{wname}")
    public ModelAndView detail(@PathVariable(value = "wname") String wname){
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("/Work/detail");
        modelAndView.addObject("wname",wname);
        return modelAndView;
    }
    @RequestMapping("/work/problems/{pid}/{wname}")
    public ModelAndView workproblem(@PathVariable(value = "pid") String pid,@PathVariable(value = "wname") String wname) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/Work/problem");
        modelAndView.addObject("pid",pid);
        modelAndView.addObject("wname",wname);
        return modelAndView;
    }

    @RequestMapping("/work/finish/{wname}")
    public ModelAndView finish(@PathVariable(value = "wname") String wname){
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("/Work/finish");
        modelAndView.addObject("wname",wname);
        return modelAndView;
    }
}
