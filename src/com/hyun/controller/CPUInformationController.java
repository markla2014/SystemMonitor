package com.hyun.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hyun.common.JasonCover;
import com.hyun.service.impl.CPUServiceImpl;
import com.hyun.service.impl.ServerListServiceImpl;
import com.hyun.vo.totalCPUpercent;

@Controller
@RequestMapping("/cpu")
public class CPUInformationController {
@Autowired
private CPUServiceImpl service;
@Resource
private ServerListServiceImpl serverService;

@RequestMapping("/cpuInformation.do")
public ModelAndView intitalSend(HttpServletRequest req,HttpServletResponse response){
	ModelAndView vm=new ModelAndView("cpuInformation");
	LinkedList<totalCPUpercent> temp=service.getCPUInformation();
	String[][] temp1=serverService.getServerlist();
	vm.addObject("serverList",JasonCover.toJason(temp1));
	vm.addObject("info",JasonCover.toJason(temp));
	return vm;
}
@RequestMapping("/getCPU.do")
@ResponseBody
public Map<String,LinkedList<totalCPUpercent>> getCPUInfomration(){
	LinkedList<totalCPUpercent> temp=service.getCPUInformation();

	Map<String,LinkedList<totalCPUpercent>> map=new HashMap<String,LinkedList<totalCPUpercent>>();
	map.put("info",temp);
	return map;
}
	
}
