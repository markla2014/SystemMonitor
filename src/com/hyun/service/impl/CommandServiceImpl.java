package com.hyun.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudwave.jdbc.CloudConnection;
import com.cloudwave.jdbc.CloudResultSet;
import com.cloudwave.jdbc.bfile.CloudBfile;
import com.hyun.dao.CommandDao;
import com.hyun.service.CommandService;
import com.hyun.vo.DataTable;
import com.hyun.vo.dataInfo;
@Service 
public class CommandServiceImpl implements CommandService {

	@Autowired
	public CommandDao dao;
	private LinkedList<String> recorderList;
	public LinkedList<String> getRecorderList() {
		return recorderList;
	}
	public void setRecorderList(LinkedList<String> recorderList) {
		this.recorderList = recorderList;
	}

public long getCurrentCommandId() {
		return dao.getCommandTempId();
	}
private int BfileCount;
	private static Logger logger = Logger.getLogger(CommandServiceImpl.class);
	  public long getTotalRows(){
		  return dao.getResultCount();
	  }
	  /**
	   *  考虑在当中加入chach 但是考虑部署和性能问题暂时停止
	   */
	public String[][] getTableData(String schema,String table,int jumpPage,long id) {
		// TODO Auto-generated method stub
		  if(id==0){
			  id=System.currentTimeMillis();
		  }
	    return dao.getTableInfor(schema, table, jumpPage, id);
		
	}
	public String[][] getViewData(String schema,String view,int jumpPage,long id){
		  if(id==0){
			  id=System.currentTimeMillis();
		  }
	    return dao.getTableInfor(schema,view, jumpPage, id);
	}
	@Override
	public String createSchema(String schema, String name) {
		// TODO Auto-generated method stu
	
		try {
			return dao.createSchema(schema,name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		  return e.getMessage();
		}
	}
	@Override
	public String deleteSchema(String schema) {
		// TODO Auto-generated method stub
		
		try {
			return dao.deleteSchema(schema);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			 return e.getMessage();
		}
	}
	@Override
	public String deleteTable(String schema, String table) {
		// TODO Auto-generated method stub
		try {
			return dao.deleteTable(schema, table);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return e.getMessage();
		}
	}
	public String createTable(DataTable forntValue){
		 try{
			 int sqlcount=1;
			 String schema=forntValue.getSchema();
			 String table=forntValue.getTable();
			 StringBuffer sql=new StringBuffer("CREATE TABLE "+schema+"."+table+"(");
			 StringBuffer fullindexsql=new StringBuffer("create fulltext index on "+schema+"."+table+"(");
			 StringBuffer textsql=new StringBuffer("create text index on "+schema+"."+table+"(");
			 String uniqueSql=",unique(";
			 LinkedList<String> fullIndexrecord=new LinkedList<String>();
			 LinkedList<String> textrecord=new LinkedList<String>();
			 LinkedList<String> uniqueRecord=new LinkedList<String>();
			 String singleSql="";
			 boolean havefulltext=false;
			 boolean havtext=false;
			 boolean haveindex=false;
			 boolean haveUnique=false;
		
			 LinkedList<String> indexSql=new LinkedList<String>();
		   for( dataInfo i:forntValue.getCols()){
			if((i.getDatatype().equals("VARCHAR")||i.getDatatype().equals("CHAR")||i.getDatatype().equals("NUMERIC"))&&i.getLength()!=0){
			    singleSql=i.getColName()+" "+i.getDatatype()+"("+i.getLength()+")";	
			}else{
				singleSql=i.getColName()+" "+i.getDatatype();
			}
			if(i.getIsPrimary()==1){
				singleSql+=" primary key";
			}
			if(i.getIsNull()==1){
				singleSql+=" not null";
			}
			if(i.getIsUnique()==1){
				haveUnique=true;
				uniqueRecord.add(i.getColName());
			}
			if(i.getIsText()==1){
				havtext=true;
				textrecord.add(i.getColName());
			}
			if(i.getIscover()==1){
				  havefulltext=true;
				  fullIndexrecord.add(i.getColName());
			}
			if(i.getIsIndex()==1){
				haveindex=true;
				sqlcount++;
				String singleindexSql="create index on "+schema+"."+table+"("+i.getColName()+");";
		           indexSql.add(singleindexSql);
			}
			if(i.equals(forntValue.getCols().get((forntValue.getCols().size()-1)))){
				sql.append(singleSql);
			}else{
				sql.append(singleSql+" , ");
			}
		   }
		   if(haveUnique){
			   for(int i=0;i<uniqueRecord.size();i++){
				  
				   if(i==(uniqueRecord.size()-1)){
					   uniqueSql+=uniqueRecord.get(i)+")";
				   }else{
					   uniqueSql+=uniqueRecord.get(i)+","; 
				   }
			   }
			   sql.append(uniqueSql);
		   }
		
		   sql.append(");");
		   ArrayList<String> sqls=new ArrayList<String>();
		   sqls.add(sql.toString());
		   if(haveindex){
			   for(int i=0;i<indexSql.size();i++){
				    sqls.add(indexSql.get(i));
			   }
		   }
		   if(havefulltext){
		   for(int i=0;i<fullIndexrecord.size();i++){
			   fullindexsql.append(fullIndexrecord.get(i));
			  if(i!=(fullIndexrecord.size()-1))
				  fullindexsql.append(", ");
			  else
				  fullindexsql.append(");");
		   }
		   sqls.add(fullindexsql.toString());
		   }
		   if(havtext){
			   for(int i=0;i<textrecord.size();i++){
				   textsql.append(textrecord.get(i));
				   if(i!=(textrecord.size()-1))
					   textsql.append(", ");
				   else
					   textsql.append(");");
			   }
		   sqls.add(textsql.toString());
		   }
		   String[] sqlsTemp=new String[sqls.size()];
		   sqls.toArray(sqlsTemp);
			return dao.createCommand(sqlsTemp);
		 }catch(Exception e){
			 return e.getMessage();
		 }
	}
	public int getSreachQueryCount(String sql){
		try {
			return dao.sreachQueryCount(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		    logger.error(e.getMessage());
		    return -1;
		}
	}
   public String getGernalQuery(String sql){
	   
	   try {
		return dao.Gernalquery(sql);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		return e.getMessage();
	}
   }
   public String[][] getSreachQuery(String sql,int start,int end){
	   
	   String[][] temp;
	try {
		temp = dao.sreachQuery(sql, start, end);
		logger.debug(sql);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		temp=new String[1][1];
		temp[0][0]=e.getMessage();
	}
	return temp;   
   }
	@Override
	public String deleteView(String schema, String view) {
		// TODO Auto-generated method stub
		try {
			return dao.deleteView(schema, view);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		   return e.getMessage();
		}
	}
	@Override
	public String[][] getBfileInterface(String schema) {
		// TODO Auto-generated method stub
		 String sql = "select * from " +schema+".bfiles";
		try{
		  CloudConnection connection=dao.CreateConnection();
		String user=dao.getSchemaUser(connection, schema);
		if(user!=null){
			  sql +=" where create_user = '" + user + "'";
		}
		BfileCount=dao.sreachQueryCount(sql);
		String[][] template=this.getSreachQuery(sql,1,20);
		connection.close();
		 return template;
		}catch(Exception e){
			logger.error(e.getStackTrace());
	       String[][] temp=new String[1][1];
	       temp[0][0]=e.getMessage();
	       return temp;
		}
	}
	public int getBfileCount() {
		return BfileCount;
	}
	public void setBfileCount(int bfileCount) {
		BfileCount = bfileCount;
	}
	public String[][] getBfile(String schema,int start, int end){
		 String sql = "select * from " +schema+".bfiles";
			try{
				CloudConnection connection=dao.CreateConnection();
			String user=dao.getSchemaUser(connection, schema);
			if(user!=null){
				  sql +=" where create_user = '" + user + "'";
			}
			return this.getSreachQuery(sql,start,end);
			}catch(Exception e){
				logger.error(e.getStackTrace());
		       String[][] temp=new String[1][1];
		       temp[0][0]=e.getMessage();
		       return temp;
			}
		}
	@Override
	public CloudBfile getBFileDownlaod(long id) throws Exception {
		// TODO Auto-generated method stub
		CloudConnection connection=dao.CreateConnection();
		CloudBfile template=dao.getBfileDowlaod(connection, id);
		  connection.close();
		return template;
		
	}
	public String createView(String sql, String schema, String viewname) {
		// TODO Auto-generated method stub
		return dao.createView(schema,viewname,sql);
	}
	@Override
	public String[][] withQuery(String sql) {
		// TODO Auto-generated method stub
		long id=System.currentTimeMillis();
		return dao.withQuery(sql,id,dao.CreateConnection());
		
	}
	@Override
	public String[][] withQueryPage(long commandId, int pageNumer) {
		// TODO Auto-generated method stub
		return dao.withQuerypage(commandId, pageNumer);
	}
	@Override
	public long getRowsCount(String schema, String table) {
		// TODO Auto-generated method stub
		return dao.getResultCount();
	}
	public void cancleTemplate(long id) {
		// TODO Auto-generated method stub
		dao.removeTmeplate(id);
	}
}
