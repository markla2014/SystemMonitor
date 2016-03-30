package com.hyun.dao;

import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.cloudwave.jdbc.CloudConnection;
import com.cloudwave.jdbc.CloudDriver;
import com.hyun.common.ServerMonitorConstant;
import com.hyun.exception.GwtException;

@Repository
public abstract class BaseDao {
	
	public static final String USER_NAME = "system";
	public static final String PASS_WORD = "CHANGEME";
	public static final String SERVER = ":@192.168.0.17:1978";

    private static final String AUTOKEY_COLUMN = "__CLOUDWAVE_AUTO_KEY__";

    private static DecimalFormat  numberS2Format = new DecimalFormat("###,##0.00");
    private CloudConnection connection;
	public CloudConnection getConnection() {
		return connection;
	}
	public void setConnection(CloudConnection connection) {
		this.connection = connection;
	}
	public String getJdbcConnection(String username,String password) {
		try {
			CloudDriver driver = new CloudDriver();
			Properties info = new Properties();
			info.put("user", username);
			info.put("password", password);
			String url = "jdbc:cloudwave" + SERVER;
			connection = (CloudConnection)driver.connect(url, info);
			String returnValue=connection.isClosed()? ServerMonitorConstant.FAIL:ServerMonitorConstant.SUCCESSFUL;
			return returnValue;
		} catch (Exception e) {
		  return e.getMessage();
		}
		
	}
}
