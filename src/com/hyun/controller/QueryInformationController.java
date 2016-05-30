package com.hyun.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hyun.common.JasonCover;
import com.hyun.common.ServerMonitorConstant;
import com.hyun.service.impl.CommandServiceImpl;
import com.hyun.service.impl.QueryServiceImpl;
@Controller
@RequestMapping("/query")
public class QueryInformationController  extends BaseController {
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
	ModelAndView mv=new ModelAndView("viewlist");
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
	long recordCount=service1.getRowsCount(schema, table);
	long pageCount=service1.getPageCount();
	int currentpage=service1.getCurrent();
	mv.addObject("table",table);
	mv.addObject("schema",schema);
	mv.addObject("result",result);
	mv.addObject("rowCount",service.getRowCount());
	mv.addObject("recordCount",recordCount);
	mv.addObject("pageCount",pageCount);
	mv.addObject("currentpage",currentpage);
	//int count=service1.getTotalRows();
	//mv.addObject("count",count);
	return mv;
}
@RequestMapping("/getViewDataInterface.do")
public ModelAndView getViewData(HttpServletRequest req,HttpServletResponse response){
	ModelAndView mv=new ModelAndView("viewStruct");
	String schema=req.getParameter("schema");
	String table=req.getParameter("table");
	long recordCount=service1.getRowsCount(schema, table);
	String[][] result=service1.getViewData(schema,table,1);
	long pageCount=service1.getPageCount();
	int currentpage=service1.getCurrent();
	mv.addObject("table",table);
	mv.addObject("schema",schema);
	mv.addObject("result",result);
	mv.addObject("rowCount",service.getRowCount());
	mv.addObject("recordCount",recordCount);
	mv.addObject("pageCount",pageCount);
	mv.addObject("currentpage",currentpage);
	//int count=service1.getTotalRows();
	//mv.addObject("count",count);
	return mv;
}

@RequestMapping("/getTableColumn.do")
@ResponseBody
public Map<String,String> getTableColumns(HttpServletRequest req,HttpServletResponse response){
	String schema=req.getParameter("schema");
	String table=req.getParameter("table");
	String[][] result=service.getTableColumn(schema, table);
	Map<String,String> temp=new HashMap<String, String>();
	temp.put("info",JasonCover.toJason(result));
	return temp;
}
@RequestMapping("/getViewDefiniation.do")
@ResponseBody
public Map<String,String> getViewDfiniation(HttpServletRequest req,HttpServletResponse response){
	String schema=req.getParameter("schema");
	String table=req.getParameter("table");
	String[][] result=service.getViewDefination(schema, table);
	Map<String,String> temp=new HashMap<String, String>();
	temp.put("info",JasonCover.toJason(result));
	return temp;
}
@RequestMapping("/getTableDistribution.do")
@ResponseBody
public Map<String,String> getTabelDistribution(HttpServletRequest req,HttpServletResponse response){
	String schema=req.getParameter("schema");
	String table=req.getParameter("table");
	String[][] result=service.getTableDistriution(schema, table);
	Map<String,String> temp=new HashMap<String, String>();
	temp.put("info",JasonCover.toJason(result));
	//int count=service1.getTotalRows();
	//mv.addObject("count",count);
	return temp;
}
@RequestMapping("/getTableDistributionpage.do")
@ResponseBody
public Map<String,String> getTabelDistributionpage(HttpServletRequest req,HttpServletResponse response){
	String schema=req.getParameter("schema");
	String table=req.getParameter("table");
	int pagenum=Integer.parseInt(req.getParameter("pageNum"));
	String[][] result=service.getTableDistributionByPage(schema, table, pagenum);
	Map<String,String> temp=new HashMap<String, String>();
	long totalcount=service.getRowCount();
	long pagenumber=totalcount/20;
	if(!(totalcount%20==0)){
		pagenumber++;
	}
	temp.put("info",JasonCover.toJason(result));
	temp.put("current",JasonCover.toJason(pagenum));
	temp.put("totalcount", totalcount+"");
	temp.put("pageNumber",pagenumber+"");
	//int count=service1.getTotalRows();
	//mv.addObject("count",count);
	return temp;
}
@RequestMapping("/createTableInterface.do")
public ModelAndView createTableInterface(HttpServletRequest req,HttpServletResponse response){
	ModelAndView mv=new ModelAndView("createTable");
	String schema=req.getParameter("schema");
    String[] temp1=service.checkTableName(schema);
    mv.addObject("temp1",JasonCover.toJason(temp1));
	mv.addObject("schema",schema);
	return mv;
}
@RequestMapping("/getCreateSchemaInterface.do")
public ModelAndView getCreateSchemaInterface(HttpServletRequest req,HttpServletResponse response){
ModelAndView mv=new ModelAndView("createSchema");
String[] temp=service.getUsers();
mv.addObject("userlist",temp);
return mv;
}
@RequestMapping("/deleteTable.do")
public ModelAndView deleteTable(HttpServletRequest req,HttpServletResponse response){
	String schema=req.getParameter("schema");
	String table=req.getParameter("table");
	ModelAndView mv =new ModelAndView("forward:getTables.do?schema="+schema);
	service1.deleteTable(schema, table);
	return mv;
}
@RequestMapping("/deleteSchema.do")
public ModelAndView deleteSchema(HttpServletRequest req,HttpServletResponse response){
	ModelAndView mv =new ModelAndView("forward:getSchema.do");
	String schema=req.getParameter("schema");
	service1.deleteSchema(schema);
	return mv;
}
@RequestMapping("/deleteView.do")
public ModelAndView deleteView(HttpServletRequest req,HttpServletResponse response){
	
	String schema=req.getParameter("schema");
	String view=req.getParameter("view");
	ModelAndView mv =new ModelAndView("forward:getViews.do?schema="+schema);
	service1.deleteView(schema, view);
	return mv;
}
@RequestMapping("/createSchema.do")
@ResponseBody
public String createrSchema(HttpServletRequest req,HttpServletResponse response){
	String schema=req.getParameter("schema");
	String name=req.getParameter("name");
	if(service.getCheckedSchemaList().length<1){
	
		return service1.createSchema(schema, name);
	}
	if(ServerMonitorConstant.checkArray(service.getCheckedSchemaList(), schema)){
		return "该模块已经存在请重新命名";
	}else{
		return service1.createSchema(schema, name);
	}
}

@RequestMapping("/download.do")  
public ResponseEntity<byte[]> download(HttpServletRequest req) throws IOException {  
    HttpHeaders headers = new HttpHeaders(); 
    String input=req.getParameter("commandInput");
    if(input==null){
    	input="";
    }
    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);  
    headers.setContentDispositionFormData("attachment", "sql.txt");  
    return new ResponseEntity<byte[]>(input.getBytes(),headers, HttpStatus.CREATED);  
}  
@RequestMapping("/getBfileInterface.do")
public ModelAndView getBfileInterface(HttpServletRequest req){
	ModelAndView mv =new ModelAndView("fileList");
	String schema=req.getParameter("schema");
	String[][] temp=service1.getBfileInterface(schema);
	int recordCount=service1.getBfileCount();
	int pageCount=recordCount/20;
	if(recordCount%20!=0){
		pageCount++;
	}
	mv.addObject("result",temp);
	mv.addObject("schema", schema);
	mv.addObject("recordCount",recordCount);
	mv.addObject("pageCount", pageCount);
	mv.addObject("currentpage", 1);
	return mv;  
}
@RequestMapping("/createViewInterface.do")
public ModelAndView getViewInterface(HttpServletRequest req){
	ModelAndView mv=new ModelAndView("createView");
	String schema=req.getParameter("schema");
	mv.addObject("schema", schema);
	mv.addObject("error","");
	return mv;
}

@RequestMapping("/createView.do")
public ModelAndView getView(HttpServletRequest req){
	
	String schema=req.getParameter("schema");
	String sql=req.getParameter("sql").trim();
	String viewname=req.getParameter("name");
	if(sql.contains(";")){
		sql.replace(";","");
	}
	String result=service1.createView(sql,schema,viewname);
	if(result=="success"){
		ModelAndView mv=new ModelAndView("forward:getViews.do?schema="+schema);
		return mv;
	}else{
		ModelAndView mv=new ModelAndView("createView");
		mv.addObject("schema", schema);
		mv.addObject("error",result);
		return mv;
	}
}
}
