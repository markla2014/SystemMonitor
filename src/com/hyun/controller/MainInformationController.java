package com.hyun.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/main")
public class MainInformationController {
	public final static String login_Name = "mark";
	public final static String login_PassWord = "1234";

	@RequestMapping("/show")
	public ModelAndView showMainInformation(HttpServletRequest req) {
		ModelAndView mv = new ModelAndView("hello");
		mv.addObject("title", "Spring MVC And Freemarker");
		mv.addObject("content", " Hello world ， test my first spring mvc ! ");
		return mv;
	}

	@RequestMapping("/show1")
	public ModelAndView index() {
		ModelAndView view = new ModelAndView("test");
		view.addObject("message", "Say hi for Freemarker.");
		return view;
	}

	@RequestMapping("/login")
	public String login(HttpServletRequest req) {
		String name = req.getParameter("username");
		String password = req.getParameter("Password");
		if (name.equals(login_Name) && password.equals(login_PassWord)) {
			return "main/show1";
		} else {
			return "success";
		}

	}
}
