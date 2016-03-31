package com.hyun.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Properties;

import org.junit.Test;

import com.cloudwave.jdbc.CloudConnection;
import com.cloudwave.jdbc.CloudDatabaseMetaData;
import com.cloudwave.jdbc.CloudDriver;
import com.cloudwave.jdbc.CloudResultSet;
import com.hyun.exception.GwtException;

public class jdbcConnection {
	public static final String USER_NAME = "system";
	public static final String PASS_WORD = "CHANGEME";
	public static final String SERVER = ":@192.168.0.20:1978";
	public static final int CONNECT_POOLSIZE = 3;
    private static final String AUTOKEY_COLUMN = "__CLOUDWAVE_AUTO_KEY__";
    public static final int FETCH_PAGE = 1;
    public static final int PAGE_SIZE = 20;
    private static DecimalFormat  numberS2Format = new DecimalFormat("###,##0.00");

	public Connection jdbcConnectionTest() {
		try {
			CloudDriver driver = new CloudDriver();
			Properties info = new Properties();
			info.put("user", USER_NAME);
			info.put("password", PASS_WORD);
			String url = "jdbc:cloudwave" + SERVER;
			Connection conn = driver.connect(url, info);
			if (conn.isClosed() == true) {
				System.out.println("connection is successful");
			}
			return conn;
		} catch (Exception e) {
			return null;
		}
		
	}
 /**
  * 
  * @param conn
  * @return 类型为 数据库版本信息和compile Date
  */
	public String getJDBCVersion(Connection conn) {
		try {
			return ((CloudConnection) conn).getServerVersion();
		} catch (SQLException e) {
			return e.getMessage();
		}
	}
    /**
     *  获取在线对话
     * @param connection
     * @return
     * @throws GwtException
     */
	public String[][] getOnlineSessions(CloudConnection connection)
			throws GwtException {
		ArrayList<String[]> sessions = new ArrayList<String[]>();
		try {
			String[] rows = connection.getOnlineUser();
			sessions.add(new String[] { String.valueOf(rows.length) });
			sessions.add(new String[] { "用户", "地址", "访问时间" });
			for (int i = 0; i < rows.length; i++) {
				try {
					String[] tokens = rows[i].split(",");
					sessions.add(new String[] { tokens[0], tokens[2], tokens[1] });
				} catch (Throwable e) {
				}
			}
			return sessions.toArray(new String[0][]);
		} catch (Exception t) {
			throw new GwtException(t.getMessage());
		}
	}
	/*文件系统基于hdfs
	 * em 文件系统
	 */
	public String[] getDfsStatus(CloudConnection connection) throws GwtException {
        try {
            String[] result = connection.getDfsStatus();
            return result;
        } catch (Throwable t) {
            throw new GwtException(t.getMessage());
        }
    }
	/**
	 * em 系统配置信息
	 * @param connection
	 * @return
	 * @throws GwtException
	 */
	 public String[] getConfigOptions(CloudConnection connection) throws GwtException {
	        try {
	            String[] result = connection.getConfigOptions();
	            return result;
	        } catch (Throwable t) {
	            throw new GwtException(t.getMessage());
	        }
	    }
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
	 /**
	  * em 服务器状态
	  * @param connection
	  * @return
	  * @throws GwtException
	  */
	    public String[][] getServerList(Connection connection) throws GwtException {
	        ArrayList<String[]> rows = new ArrayList<String[]>();
	        try {
	            CloudDatabaseMetaData dbmeta = (CloudDatabaseMetaData) connection.getMetaData();
	            ResultSet result = dbmeta.getServers();
	            String[][] records = getServerMoreData(result,FETCH_PAGE * PAGE_SIZE);
	            result.close();
	            
	          //  rows.add(new String[] {String.valueOf(records.length)});
	           // rows.add(new String[] {"服务器", "类型", "状态", "系统", "JAVA", "处理器", "核心", "内存", "磁盘", "系统时间"});
	            for (String[] record : records) {
	                int coreNum = Integer.parseInt(record[6]);
	                if (coreNum < 0) {
	                    record[6] = "-";
	                }
	                
	                double memSize = Double.parseDouble(record[7]) / (1024 * 1024 * 1024);
	                if (memSize < 0) {
	                    record[7] = "-";
	                } else {
	                    record[7] = numberS2Format.format(memSize) + "G";    
	                }
	                
	                double diskSize = Double.parseDouble(record[8]) / (1024 * 1024 * 1024);
	                if (diskSize < 0) {
	                    record[8] = "-";    
	                } else {
	                    record[8] = numberS2Format.format(diskSize) + "G";
	                }
	                rows.add(record);
	            }
	            return rows.toArray(new String[0][]);
	        } catch (Throwable t) {
	            throw new GwtException(t.getMessage());
	        }
	    }
	 public static String[][] getServerMoreData(ResultSet result, long count) throws GwtException {
	        ArrayList<String[]> records = new ArrayList<String[]>();
	        try {
	            ResultSetMetaData meta = result.getMetaData();
	            int columnCount = meta.getColumnCount();
	            int baseIndex = 0;
	            if (meta.getColumnName(1).equals(AUTOKEY_COLUMN)) {
	                baseIndex = 1;
	            }
	            columnCount -= baseIndex; 
	            if (columnCount < 1) {
	                return new String[0][];
	            }
	            
	            int recordCount = 0;
	            while (recordCount < count && result.next()) {
	                String[] record = new String[columnCount];
	                for (int i = 0; i < columnCount; i ++) {
	                    String value = result.getString(baseIndex + i + 1);
	                    record[i] = value != null ? value : "NULL";
	                }
	                recordCount ++;
	                records.add(record);
	            }
	            return records.toArray(new String[0][]);
	        } catch (Throwable t) {
	            t.printStackTrace();
	            throw new GwtException(t.getMessage());
	        }
	    }
	 /*
	  * 
	  * 
	  */    public String getUserPrivileges(Connection connection, String user) throws GwtException {
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
	    
	    public String getUserPrivileges( CloudConnection connection,String user) throws GwtException {
	        try {
	            return getUserPrivileges(connection, user);
	        } catch (Throwable t) {
	            throw new GwtException(t.getMessage());
	        }
	    }
	    /*
	     *  系统重启 
	     * ****/
	    public boolean doRestartServer(CloudConnection conn,String target) throws GwtException {
	        try {
	            CloudDatabaseMetaData meta = (CloudDatabaseMetaData) conn.getMetaData();
	            return meta.executeRestartServer(target);
	        } catch (Throwable t) {
	            throw new GwtException(t.getMessage());
	        }
	    }
	  public String[][] getRunningSQL(CloudConnection connection) throws GwtException {
	        ArrayList<String[]> allRows = new ArrayList<String[]>();  
	        try {
	            CloudResultSet result = (CloudResultSet) connection.getRunningSql();
	            allRows.add(new String[] {String.valueOf(result.getRecordCount())});
	            allRows.add(new String[] {"用户", "时间", "SQL语句"});
	            String[][] records = getMoreData(result, result.getRecordCount());
	            for (String[] record : records) {
	                allRows.add(record);
	            } 
	            result.close();
	            return allRows.toArray(new String[0][]);
	        } catch (Exception t) {
	            throw new GwtException(t.getMessage());
	        }
	    }
	   public String[][] getMoreData(ResultSet result, long count) throws GwtException {
	        ArrayList<String[]> records = new ArrayList<String[]>();
	        try {
	            ResultSetMetaData meta = result.getMetaData();
	            int columnCount = meta.getColumnCount();
	            int baseIndex = 0;
	            if (meta.getColumnName(1).equals(AUTOKEY_COLUMN)) {
	                baseIndex = 1;
	            }
	            columnCount -= baseIndex; 
	            if (columnCount < 1) {
	                return new String[0][];
	            }
	            
	            int recordCount = 0;
	            while (recordCount < count && result.next()) {
	                String[] record = new String[columnCount];
	                for (int i = 0; i < columnCount; i ++) {
	                    String value = result.getString(baseIndex + i + 1);
	                    record[i] = value != null ? value : "NULL";
	                }
	                recordCount ++;
	                records.add(record);
	            }
	            return records.toArray(new String[0][]);
	        } catch (Throwable t) {
	            t.printStackTrace();
	            throw new GwtException(t.getMessage());
	        }
	    }
	   //////////////////////////*
	   
	   /*
	    * 内存利用率
	    */
	   public String[][] getSystemUtilization(Connection connection) throws GwtException {
	        ArrayList<String[]> rows = new ArrayList<String[]>();
	        try {
	            CloudDatabaseMetaData dbmeta = (CloudDatabaseMetaData) connection.getMetaData();
	            ResultSet result = dbmeta.getSystemUtilization();
	            String[][] records = getMoreData(result);
	            result.close();
	            
	            rows.add(new String[] {String.valueOf(records.length)});
	            rows.add(new String[] {"服务器", "类型", "状态", "系统利用率", "进程利用率"});
	            for (String[] record : records) {
	                rows.add(record);
	            }
	            return rows.toArray(new String[0][]);
	        } catch (Throwable t) {
	            throw new GwtException(t.getMessage());
	        }
	    }
	   public String[][] getMoreData(ResultSet result) throws GwtException {
	        return getMoreData(result, FETCH_PAGE * PAGE_SIZE);
	    }
	 ///////////////////////////////////////////////////////////////////////////////
	@Test
   public void testServerList(){
			Connection conn=jdbcConnectionTest();
			CloudConnection connect=((CloudConnection) conn);
		/////////////////////////////////////////////////
		try {
			String[][] a=getServerList(connect);
			for(String[] i:a){
				for(String j:i){
				System.out.print(j+" ");
				}
				System.out.println();
			}
		} catch (GwtException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			 
	////////////////////////////////
	
   }
	@Test
	public void testDFS(){
		Connection conn=jdbcConnectionTest();
		CloudConnection connect=((CloudConnection) conn);
		System.out.println("DFS test");
		/////////////////////////////////////////////////
		try {
			String[] a=getDfsStatus(connect);
			
				for(String j:a){
					System.out.println(j);
				}
			
		} catch (GwtException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("////////////////////////////////////////////");
		////////////////////////////////
		
	}
	@Test
	public void testConfig(){
		Connection conn=jdbcConnectionTest();
		CloudConnection connect=((CloudConnection) conn);
		System.out.println("config test");
		/////////////////////////////////////////////////
		try {
			String[] a=getConfigOptions(connect);
			
			for(String j:a){
				System.out.println(j);
			}
			
		} catch (GwtException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("////////////////////////////////////////////");
		////////////////////////////////
		
	}
	@Test
	public void testSession(){
		Connection conn=jdbcConnectionTest();
		CloudConnection connect=((CloudConnection) conn);
		System.out.println("session test");
		/////////////////////////////////////////////////
		try {
			String[][] a=getOnlineSessions(connect);
			
			for(String[] i:a){
				for(String j:i){
				System.out.print(j+" ");
				}
				System.out.println();
			}
			
		} catch (GwtException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("////////////////////////////////////////////");
		////////////////////////////////
		
	}
	@Test
	public void testSQLSession(){
		Connection conn=jdbcConnectionTest();
		CloudConnection connect=((CloudConnection) conn);
		System.out.println("sql test");
		/////////////////////////////////////////////////
		try {
			String[][] a=getRunningSQL(connect);
			
			for(String[] i:a){
				for(String j:i){
					System.out.print(j+" ");
				}
				System.out.println();
			}
			
		} catch (GwtException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("////////////////////////////////////////////");
		////////////////////////////////
		
	}
	@Test
	public void testOverView(){
		Connection conn=jdbcConnectionTest();
		CloudConnection connect=((CloudConnection) conn);
		System.out.println("overview");
		/////////////////////////////////////////////////
		try {
			String[] a=getSystemOverview(connect);
			
			
				for(String j:a){
					System.out.println(j+" ");
				}
			
			
		} catch (GwtException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("////////////////////////////////////////////");
		////////////////////////////////
	}
	@Test
	public void testSystemMemory(){
		Connection conn=jdbcConnectionTest();
		CloudConnection connect=((CloudConnection) conn);
		System.out.println("memory");
		/////////////////////////////////////////////////
		try {
			String[][] a=getSystemUtilization(connect);
			
			
			for(String[] i:a){
				for(String j:i){
					System.out.print(j+" ");
				}
				System.out.println();
			}
			
			
		} catch (GwtException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("////////////////////////////////////////////");
		////////////////////////////////
	}
	
}
