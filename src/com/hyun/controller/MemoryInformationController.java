package com.hyun.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hyun.common.JasonCover;
import com.hyun.service.ServerListService;
import com.hyun.service.impl.MemoryServiceImpl;
import com.hyun.service.impl.ServerListServiceImpl;
import com.hyun.vo.totalCPUpercent;

@Controller
@RequestMapping("/memory")
public class MemoryInformationController  extends BaseController {
	 @Resource
	 private MemoryServiceImpl service;
	 @RequestMapping("/info.do")
		public ModelAndView showMemoryList(HttpServletRequest req,HttpServletResponse response) {
			ModelAndView mv = new ModelAndView("memoryInformation");
			String[][] temp=service.getTotalMemoryInformation();
			LinkedList<totalCPUpercent> memoryList=service.getMemoryInformation();
			mv.addObject("info",JasonCover.toJason(temp));
			mv.addObject("memoryList", JasonCover.toJason(memoryList));
			return mv;
		}
	 
	 @RequestMapping("/getMemory.do")
	 @ResponseBody
	 public Map<String,LinkedList<totalCPUpercent>> getMemoryInfomration(){
	 	service.getTotalMemoryInformation();
	 	LinkedList<totalCPUpercent> temp=service.getMemoryInformation();
	 	Map<String,LinkedList<totalCPUpercent>> map=new HashMap<String,LinkedList<totalCPUpercent>>();
	 	map.put("info",temp);
	 	return map;
	 }
	 
	
}
