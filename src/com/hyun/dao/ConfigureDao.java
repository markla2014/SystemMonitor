package com.hyun.dao;

import org.springframework.stereotype.Repository;

import com.cloudwave.jdbc.CloudConnection;
import com.cloudwave.jdbc.CloudDatabaseMetaData;
import com.hyun.exception.GwtException;
@Repository
public class ConfigureDao extends BaseDao{
	public String[] getDfsStatus(CloudConnection connection) throws GwtException {
        try {
            String[] result = connection.getDfsStatus();
            return result;
        } catch (Throwable t) {
            throw new GwtException(t.getMessage());
        }
    }
	 public String[] getConfigOptions(CloudConnection connection) throws GwtException {
	        try {
	            String[] result = connection.getConfigOptions();
	            return result;
	        } catch (Throwable t) {
	            throw new GwtException(t.getMessage());
	        }
	    }
	   public boolean doRestartServer(CloudConnection conn,String target) throws GwtException {
	        try {
	            CloudDatabaseMetaData meta = (CloudDatabaseMetaData) conn.getMetaData();
	            return meta.executeRestartServer(target);
	        } catch (Throwable t) {
	            throw new GwtException(t.getMessage());
	        }
	    }
}
