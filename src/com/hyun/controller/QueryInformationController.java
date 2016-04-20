package com.hyun.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hyun.common.JasonCover;
import com.hyun.service.impl.CommandServiceImpl;
import com.hyun.service.impl.QueryServiceImpl;
@Controller
@RequestMapping("/query")
public class QueryInformationController {
@Autowired
private QueryServiceImpl service;
@Autowired
private CommandServiceImpl service1;
	
@RequestMapping("/getSchema.do")
public String getScheme(Map<String,Object> model){
	String[] temp=service.getSchema();
       model.put("schema",JasonCover.toJason(temp));
	return "menu";	
}
@RequestMapping("/getTables.do")
public ModelAndView getTebles(HttpServletRequest req,HttpServletResponse response){
   ModelAndView mv=new ModelAndView("biaolist");
   String schema=req.getParameter("schema");
	String[][] temp=service.getTables(schema);
     mv.addObject("tableList",JasonCover.toJason(temp));
     mv.addObject("schemaName",schema);
	return mv;	
}
@RequestMapping("/getViews.do")
public ModelAndView getViews(HttpServletRequest req,HttpServletResponse response){
	ModelAndView mv=new ModelAndView("biaolist");
	String schema=req.getParameter("schema");
	String[][] temp=service.getView(schema);
	mv.addObject("databaseName","DEFAULT");
	mv.addObject("schemaName",schema);
	mv.addObject("tableList",JasonCover.toJason(temp));
	return mv;	
}
@RequestMapping("/getTableCol.do")
public ModelAndView getTabelColumn(HttpServletRequest req,HttpServletResponse response){
	ModelAndView mv=new ModelAndView("biaoStruct");
	String schema=req.getParameter("schema");
	String table=req.getParameter("table");
	String[][] result=service.getTableColumn(schema, table);
	mv.addObject("table",table);
	mv.addObject("result",result);
	//int count=service1.getTotalRows();
	//mv.addObject("count",count);
	return mv;
}
@RequestMapping("/getTableDistribution.do")
public ModelAndView getTabelDistribution(HttpServletRequest req,HttpServletResponse response){
	ModelAndView mv=new ModelAndView("biaoStruct");
	String schema=req.getParameter("schema");
	String table=req.getParameter("table");
	String[][] result=service.getTableDistriution(schema, table);
	mv.addObject("table",table);
	mv.addObject("result",result);
	//int count=service1.getTotalRows();
	//mv.addObject("count",count);
	return mv;
}
@RequestMapping("/createTableInterface.do")
public ModelAndView createTableInterface(HttpServletRequest req,HttpServletResponse response){
	ModelAndView mv=new ModelAndView("createTable");
	String schema=req.getParameter("schema");
	mv.addObject("schema",schema);
	return mv;
}
}
