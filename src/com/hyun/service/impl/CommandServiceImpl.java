package com.hyun.service.impl;

import java.sql.SQLException;
import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.junit.internal.runners.statements.Fail;
import org.omg.PortableInterceptor.SUCCESSFUL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	private static Logger logger = Logger.getLogger(CommandServiceImpl.class);
	  public int getTotalRows(){
		  return dao.getResultCount();
	  }
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
			 String schema=forntValue.getSchema();
			 String table=forntValue.getTable();
			 StringBuffer sql=new StringBuffer("CREATE TABLE "+schema+"."+table+"(");
			 StringBuffer fullindexsql=new StringBuffer("create fulltext index on"+schema+"."+table+"(");
			 String singleSql="";
			 boolean havefulltext=false;
			 boolean haveindex=false;
			 String Singlefulltextsql="";
			 StringBuffer indexSql=new StringBuffer();
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
			if(i.getIscover()==1){
				  havefulltext=true;
				fullindexsql.append(i.getColName()+",");
			}
			if(i.getIsIndex()==1){
				haveindex=true;
				String singleindexSql="create index on "+schema+"."+table+"("+i.getColName()+")";
				 indexSql.append(singleindexSql);
				 indexSql.append("\r\n");
			}
			if(i.equals(forntValue.getCols().get((forntValue.getCols().size()-1)))){
				sql.append(singleSql);
			}else{
				sql.append(singleSql+" , ");
			}
		   }
		   sql.append(");");
		   if(havefulltext){
			   sql.append("\r\n");
		    fullindexsql.append(");");
		    sql.append(fullindexsql.toString());
		   }
		   if(haveindex){
		    sql.append("\r\n");
		    sql.append(indexSql.toString());
		   }
			dao.createCommand(sql.toString());
		 }catch(Exception e){
			 return e.getMessage();
		 }
		
		return "";
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
}
