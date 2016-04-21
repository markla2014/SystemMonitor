package com.hyun.service.impl;

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
}
