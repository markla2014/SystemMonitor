package com.hyun.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hyun.common.JasonCover;
import com.hyun.service.ServerListService;
import com.hyun.service.impl.ServerListServiceImpl;

@Controller
@RequestMapping("/memory")
public class MemoryInformationController {
	 @Resource
	 private ServerListServiceImpl service;
	 @RequestMapping("/info.do")
		public ModelAndView showServerList(HttpServletRequest req,HttpServletResponse response) {
			ModelAndView mv = new ModelAndView("memoryInformation");
			String[][] temp=service.getServerlist();
			mv.addObject("serverList",JasonCover.toJason(temp));
			return mv;
		}
	
}
