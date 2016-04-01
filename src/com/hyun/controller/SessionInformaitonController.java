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
public class SessionInformaitonController {
	@Resource
	 private SessionListServiceImpl service;

@RequestMapping("/info.do")
public ModelAndView showSessionList(HttpServletRequest req,HttpServletResponse response) {
	ModelAndView mv = new ModelAndView("session");
	String[][] temp=service.getSessionList();
	mv.addObject("sessionList",JasonCover.toJason(temp));
	return mv;
}
}
