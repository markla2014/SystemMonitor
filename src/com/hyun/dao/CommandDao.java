package com.hyun.dao;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.hyun.connectionpool.currentTemplate;
@Repository
public class CommandDao extends BaseDao {
   private int resultCount;
	@Autowired
	private currentTemplate template;
	
      public int getResultCount() {
		return resultCount;
	}

	public void setResultCount(int resultCount) {
		this.resultCount = resultCount;
	}

	public long runCommnad(String schema,String table){
    	return template.getTemplate().queryForObject("select count(*) from "+schema+"."+table,Long.class); 
      }
      
      public String[][] getTableInfor(String schema,String table,int start,int end){
    	 String sql="select * from "+schema+"."+table+" where rownum >= "+start+" and rownum <= "+end;
    	 List<Map<String, Object>> temp=template.getTemplate().queryForList(sql);
    	
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
}
