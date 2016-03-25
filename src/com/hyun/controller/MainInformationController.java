package com.hyun.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hyun.common.ServerMonitorConstant;
import com.hyun.service.impl.MainInformationServiceImpl;
import com.hyun.vo.totalMasterOverviewInformation;

@Controller
@RequestMapping("/main")
public class MainInformationController {
 public static String login_Name="";
 public final String login_PassWord="";
 @Resource
 private MainInformationServiceImpl service;
 private String name="";
 @RequestMapping("/header.do")
	public ModelAndView showHead(HttpServletRequest req) {
		ModelAndView mv = new ModelAndView("head");
		mv.addObject("username", name);
		return mv;
	}
	@RequestMapping("/login.do")
	public String login(HttpServletRequest req) {
		String username = req.getParameter("username");
		String password = req.getParameter("Password");
		 String returnValue= service.getMasterLogin(username, password);
        if(ServerMonitorConstant.SUCCESSFUL.equals(returnValue)){
        	name=username;
        	return "redirect:/main/mainInformtion.do";
        }else{
            req.setAttribute("errorMessage", returnValue);
            return "index";
        }
	}
	@RequestMapping("/mainInformtion.do")
	public String mainInformation(HttpServletRequest req){
		totalMasterOverviewInformation temp=service.getMasterInfroamtion();
		 req.setAttribute("mainInformation", temp);
		return "main";
	}
}
