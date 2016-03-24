package com.hyun.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hyun.service.AccountService;

@Controller
@RequestMapping("/main")
public class MainInformationController {
 public static String login_Name="";
 public final String login_PassWord="";
	@Resource(name="accountServiceImpl")
private AccountService as;
	@RequestMapping("/header.do")
	public ModelAndView showHead(HttpServletRequest req) {
		ModelAndView mv = new ModelAndView("head");
		mv.addObject("username", req.getAttribute("username"));
		return mv;
	}

	@RequestMapping("/login")
	public String login(HttpServletRequest req) {
		String name = req.getParameter("username");
		String password = req.getParameter("Password");
		if (name.equals(login_Name) && password.equals(login_PassWord)) {
			req.setAttribute("username", name);
			return "main";
		} else {
			req.setAttribute("errorMessage","您输入的用户名或者密码有误");
			return "index";
		}

	}
}
