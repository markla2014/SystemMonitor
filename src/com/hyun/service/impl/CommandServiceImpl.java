package com.hyun.service.impl;

import java.sql.SQLException;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyun.common.indexPager;
import com.hyun.common.pageNumber;
import com.hyun.dao.CommandDao;
import com.hyun.service.CommandService;
@Service
public class CommandServiceImpl implements CommandService {

	@Autowired
	public CommandDao dao;
	private indexPager pager;
	private LinkedList<pageNumber> pagelist;
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
            String[][] temp={{"没有你数据"}}; 
			return temp;
		}else{
			 pageNumber a=pagelist.get(jumpPage-1);
             pager.setCurrentPage(jumpPage);
		return dao.getTableInfor(schema, table,a.getStart(),a.getEnd());
		}
		
	}
	@Override
	public String createSchema(String schema, String name) {
		// TODO Auto-generated method stu
	
		try {
			return dao.createSchema(schema,name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		  return e.getSQLState();
		}
	}
	@Override
	public String deleteSchema(String schema) {
		// TODO Auto-generated method stub
		
		try {
			return dao.deleteSchema(schema);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			 return e.getSQLState();
		}
	}
	@Override
	public String deleteTable(String schema, String table) {
		// TODO Auto-generated method stub
		try {
			return dao.deleteTable(schema, table);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return e.getSQLState();
		}
	}
}
