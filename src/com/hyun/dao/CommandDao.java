package com.hyun.dao;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.cloudwave.jdbc.CloudConnection;
import com.cloudwave.jdbc.CloudDatabaseMetaData;
import com.cloudwave.jdbc.bfile.CloudBfile;
import com.hyun.connectionpool.currentTemplate;
@Repository
public class CommandDao extends BaseDao {
   private int resultCount;
   private static final String SUCCESS="success";
   private static final String FAIL="fail";
   private static final Logger logge = Logger.getLogger(CommandDao.class);
	@Autowired
	private currentTemplate template;
	
      public int getResultCount() {
		return resultCount;
	}

	public void setResultCount(int resultCount) {
		this.resultCount = resultCount;
	}
    public String deleteSchema(String schema) throws Exception{
    String sql="drop schema "+schema;
    return (template.getTemplate().update(sql)>-1)?SUCCESS:FAIL;
    }
    public String deleteView(String schema,String view) throws Exception{
    	String sql="drop view "+schema+"."+view;
    			 
    			 return (template.getTemplate().update(sql)>-1)?SUCCESS:FAIL;
    }
    public String deleteTable(String schema,String table) throws Exception{
    	String sql="drop table "+schema+"."+table;
    	 return (template.getTemplate().update(sql)>-1)?SUCCESS:FAIL;
    }
    public String createSchema(String schema,String username) throws Exception{
    	String sql="create schema "+schema+" authorization "+username;
    	int test=template.getTemplate().update(sql);
    	 String value= (test>-1)?SUCCESS:FAIL;
    	 return value;
    }
      public String createCommand(String[] sql) throws Exception{
    	  logge.info(sql);
    	  String value=SUCCESS;
    	 int[] test=template.getTemplate().batchUpdate(sql);
    	 for(int i:test){
      System.out.println("sql 语句成功"+i);
    	 }
     	 return value;
      }

	public long runCommnad(String schema,String table){
    	return template.getTemplate().queryForObject("select count(*) from "+schema+"."+table,Long.class); 
      }
	public String Gernalquery(String sql) throws Exception{
	   int count=template.getTemplate().update(sql);
	   return (count>-1)?SUCCESS:FAIL;
	}
	 public int sreachQueryCount(String sql) throws Exception{
		 String runSql="select count(*) from ("+ sql+")";
		 int count=template.getTemplate().queryForObject(runSql,Integer.class);
		 return count;
	 }
      public String[][] sreachQuery(String sql,int start, int end) throws Exception{
    	  String runSql="select * from ("+ sql+") where rownum >= "+start+" and rownum <= "+end;
    	  List<Map<String, Object>> temp=template.getTemplate().queryForList(runSql);
     	 // Map<String, Object> keys=temp.get(0);
     	 this.setResultCount(temp.size());
     	if(this.resultCount==0){
     	String[][] returnValue={{"该表没有数据"}};
     	 return returnValue;
     	}else{
     	 int rows=temp.size()+1;
     	 int col=temp.get(0).keySet().size();
     	 String[][] returnValue=new String[rows][col];
            int i=1;
            int keyc=0;
     	 for(Map<String,Object> t:temp){
     		  int j=0;
         	   for(String k:t.keySet()){
         		   returnValue[0][j]=k;
         		   if(t.get(k)==null){
         			   returnValue[i][j]="";
         		   }else{
         			   returnValue[i][j]=t.get(k).toString();
         		   }
         		 
         		  j++;
         	   }
         	   i++;
            }
     	 
     	 return returnValue;
     	}
       }

      public String[][] getTableInfor(String schema,String table,int start,int end){
    	 String sql="select * from "+schema+"."+table+" where rownum >= "+start+" and rownum < "+end;
    	 List<Map<String, Object>> temp=template.getTemplate().queryForList(sql);
    	
    	 // Map<String, Object> keys=temp.get(0);
    	 this.setResultCount(temp.size());
    	if(this.resultCount==0){
    	String[][] returnValue={{"该表没有数据"}};
    	 return returnValue;
    	}else{
    	 int rows=temp.size()+1;
    	 int col=temp.get(0).keySet().size()+1;
    	 String[][] returnValue=new String[rows][col];
           int i=1;
           int keyc=start+1;
           returnValue[0][0]="记录条数";
    	 for(Map<String,Object> t:temp){
    		  int j=1;
    		  returnValue[i][0]=keyc+"";
        	   for(String k:t.keySet()){
        		   returnValue[0][j]=k;
        		   if(t.get(k)==null){
        			   returnValue[i][j]="";
        		   }else{
        			   returnValue[i][j]=t.get(k).toString();
        		   }
        		 
        		  j++;
        	   }
        	   i++;
        	   keyc++;
           }
    	 
    	 return returnValue;
    	}
      }
      public String getSchemaUser(CloudConnection connection, String schema) throws Exception{
    	  CloudDatabaseMetaData meta = (CloudDatabaseMetaData) connection.getMetaData();
          String user = meta.getSchemaOwner(schema);
          return user;
      }
      public CloudBfile getBfileDowlaod(CloudConnection connection,long id) throws Exception{
    	  CloudBfile file=connection.getBfile(id);
    	  return file;
      }

	public String createView(String schema,
			String viewname, String sql) {
		// TODO Auto-generated method stub
		try{
		String sqls="create view "+schema+"."+viewname+" as "+sql+";";
		int affect=template.getTemplate().update(sqls);
			return SUCCESS;
		}catch(Exception e){
			return e.getMessage();
		}
	}
}
