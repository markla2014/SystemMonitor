package com.hyun.dao;

import java.sql.Connection;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cloudwave.jdbc.CloudConnection;
import com.cloudwave.jdbc.CloudDatabaseMetaData;
import com.cloudwave.jdbc.CloudResultSet;
import com.hyun.connectionpool.currentTemplate;
import com.hyun.exception.GwtException;
@Repository
public class UserDao extends BaseDao {
	@Autowired
	private currentTemplate template;
	public String getUserPrivileges(Connection connection, String user) throws GwtException {
        try {
            CloudDatabaseMetaData meta = (CloudDatabaseMetaData) connection.getMetaData();
            String privileges = meta.getUserPrivileges(user);
            if (privileges != null) {
                return privileges;
            } else {
                return null;
            }
        } catch (Throwable t) {
            throw new GwtException(t.getMessage());
        }
    }
	    public String[] getUserNameList(String catalog,CloudConnection connection) throws GwtException {
	        try {
	            CloudDatabaseMetaData meta = (CloudDatabaseMetaData) connection.getMetaData();
	            CloudResultSet result = (CloudResultSet)meta.getUsers();
	            int count = (int) result.getRecordCount();
	            String[] users = new String[count];
	            boolean existDba = false;  
	            for (int i = 0; i < count && result.next(); i ++) {
	                String user = result.getString(1); 
	                if (!user.equals("system")) {
	                    users[i] = user;
	                } else {
	                    users[i] = "管理员在尾部";
	                    existDba = true;
	                }
	            }
	            result.close();
	            
	            Arrays.sort(users);
	            if (existDba) {
	                users[count - 1] = "system";    
	            }
	            return users;
	        } catch (Throwable t) {
	            throw new GwtException(t.getMessage());
	        }
	    }
	    public int changePassword(CloudConnection connection,String user,String newPassword,String oldpassord) throws Exception
	    {
	    	String sql="alter user "+user+" identified by "+newPassword+" replace "+oldpassord;
	    	if(connection.getUserName().equals("system")){
	    		sql="alter user "+user+" identified by "+newPassword;
	    	}
	    	return template.getTemplate().update(sql);
	    }
	    public int createUser(String username,String password) throws Exception{
	    	String sql="create user "+username+" identified by "+password;
	    	return template.getTemplate().update(sql);
	    }
	    public int deleteUser(String username) throws Exception{
	    	String sql="drop user "+username;
	    	return template.getTemplate().update(sql);
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
