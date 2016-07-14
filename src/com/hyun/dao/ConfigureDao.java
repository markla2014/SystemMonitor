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
	            String[] result = connection.getConfigOptions(1);
	            return result;
	        } catch (Throwable t) {
	            throw new GwtException(t.getMessage());
	        }
	    }
	 public String[] getCloudwaveOptions(CloudConnection connection) throws GwtException{
	     try {
             String[] result = connection.getConfigOptions(2);
             return result;
         } catch (Throwable t) {
             throw new GwtException(t.getMessage());
         }
	 }
}
