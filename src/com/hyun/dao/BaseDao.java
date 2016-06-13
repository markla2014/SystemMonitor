package com.hyun.dao;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractRefreshableApplicationContext;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cloudwave.jdbc.CloudConnection;
import com.cloudwave.jdbc.CloudDriver;
import com.hyun.common.ServerMonitorConstant;
import com.hyun.connectionpool.currentTemplate;
import com.hyun.exception.GwtException;

@Repository
public abstract class BaseDao {
	
	//public static final String USER_NAME = "system";
	//public static final String PASS_WORD = "CHANGEME";
 public static String username;
 public static String password;
 public static String serverAddress;
	public String getServerAddress() {
	return serverAddress;
}
public void setServerAddress(String serverAddress) {
	this.serverAddress = serverAddress;
}
	public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
	public static final String SERVER = ":@localhost:1978";
    private static final String AUTOKEY_COLUMN = "__CLOUDWAVE_AUTO_KEY__";
    private static DecimalFormat  numberS2Format = new DecimalFormat("###,##0.00");
    private static Logger logger=Logger.getLogger(BaseDao.class);
    @Resource
    private currentTemplate template;
    @Autowired
    //private ConnectionPoolManager pool;
    private static CloudConnection connection;
    public currentTemplate getTemplate(){
    	return template;
    }
    public CloudConnection CreateConnection(){
    	try{
    	CloudDriver driver = new CloudDriver();
		Properties info = new Properties();
		String user=this.getUsername();
		String password=this.getPassword();
		String addr=this.getServerAddress();
		info.put("user", user);
		info.put("password",password);
		String url = "jdbc:cloudwave:@"+addr+":1978";;
		
		CloudConnection thisConnection=(CloudConnection)driver.connect(url, info);
    
		return thisConnection;
    	}catch(Exception e){
    	logger.error(e.getMessage());
    	return null;
    	}
    }
	public CloudConnection getConnection() {
	    return this.connection;
	}
	public void setConnection(CloudConnection connection) {
		this.connection = connection;
	}
	public String getJdbcConnection(String username,String password,String ipaddress) throws GwtException {
		try {
			CloudDriver driver = new CloudDriver();
			Properties info = new Properties();
			info.put("user", username);
			info.put("password", password);
			String url = "jdbc:cloudwave" + SERVER;
			  this.setServerAddress(SERVER);
			if(!ipaddress.equals("")){
				url="jdbc:cloudwave:@"+ipaddress+":1978";
				this.setServerAddress(":@"+ipaddress+":1978");
			}
			
			connection = (CloudConnection)driver.connect(url, info);
			String returnValue=connection.isClosed()? ServerMonitorConstant.FAIL:ServerMonitorConstant.SUCCESSFUL;
			if(returnValue==ServerMonitorConstant.SUCCESSFUL){
			
			  BasicDataSource datasource=new BasicDataSource();
			   datasource.setDriverClassName("com.cloudwave.jdbc.CloudDriver");
			   datasource.setMaxIdle(2);
			   datasource.setPassword(password);
			   datasource.setUsername(username);
			   datasource.setMaxActive(20);
			   datasource.setUrl(url);
			    template.setDatasource(datasource);
			    WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();  
		        ServletContext servletContext = webApplicationContext.getServletContext();
		        WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);  
		        AbstractRefreshableApplicationContext arac = (AbstractRefreshableApplicationContext)wac;  
		        arac.refresh();  
		    	this.setUsername(username);
				this.setPassword(password);
			     this.setServerAddress(connection.getServerAddr());
			}
			return returnValue;
		} catch (Throwable t) {
			String message=t.getMessage();
			if (message != null) {
		     if (message.contains("Can not connect to url")) {
	                message = "无法连接数据库，请检查网络连接";
	            } else if (message.contains("User authentication failed")) {
	                message = "身份认证失败，请检查用户名/密码";
	            } else if (message.contains("Network is unreachable")) {
	                message = "网络不可达，请检查网络连接";
	            } else if (message.contains("CLOUDWAVE-000024")) {
	                message = "身份认证失败，请检查用户名/密码";
	            } else {
	                message = "发生连接异常，原因如下：" + message;
	            }
	        } else {
	            message = "发生未知异常，无法连接数据库";
	        }
			throw new GwtException(message);
        }
		
	}
}
