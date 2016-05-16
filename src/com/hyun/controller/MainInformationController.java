package com.hyun.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.runner.Request;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hyun.common.JasonCover;
import com.hyun.common.ServerMonitorConstant;
import com.hyun.service.impl.ConfigurationServiceImpl;
import com.hyun.service.impl.MainInformationServiceImpl;
import com.hyun.vo.totalCPUpercent;
import com.hyun.vo.totalMasterOverviewInformation;
import com.hyun.vo.diagram.totalDiskSpaceDiagram;

@Controller
@RequestMapping("/main")
public class MainInformationController {
 public static String login_Name="";
 public final String login_PassWord="";
 @Resource
 private MainInformationServiceImpl service;
 @Resource
 private ConfigurationServiceImpl service1; 
 private String name="";
 @RequestMapping("/header.do")
	public ModelAndView showHead(HttpServletRequest req,HttpServletResponse response) {
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
        	HttpSession session=req.getSession();
        	  
            session.setAttribute("UserName", "system");
        	name=username;
        	return "main";
        }else{
            req.setAttribute("errorMessage", returnValue);
            return "index";
        }
	}
	@RequestMapping("/mainInformtion.do")
	public ModelAndView mainInformation(HttpServletRequest req,HttpServletResponse response){
		ModelAndView mv =new ModelAndView("systemInformation");
		
		totalMasterOverviewInformation info=service.getMasterInfroamtion();
		mv.addObject("info",info);
		totalDiskSpaceDiagram temp1=service.perpareTotalDiskSpace(info);
		totalCPUpercent temp2=service.perpareTotalCPU(info);
		new JasonCover();
		mv.addObject("pieValue",JasonCover.toJason(temp1));
		new JasonCover();
		mv.addObject("lineValue",JasonCover.toJason(temp2));
		return mv;
	}

	
	@RequestMapping(value="/getCPUSum.do")
	@ResponseBody
	public Map<String,totalCPUpercent> getTotalDiskSpace(HttpServletRequest req,HttpServletResponse response){
		 
		totalCPUpercent temp=service.perpareTotalCPU(service.getMasterInfroamtion());
		Map<String,totalCPUpercent> map=new HashMap<String, totalCPUpercent>();
		map.put("info",temp);
		return map;
	}
	@RequestMapping("/diskInformation.do")
	public ModelAndView diskInformation(HttpServletRequest req,HttpServletResponse response){
		 ModelAndView mv=new ModelAndView("hardDiskInformation");
		 totalMasterOverviewInformation info=service.getMasterInfroamtion();
		   String[][] temp1=service1.getDFSConfigure();
		   LinkedList<String[]> temp=service1.getDataNode();
		 LinkedList<totalDiskSpaceDiagram> retrnValue=service.perpareTableDiskSpace(info);
		 mv.addObject("info",JasonCover.toJason(retrnValue));
		 mv.addObject("datanode",JasonCover.toJason(temp));
		 mv.addObject("result",temp1);
		return mv;
	}
		
}
