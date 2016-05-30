package com.hyun.service.impl;

import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.cloudwave.jdbc.CloudResultSet;
import com.hyun.dao.QueryDao;
import com.hyun.exception.GwtException;
import com.hyun.service.QueryService;
@Service
public class QueryServiceImpl implements QueryService {
@Autowired
private QueryDao dao;
private static Logger logger = Logger.getLogger(QueryServiceImpl.class);
private String[] checkedSchemaList;

	public String[] getCheckedSchemaList() {
	return checkedSchemaList;
}

public void setCheckedSchemaList(String[] checkedSchemaList) {
	this.checkedSchemaList = checkedSchemaList;
}

	public QueryServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String[] getSchema() {
		// TODO Auto-generated method stub
		
		try {
	           this.setCheckedSchemaList(dao.getSchemaNameList(dao.getConnection(),"default"));
				return this.getCheckedSchemaList();
		} catch (GwtException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
		return null;
	}

	@Override
	public String[][] getTables(String schema) {
		// TODO Auto-generated method stub
		 String[][] temp=null;
		try {
			
				temp=dao.getTableNameList(dao.getConnection(), schema);
		
		} catch (GwtException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
		return temp;
	}

	@Override
	public String[][] getView(String schema) {
		// TODO Auto-generated method stub
		 try {
			return dao.getViewList(dao.getConnection(),schema);
		} catch (GwtException e) {
			// TODO Auto-generated catch block
		  logger.error(e.getMessage());
		  return new String[1][1];
		}
	}

	@Override
	public String[][] getTableColumn(String schema,String table) {
		// TODO Auto-generated method stub
		try {
			return dao.getTableColumns(dao.getConnection(), schema, table);
		} catch (GwtException e) {
			// TODO Auto-generated catch block
			logger.error(e.getStackTrace());
			String[][] errorRetrun= new String[1][1];
			errorRetrun[0][0]=e.getMessage();
			return errorRetrun;
		}
	}

	@Override
	public String[][] getTableDistriution(String schema, String table) {
		try{
		dao.setTemplateResultCont(new LinkedList<String[][]>());
		return dao.getTableDistribution(dao.getConnection(), schema, table);
		}catch(GwtException e){
			logger.error(e.getStackTrace());
			String[][] errorRetrun= new String[1][1];
			errorRetrun[0][0]=e.getMessage();
			return errorRetrun;
		}
	}
	@Async
	public String[][] getTableDistributionByPage(String schema,String table,int pagenum){
		try{
			return dao.getTableDistributionRecord(dao.getConnection(), schema, table, pagenum);
		}catch(Exception e){
			logger.error(e.getStackTrace());
			String[][] errorRetrun= new String[1][1];
			errorRetrun[0][0]=e.getMessage();
			return errorRetrun;
		}
	}
	@Override
	public long getRowCount() {
		
		return dao.getRowCount();
	}
	public String[][] getViewDefination(String schema,String view){
		try {
			return dao.getViewDefinition(dao.getConnection(), schema,view);
		} catch (GwtException e) {
			// TODO Auto-generated catch block
			String[][] temp=new String[1][1];
			temp[0][0]=e.getMessage();
			logger.error(e.getMessage());
			return temp;
		}
	}
    public String[] checkTableName(String schema){
    	try {
			return dao.checkTableName(dao.getConnection(), schema);
		} catch (GwtException e) {
			// TODO Auto-generated catch block
			String[] temp=new String[1];
			temp[0]=e.getMessage();
			logger.error(e.getMessage());
			return temp;
		}
    	
    }
	@Override
	public String[] getUsers() {
		
		try {
			return dao.getUserNameList(dao.getConnection());
		} catch (GwtException e) {
			// TODO Auto-generated catch block
			logger.error(e.getStackTrace());
			String[] error={e.getMessage()};
			return error;
		}
	}

}
