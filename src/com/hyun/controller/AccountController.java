package com.hyun.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hyun.common.Pager;
import com.hyun.entity.Account;
import com.hyun.service.AccountService;

@Controller
@RequestMapping("/account")
public class AccountController{
	public final static String login_Name="mark";
	public final static String login_PassWord="1234";
	@Resource(name="accountServiceImpl")
private AccountService as;
@RequestMapping("/add")
public String addAccount(HttpServletRequest req ){
	Account acc=new Account();
	acc.setName(req.getParameter("name"));
	acc.setPwd(req.getParameter("pwd"));
	as.save(acc);
	req.setAttribute("key", "success");
	return "hzjw";
}
@RequestMapping("/findByPage")
public String findByPage(HttpServletRequest req){
	int pageSize=3;
	int pageNo=0;
	String sPageNo=req.getParameter("pager.offset");
	if(sPageNo!=null){
		pageNo=Integer.parseInt(sPageNo);
	}
	Pager<Account> pager=as.findByPage(pageNo, pageSize);
	req.setAttribute("pager",pager);
	return "findByPage";
}
@RequestMapping("/login")
public String login(HttpServletRequest req){
	  String name=req.getParameter("username");
	  String password=req.getParameter("Password");
	   if(name.equals(login_Name)&&password.equals(login_PassWord)){
		   return "redirect:main";
	   }else{
		   return "redirect:/index.jsp";
	   }
	
}
}
