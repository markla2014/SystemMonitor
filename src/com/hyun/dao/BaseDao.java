package com.hyun.dao;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.apache.log4j.Logger;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cloudwave.jdbc.CloudConnection;
import com.cloudwave.jdbc.CloudDriver;
import com.hyun.common.ServerMonitorConstant;
import com.hyun.connectionpool.currentTemplate;

@Repository
public abstract class BaseDao {
	
	public static final String USER_NAME = "system";
	public static final String PASS_WORD = "CHANGEME";
	public static final String SERVER = ":@192.168.0.13:1978";
    private static final String AUTOKEY_COLUMN = "__CLOUDWAVE_AUTO_KEY__";
    private BasicDataSource datasource=new BasicDataSource();
    private static DecimalFormat  numberS2Format = new DecimalFormat("###,##0.00");
    private static Logger logger=Logger.getLogger(BaseDao.class);
    @Resource
    private currentTemplate template;
    @Autowired
    //private ConnectionPoolManager pool;
    private static CloudConnection connection;
	public CloudConnection getConnection() {
		try {
			if(connection==null||connection.isClosed()){
				CloudDriver driver = new CloudDriver();
				Properties info = new Properties();
				info.put("user", this.USER_NAME);
				info.put("password",this.PASS_WORD);
				String url = "jdbc:cloudwave" + SERVER;
				
					connection = (CloudConnection)driver.connect(url, info);
				
				return connection;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error(e.getStackTrace());
		}
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
			if(returnValue==ServerMonitorConstant.SUCCESSFUL){
			   datasource.setDriverClassName("com.cloudwave.jdbc.CloudDriver");
			   datasource.setMaxIdle(2);
			   datasource.setPassword(password);
			   datasource.setUsername(username);
			   datasource.setMaxActive(20);
			   datasource.setUrl(url);
			    template.setDatasource(datasource);
			}
			return returnValue;
		} catch (Exception e) {
		  return e.getMessage();
		}
		
	}
}
