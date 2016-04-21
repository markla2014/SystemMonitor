package com.hyun.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hyun.common.JasonCover;
import com.hyun.service.impl.CommandServiceImpl;
@Controller
@RequestMapping("/command")
public class CommandInformationController {
       @Autowired
	private CommandServiceImpl service;
@RequestMapping("/getTableData.do")
@ResponseBody
public Map<String,String> getTableData(HttpServletRequest req,HttpServletResponse response){
	String schema=req.getParameter("schema");
	String table=req.getParameter("table");
    int pageNum=Integer.parseInt(req.getParameter("pageNum"));
	String[][] result=service.getTableDate(schema, table,pageNum);
	Map<String,String> temp=new HashMap<String, String>();
	temp.put("info",JasonCover.toJason(result));
	temp.put("current",pageNum+"");
  return temp;
}
   
}
