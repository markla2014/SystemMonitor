package com.hyun.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.tomcat.dbcp.dbcp.DelegatingConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cloudwave.jdbc.CloudConnection;
import com.cloudwave.jdbc.CloudDatabaseMetaData;
import com.cloudwave.jdbc.CloudResultSet;
import com.cloudwave.jdbc.bfile.CloudBfile;
import com.hyun.connectionpool.currentTemplate;
import com.hyun.exception.GwtException;
import com.hyun.vo.WithCommandVo;
@Repository
public class CommandDao extends BaseDao {
   private long resultCount;
   private static final String SUCCESS="success";
   private static final String FAIL="fail";
   private static final Logger logge = Logger.getLogger(CommandDao.class);
	private static final String AUTOKEY_COLUMN = "__CLOUDWAVE_AUTO_KEY__";
	private  Map<Long,WithCommandVo> commandtemp=new HashMap<Long, WithCommandVo>();

	public Map<Long, WithCommandVo> getCommandtemp() {
		return commandtemp;
	}

	public void setCommandtemp(long time,WithCommandVo vo) {
		this.commandtemp.put(time, vo);
	}

	@Autowired
	private currentTemplate template;
	
      public long getResultCount() {
		return resultCount;
	}

	public void setResultCount(long resultCount) {
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
	 public int sreachQueryCount(String sql){
		 String runSql="select count(*) from ("+ sql+")";
		 int count=template.getTemplate().queryForObject(runSql,Integer.class);
		 return count;
	 }
	 public String[][] withQuerypage(long id,int pagenum){
		 WithCommandVo thiscommand=this.getCommandtemp().get(id);
		 
			ArrayList<String[]> array = new ArrayList<String[]>();
		 if(thiscommand.getCommandrecorder().size()>=pagenum){
			 array.add(thiscommand.getHeader());
			 for(String[] i:thiscommand.getCommandrecorder().get((pagenum-1))){
				array.add(i);
			 }
			
		 }else{
			 CloudResultSet result=thiscommand.getCurrentSet();
			 String[][] data;
			try {
				data = this.getMoreData(result,20);
		
			 thiscommand.getCommandrecorder().add(data);
			 array.add(thiscommand.getHeader());
			 for(String[] i:data){
				 array.add(i);
			 }
			 thiscommand.setCurrentSet(result);
		    this.getCommandtemp().remove(id);
		    this.getCommandtemp().put(id,thiscommand);
			} catch (Exception e) {
				// TODO Auto-generated catch block
			   array.add(new String[]{e.getMessage()});
			}
		 }
		 return array.toArray(new String[0][]);
	 }
	 public String[][] withQuery(String sql,long id,CloudConnection connection){
			ArrayList<String[]> array = new ArrayList<String[]>();
		   try{
			   CloudConnection conectionTemp=connection;
		      Statement statement=conectionTemp.createStatement();
		      CloudResultSet result=(CloudResultSet)statement.executeQuery(sql);
		      long resultCount=result.getRecordCount();
		       this.setResultCount(resultCount);
		      result.setFetchSize(20);
		      String[] header=this.getHeadData(result); 
		      array.add(header);
		      if(resultCount<1){
		    	  array.add(new String[]{"没有数据 "});
		      }else{
		    String[][] rows=this.getMoreData(result, 20);
		      for(String[] i:rows){
		    	  array.add(i);
		      }}
		      if(resultCount>20){
		          WithCommandVo vo=new WithCommandVo();
		          vo.setConnection(conectionTemp);
		          vo.setCurrentSet(result);
		          vo.setTotalPage(resultCount);
		          vo.setHeader(header);
		          vo.setRowCount(resultCount);
		          vo.setCommandrecorder(array.toArray(new String[0][]));
		          this.getCommandtemp().put(id,vo);
		      }
		   }catch(Exception e){
			   array.add(new String[]{e.getMessage()});
		   }
			  String[][] resultTemp= array.toArray(new String[0][]);
				return resultTemp;
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
	public String[][] getMoreData(ResultSet result, long count)
			throws GwtException {
		ArrayList<String[]> records = new ArrayList<String[]>();
		try {
			ResultSetMetaData meta = result.getMetaData();
			int columnCount = meta.getColumnCount();
			int baseIndex = 0;
			if (meta.getColumnName(1).equals(AUTOKEY_COLUMN)) {
				baseIndex = 1;
			}
			columnCount -= baseIndex;
			if (columnCount < 1) {
				return new String[0][];
			}

			int recordCount = 0;
			while (recordCount < count && result.next()) {
				String[] record = new String[columnCount];
				for (int i = 0; i < columnCount; i++) {
					String value = result.getString(baseIndex + i + 1);
					record[i] = value != null ? value : "NULL";
				}
				recordCount++;
				records.add(record);
			}
			return records.toArray(new String[0][]);
		} catch (Throwable t) {
			t.printStackTrace();
			throw new GwtException(t.getMessage());
		}
	}

	public String[] getHeadData(CloudResultSet result) throws SQLException {
		ResultSetMetaData meta = result.getMetaData();
		int columnCount = meta.getColumnCount();
		int baseIndex = 0;
		if (meta.getColumnName(1).equals(AUTOKEY_COLUMN)) {
			baseIndex = 1;
		}
		columnCount -= baseIndex;
		if (columnCount < 1) {
			return new String[0];
		}

		String[] header = new String[columnCount];
		for (int i = 0; i < header.length; i++) {
			header[i] = meta.getColumnName(baseIndex + i + 1);
		}
		return header;
	}

}
