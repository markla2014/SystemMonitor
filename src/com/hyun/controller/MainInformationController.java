package com.hyun.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.JsonNull;
import com.hyun.common.ServerMonitorConstant;
import com.hyun.service.impl.MainInformationServiceImpl;
import com.hyun.vo.totalCPUpercent;
import com.hyun.vo.totalMasterOverviewInformation;
import com.hyun.vo.diagram.totalDiskSpaceDiagram;

@Controller
@RequestMapping("/main")
public class MainInformationController {
 public static String login_Name="";
 public final String login_PassWord="";
 public Gson gson=new Gson();
 @Resource
 private MainInformationServiceImpl service;
 private String name="";
 @RequestMapping("/header.do")
	public ModelAndView showHead(HttpServletRequest req,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("head");
		mv.addObject("username", name);
		return mv;
	}
	@RequestMapping("/login.do")
	public String login(HttpServletRequest req,HttpServletResponse response) {
		String username = req.getParameter("username");
		String password = req.getParameter("Password");
		 String returnValue= service.getMasterLogin(username, password);
        if(ServerMonitorConstant.SUCCESSFUL.equals(returnValue)){
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
		totalMasterOverviewInformation temp=service.getMasterInfroamtion();
		mv.addObject("info",temp);
		totalDiskSpaceDiagram temp1=service.perpareTotalDiskSpace(info);
		totalCPUpercent temp2=service.perpareTotalCPU(info);
		mv.addObject("pieValue",toJason(temp1));
		mv.addObject("lineValue", toJason(temp2));
		return mv;
	}
	public String toJason(Object o){
		if(o==null){
			return gson.toJson(JsonNull.INSTANCE);
		}
		return gson.toJson(o);
	}
	
	@RequestMapping(value="/getTotalDiskSpace.do")
	@ResponseBody 
	public totalCPUpercent getTotalDiskSpace(){
		   
		return service.perpareTotalCPU(service.getMasterInfroamtion());
		
	}
   
}
