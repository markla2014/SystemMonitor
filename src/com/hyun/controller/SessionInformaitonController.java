package com.hyun.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hyun.common.JasonCover;
import com.hyun.service.impl.SessionListServiceImpl;

@Controller
@RequestMapping("/session")
public class SessionInformaitonController  extends BaseController {
	@Resource
	 private SessionListServiceImpl service;

@RequestMapping("/info.do")
public ModelAndView showSessionList(HttpServletRequest req,HttpServletResponse response) {
	ModelAndView mv = new ModelAndView("session");
	String[][] temp=service.getSessionList();
	String[][] temp1=service.getRunningSQL();
	mv.addObject("sessionList",JasonCover.toJason(temp));
	mv.addObject("SQL",JasonCover.toJason(temp1));
	return mv;
}
}
