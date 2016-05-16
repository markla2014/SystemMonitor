package com.hyun.controller;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ehcache.CacheManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyun.common.JasonCover;
import com.hyun.service.impl.CommandServiceImpl;
import com.hyun.vo.DataTable;
import com.hyun.vo.dataInfo;

@Controller
@RequestMapping("/command")
public class CommandInformationController {
	@Autowired
	private CommandServiceImpl service;
	@Autowired
	private CacheManager cacheManager;

	@RequestMapping("/getTableData.do")
	@ResponseBody
	public Map<String, String> getTableData(HttpServletRequest req,
			HttpServletResponse response) {
		String schema = req.getParameter("schema");
		String table = req.getParameter("table");
		int pageNum = Integer.parseInt(req.getParameter("pageNum"));
		String[][] result = service.getTableDate(schema, table, pageNum);
		Map<String, String> temp = new HashMap<String, String>();
		temp.put("info", JasonCover.toJason(result));
		temp.put("current", pageNum + "");
		return temp;
	}
	/**
	 * view 和table 一致如有时间更改
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping("/getViewData.do")
	@ResponseBody
	public Map<String, String> getViewData(HttpServletRequest req,
			HttpServletResponse response) {
		
		String schema = req.getParameter("schema");
		String table = req.getParameter("table");
		int pageNum = Integer.parseInt(req.getParameter("pageNum"));
		String[][] result = service.getTableDate(schema, table, pageNum);
		Map<String, String> temp = new HashMap<String, String>();
		temp.put("info", JasonCover.toJason(result));
		temp.put("currentpage", pageNum + "");
		return temp;
	}
	
	@RequestMapping(value = "/createTable.do", method = RequestMethod.POST)
	@ResponseBody
	public String create(@ModelAttribute("table") String table,
			@ModelAttribute("cols") String test,
			@ModelAttribute("schema") String schema) {
		ObjectMapper objectMapper = new ObjectMapper();
		List<Map<String, Object>> list = new LinkedList<Map<String, Object>>();
		try {
			list = objectMapper.readValue(test, List.class);
			DataTable temp = new DataTable();
			temp.setSchema(schema);
			temp.setTable(table);
			LinkedList<dataInfo> cols=new LinkedList<dataInfo>();
			for (Map<String, Object> t : list) {
				
				dataInfo col = new dataInfo();
				for (String a : t.keySet()) {
					Field field = col.getClass().getDeclaredField(a);
					String types=field.getType().toString();
					field.setAccessible(true);
					if(types.equals("class java.lang.Integer")||types.equals("int")){
					  int value=0;
						if(t.get(a)!=""){
							value=Integer.parseInt((String) t.get(a));
						}
			             field.set(col,value);
					}else{
					field.set(col, t.get(a));
					}
				}
                cols.add(col);
			}
			Collections.sort(cols);
			DataTable temp1=new DataTable();
			temp1.setSchema(schema);
			temp1.setTable(table);
			temp1.setCols(cols);
	    service.createTable(temp1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return JasonCover.toJason(e.getMessage());
		}
		return JasonCover.toJason("success");
	}
	@RequestMapping("/getCommandInterface.do")
	public ModelAndView getCommandInterface(HttpServletRequest req,
			HttpServletResponse response) {
		ModelAndView mv=new ModelAndView("command");
		return mv;
	}
	@SuppressWarnings("unchecked")
	@RequestMapping("/getGernalCommand.do")
	@ResponseBody
	public Map<String,String> getGernalCommand(HttpServletRequest req,HttpServletResponse response){
		Map<String,String> temp=new HashMap<String, String>();
		String sql=req.getParameter("sql");
	     temp.put("runned", sql);
	     String returnValue=service.getGernalQuery(sql);
		 temp.put("result", returnValue);
		return temp;
	}
   @RequestMapping("/getSreachquery.do")
   public ModelAndView getSqueryQuery(HttpServletRequest req,HttpServletResponse response){
	   ModelAndView mv=new ModelAndView("sreachResult");
	   String sql=req.getParameter("sql");
	   mv.addObject("runningsql", sql);
	   int count=service.getSreachQueryCount(sql);
	   mv.addObject("recordCount",count);
	   String[][] value=service.getSreachQuery(sql,0,20);
	   mv.addObject("result", value);
	   mv.addObject("current",1);
	   int totalpage=count/20;
	   if((count%20)!=0){
	    totalpage++;
	   }
	   mv.addObject("totalpage",totalpage);
	   return mv;
   }
   @RequestMapping("/getSreach.do")
   public Map<String,String> getSquery(HttpServletRequest req,HttpServletResponse response){
	 Map<String,String> temp=new HashMap<String, String>();
	 String sql=req.getParameter("sql");
	 String pageNum=req.getParameter("pageNum");
	 int current=Integer.parseInt(pageNum);
	 int start=(current-1)*20;
	 int end=current*20;
	 String[][] value=service.getSreachQuery(sql,start,end);
	 temp.put("info", JasonCover.toJason(value));
	 temp.put("current", current+"");
	 return temp;
   }
@RequestMapping("/getBFile.do")
@ResponseBody
public Map<String,String> getBFile(HttpServletRequest req){
	Map<String,String> temp=new HashMap<String,String>();
	String schema=req.getParameter("schema");
	String pageNum=req.getParameter("pageNum");
	int current=Integer.parseInt(pageNum);
	 int start=(current-1)*20;
	 int end=current*20;
	String[][] value=service.getBfile(schema, start, end);
	 temp.put("info", JasonCover.toJason(value));
	 temp.put("current", current+"");
	 return temp;
}
}
