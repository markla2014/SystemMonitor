package com.hyun.service.impl;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;

import com.hyun.dao.QueryDao;
import com.hyun.exception.GwtException;
import com.hyun.service.QueryService;
@Service
public class QueryServiceImpl implements QueryService {
@Autowired
private QueryDao dao;
private static Logger logger = Logger.getLogger(QueryServiceImpl.class);
	public QueryServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String[] getSchema() {
		// TODO Auto-generated method stub
		
		try {
	
				return dao.getSchemaNameList(dao.getConnection(),"default");
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
			return new String[1][1];
		}
	}

	@Override
	public String[][] getTableDistriution(String schema, String table) {
		return dao.getTableDistribution(dao.getConnection(), schema, table);
	}

}
