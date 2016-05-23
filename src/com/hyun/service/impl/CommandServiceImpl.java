package com.hyun.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.cloudwave.jdbc.CloudConnection;
import com.cloudwave.jdbc.bfile.CloudBfile;
import com.hyun.common.indexPager;
import com.hyun.common.pageNumber;
import com.hyun.dao.CommandDao;
import com.hyun.service.CommandService;
import com.hyun.vo.DataTable;
import com.hyun.vo.dataInfo;
@Service 
public class CommandServiceImpl implements CommandService {

	@Autowired
	public CommandDao dao;
	private indexPager pager;
	private LinkedList<pageNumber> pagelist;
   private int BfileCount;
	private static Logger logger = Logger.getLogger(CommandServiceImpl.class);
	  public int getTotalRows(){
		  return dao.getResultCount();
	  }
	  /**
	   *  考虑在当中加入chach 但是考虑部署和性能问题暂时停止
	   */
      public long getRowsCount(String schema,String table){
    	  pager=new indexPager();
    	  pager.setInterval(20);
    	  long countTemp=dao.runCommnad(schema, table);
    	  pager.setTotalRecord(countTemp);
    	  long temp=pager.getTotalpages();
    	  pagelist=pager.getPageCount();
    	  pager.setCurrentPage(1);
    	  return countTemp;
    	  
      }
      public int getCurrent(){
    	  return pager.getCurrentPage();
      }
      public long getPageCount(){
    	  return pager.getTotalpages();
      }
	public String[][] getTableDate(String schema,String table,int jumpPage) {
		// TODO Auto-generated method stub
		if(this.getPageCount()<1){
            String[][] temp={{"没有数据"}}; 
			return temp;
		}else{
			 pageNumber a=pagelist.get(jumpPage-1);
             pager.setCurrentPage(jumpPage);
		return dao.getTableInfor(schema, table,a.getStart(),a.getEnd());
		}
		
	}

	public String[][] getViewData(String schema,String view,int jumpPage){
		if(this.getPageCount()<1){
            String[][] temp={{"没有数据"}}; 
			return temp;
		}else{
			 pageNumber a=pagelist.get(jumpPage-1);
             pager.setCurrentPage(jumpPage);
		return dao.getTableInfor(schema, view,a.getStart(),a.getEnd());
		}
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
			 LinkedList<String> fullIndexrecord=new LinkedList<String>();
			 String singleSql="";
			 boolean havefulltext=false;
			 boolean haveindex=false;
		
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
				singleSql+=",unique("+i.getColName()+")";
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
		   sql.append(");");
		   if(fullIndexrecord.size()>0){
			   sqlcount++;
		   }
		   String[] sqls=new String[sqlcount];
		   sqls[0]=sql.toString();
		   if(haveindex){
			   for(int i=0;i<indexSql.size();i++){
				   sqls[i+1]=indexSql.get(i);
			   }
		   }
		   if(havefulltext){
		   for(int i=0;i<fullIndexrecord.size();i++){
			   fullindexsql.append(fullIndexrecord.get(i));
			  if(i!=(fullIndexrecord.size()-1)){
				  fullindexsql.append(", ");
			  }else{
				  fullindexsql.append(");");
			  }
			  
		   }
		   sqls[sqls.length-1]=fullindexsql.toString();
		   }
			return dao.createCommand(sqls);
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
		String user=dao.getSchemaUser(dao.getConnection(), schema);
		if(user!=null){
			  sql +=" where create_user = '" + user + "'";
		}
		BfileCount=dao.sreachQueryCount(sql);
		return this.getSreachQuery(sql,1,20);
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
			String user=dao.getSchemaUser(dao.getConnection(), schema);
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
		return dao.getBfileDowlaod(dao.getConnection(), id);
	
		
	}
}
