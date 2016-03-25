package com.hyun.dao;

import org.springframework.stereotype.Repository;

import com.cloudwave.jdbc.CloudConnection;
import com.hyun.exception.GwtException;
@Repository
public class MainInformationDao extends BaseDao{

	 /**
	  * em系统首页信息
	  * @return
	  * @throws GwtException
	  */
	 public String[] getSystemOverview(CloudConnection connection) throws GwtException {
	        try {
	           
	            String[] result = connection.getSystemOverview();
	            return result;
	        } catch (Throwable t) {
	            throw new GwtException(t.getMessage());
	        }
	    }
	 
	
	}
	
