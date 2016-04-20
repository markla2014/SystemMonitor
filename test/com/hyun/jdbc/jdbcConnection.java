package com.hyun.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.executor.ReuseExecutor;
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
    private static final String CACHE_RESULTINFO_KEY = "CACHE_RESULTINFO";
    private static DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
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
	    *cpu利用率
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
	   /**
	    *  内存利用率 
	    * @param connection
	    * @return
	    * @throws GwtException
	    */
	   public String[][] getMemorySize(Connection connection) throws GwtException {
	        ArrayList<String[]> rows = new ArrayList<String[]>();
	        try {
	            CloudDatabaseMetaData dbmeta = (CloudDatabaseMetaData) connection.getMetaData();
	            ResultSet result = dbmeta.getMemorySize();
	            String[][] records = getMoreData(result);
	            result.close();
	            
	            rows.add(new String[] {String.valueOf(records.length)});
	            rows.add(new String[] {"服务器", "类型", "状态", "物理内存", "配置内存", "提交内存", "已用内存", "交换内存"});
	            for (String[] record : records) {
	                Double phyical = Double.parseDouble(record[3])
	                        / (1024 * 1024 * 1024);
	                record[3] = numberS2Format.format(phyical);
	                Double config = Double.parseDouble(record[4])
	                        / (1024 * 1024 * 1024);
	                record[4] = numberS2Format.format(config);
	                Double commit = Double.parseDouble(record[5])
	                        / (1024 * 1024 * 1024);
	                record[5] = numberS2Format.format(commit);
	                Double current = Double.parseDouble(record[6])
	                        / (1024 * 1024 * 1024);
	                record[6] = numberS2Format.format(current);
	                Double swap = Double.parseDouble(record[7])
	                        / (1024 * 1024 * 1024);
	                record[7] = numberS2Format.format(swap);
	                rows.add(record);
	            }
	            return rows.toArray(new String[0][]);
	        } catch (Throwable t) {
	            throw new GwtException(t.getMessage());
	        }
	    }
	    ////////////////////////////////////////
	   /*
	    * running query 
	    */
	    public String[][] executeQuery(String theSQL,Connection connection, long count) throws GwtException {
	       
	            String[] querys = parseTheSQLs(theSQL);
	            if (querys.length == 1) {
	           //    return executeQuery(connection, querys[0],count);
	            } else {
	           //     return executeQuerys(querys, count);
	            }
	       return null;
	    }
	    
	    public String[][] executeQuerys(String[] theSQLs,Connection connection, long count) throws GwtException {
	        List<String[]> resultsets = new ArrayList<String[]>();
	        List<String> processes = new ArrayList<String>();
	        
	       
	        if (connection != null) {
	            try {
	                Statement stmt = connection.createStatement();
	                for (int i = 0; i < theSQLs.length; i ++) {
	                    String query = theSQLs[i];
	                    String[] info = new String[5];
	                    info[0] = (i + 1) + "/" + theSQLs.length;
	                    String process = "Query " + info[0] + " runs at " 
	                            + timeFormat.format(new Date()) + ": " + query;
	                    if (process.length() > 180) {
	                        process = process.substring(0, 180) + " ...";
	                    }
	                    processes.add(process);
	                    
	                    long time0 = System.currentTimeMillis();
	                    if (isQuery(query)) {
	                        try {
	                            CloudResultSet result = (CloudResultSet) stmt.executeQuery(query);
	                            info[1] = "查询";
	                            info[2] = numberS2Format.format((System.currentTimeMillis() - time0) / 1000.0) + "秒";
	                            info[3] = result.getRecordCount() + "条";
	                            info[4] = query;
	                            result.close();
	                        } catch (Exception e) {
	                            info[1] = "查询";
	                            info[2] = numberS2Format.format((System.currentTimeMillis() - time0) / 1000.0) + "秒";
	                            info[3] = "失败";
	                            info[4] = query + " [ERROR] " + e.getMessage();
	                        }
	                    } else {
	                        try {
	                            int result = stmt.executeUpdate(query);
	                            info[1] = "更新";
	                            info[2] = numberS2Format.format((System.currentTimeMillis() - time0) / 1000.0) + "秒";
	                            info[3] = result + "条";
	                            info[4] = query;
	                        } catch (Exception e) {
	                            info[1] = "更新";
	                            info[2] = numberS2Format.format((System.currentTimeMillis() - time0) / 1000.0) + "秒";
	                            info[3] = "失败";
	                            info[4] = query + " [ERROR] " + e.getMessage();
	                        }
	                    }
	                    
	                    process = "Query " + info[0] + " done in " +
	                            numberS2Format.format((System.currentTimeMillis() - time0) / 1000.0) + " secs: " + query;
	                    if (process.length() > 180) {
	                        process = process.substring(0, 180) + " ...";
	                    }
	                    processes.set(processes.size() - 1, process);
	                    resultsets.add(info);
	                }
	                stmt.close();
	            } catch (Exception e) {
	                String[] info = new String[5];
	                info[0] = "全部";
	                info[1] = "";
	                info[2] = "";
	                info[3] = "失败";
	                info[4] = "[ERROR] " + e.getMessage();
	                if (info[4].length() > 180) {
	                    info[4] = info[4].substring(0, 180);
	                }
	                resultsets.add(info);
	            }
	            resultsets.add(0, new String[] { String.valueOf(resultsets.size()) });
	            resultsets.add(1, new String[] {"任务", "类型", "时间", "结果", "语句"});
	            return resultsets.toArray(new String[0][]);
	        } else {
	            throw new GwtException("No connection available.");    
	        }
	    }
	   
	    private String[] parseTheSQLs(String theSQL) {
	        List<String> querys = new ArrayList<String>();
	        String[] rows = theSQL.split("\n");
	        StringBuilder sb = new StringBuilder();
	        for (String row : rows) {
	            sb.append(row + "\n");
	            row = row.trim();
	            if (row.endsWith(";")) {
	                querys.add(sb.toString());
	                sb.setLength(0);
	            }
	        }
	        if (sb.length() > 0) {
	            querys.add(sb.toString());
	        }
	        return querys.toArray(new String[0]);
	    }
	    private boolean isQuery(String theSQL) {
	        theSQL = theSQL.toLowerCase().trim();
	        return (theSQL.startsWith("desc") || theSQL.startsWith("select") || theSQL.startsWith("with"));
	    }
	    public String[] getSchemaNameList(Connection connection,String catalog) throws GwtException {
	        try {
	      
	            CloudDatabaseMetaData meta = (CloudDatabaseMetaData) connection.getMetaData();
	            CloudResultSet result = (CloudResultSet)meta.getSchemas();
	            String[] schemas = new String[(int) result.getRecordCount()];
	            for (int i = 0; i < schemas.length && result.next(); i ++) {
	                schemas[i] = result.getString(1);
	            }
	            result.close();
	            Arrays.sort(schemas);
	            return schemas;
	        } catch (Throwable t) {
	            throw new GwtException(t.getMessage());
	        }
	    }
	    public String[] getViewList(Connection connection,String catalog,String schema) throws GwtException {
	    	try {
	            CloudDatabaseMetaData meta = (CloudDatabaseMetaData) connection.getMetaData();
	            CloudResultSet result = (CloudResultSet)meta.getTables("DEFAULT", schema, null, new String[] { "VIEW" });
	            String[] tables = new String[(int) result.getRecordCount()];
	            for (int i = 0; i < tables.length && result.next(); i ++) {
	                tables[i] = result.getString(3);
	            }
	            result.close();
	            Arrays.sort(tables);
	            return tables;
	        } catch (Throwable t) {
	            throw new GwtException(t.getMessage());
	        }
	    }
	    
	    public String[] getTableNameList(Connection connection, String schema) throws GwtException {
	        try {
	            CloudDatabaseMetaData meta = (CloudDatabaseMetaData) connection.getMetaData();
	            CloudResultSet result = (CloudResultSet)meta.getTables("DEFAULT", schema, null, new String[] { "TABLE" });
	            String[] tables = new String[(int) result.getRecordCount()];
	            for (int i = 0; i < tables.length && result.next(); i ++) {
	                tables[i] = result.getString(3);
	            }
	            result.close();
	            Arrays.sort(tables);
	            return tables;
	        } catch (Throwable t) {
	            throw new GwtException(t.getMessage());
	        }
	    }
	    
	    /***
	     * no sql
	     */
	   
	    public String[][] getTableColumns(CloudConnection connection, String schema, String table) throws GwtException {
	        ArrayList<String[]> array = new ArrayList<String[]>();
	        try {
	            CloudDatabaseMetaData meta = (CloudDatabaseMetaData) connection.getMetaData();
	            CloudResultSet result = (CloudResultSet) meta.getColumns("DEFAULT_", schema, table, null);
	            
	            long recordCount = result.getRecordCount();
	            int[] columns = new int[] { 4, 6, 7, 13, 18 };
	            String[] head = new String[] {
	                    "字段", "类型", "长度", "默认值", "可空"
	            };
	            String[] head2 = new String[head.length + 4];
	            System.arraycopy(head, 0, head2, 0, head.length);
	            head2[head.length] = "索引";
	            head2[head.length + 1] = "全文";
	            head2[head.length + 2] = "主键";
	            head2[head.length + 3] = "键序";
	            
	            CloudResultSet pkResult = (CloudResultSet) meta.getPrimaryKeys("DEFAULT_", schema, table);
	            ArrayList<String> pkColumns = new ArrayList<String>();
	            ArrayList<Integer> pkSequences = new ArrayList<Integer>();
	            String pkName = null;
	            while (pkResult.next()) {
	                if (pkName == null) {
	                    pkName = pkResult.getString(6);
	                }
	                String pkColumn = pkResult.getString(4);
	                int pkSequence = pkResult.getInt(5);
	                pkColumns.add(pkColumn);
	                pkSequences.add(pkSequence);
	            }
	            pkResult.close();

	            ArrayList<String> ftiColumns = new ArrayList<String>();
	            String ftiColumnsString = meta.getFullTextIndexColumns(schema, table);
	            String[] tokens = ftiColumnsString.split(",");
	            for (String tok : tokens) {
	                if (tok.length() > 0) {
	                    ftiColumns.add(tok);
	                }
	            }
	            
	            ArrayList<String> indexColumns = new ArrayList<String>();
	            String indexColumnsString = meta.getIndexColumns(schema, table);
	            tokens = indexColumnsString.split(",");
	            for (String tok : tokens) {
	                if (tok.length() > 0) {
	                    indexColumns.add(tok);
	                }
	            }
	            
	            String[][] rows = getMoreData(result, recordCount, columns);
	            for (int i = 0; i < rows.length; i ++) { 
	                String[] record = rows[i]; 
	                if (record[0].equals(AUTOKEY_COLUMN)) {
	                    recordCount --;
	                }
	            }
	            
	            array.add(new String[] { String.valueOf(recordCount) });
	            array.add(head2);
	            for (int i = 0; i < rows.length; i ++) {
	                String[] record = rows[i]; 
	                if (record[0].equals(AUTOKEY_COLUMN)) {
	                    continue;
	                }
	                String[] record2 = new String[record.length + 4];
	                System.arraycopy(record, 0, record2, 0, record.length);
	                // remove length part from data type  
	                int ki = record2[1].indexOf("(");
	                if (ki > 0) {
	                    String ctype = record2[1].substring(0, ki);
	                    String clen = record2[1].substring(ki + 1, record2[1].length() - 1);
	                    record2[1] = ctype; 
	                    record2[2] = clen;
	                }
	                if (record2[1].equals("NUMBER")) {
	                    record2[1] = "NUMERIC";
	                }
	                // replace NULL value
	                if (record2[3].equals("NULL")) {
	                    record2[3] = "-";
	                }
	                
	                int index = indexColumns.indexOf(record[0]);
	                if (index >= 0) {
	                    record2[record.length] = "YES";
	                } else {
	                    record2[record.length] = "NO";
	                }
	                int fulltext = ftiColumns.indexOf(record[0]);
	                if (fulltext >= 0) {
	                    record2[record.length + 1] = "YES";
	                } else {
	                    record2[record.length + 1] = "NO";
	                }
	                int pki = pkColumns.indexOf(record[0]);
	                if (pki >= 0) {
	                    record2[record.length + 2] = "YES";
	                    record2[record.length + 3] = String.valueOf(pkSequences.get(pki));
	                } else {
	                    record2[record.length + 2] = "NO";
	                    record2[record.length + 3] = "-";
	                }
	                array.add(record2);
	            }
	            return array.toArray(new String[0][]);
	        } catch (Throwable t) {
	            throw new GwtException(t.getMessage());
	        }
	    }
	    public String[][] getMoreData(ResultSet result, long count, int[] columns) throws GwtException {
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
	                String[] record = new String[columns.length];
	                for (int i = 0; i < columns.length; i ++) {
	                    String value = result.getString(baseIndex + columns[i]);
	                    record[i] = value != null ? value : "NULL";
	                }
	                recordCount ++;
	                records.add(record);
	            }
	            return records.toArray(new String[0][]);
	        } catch (Throwable t) {
	            throw new GwtException(t.getMessage());
	        }
	    }
	    public String[][] getTableDistribution(CloudConnection connection, String schema, String table)  {
	        ArrayList<String[]> array = new ArrayList<String[]>();
	        try {
	            CloudDatabaseMetaData dbmeta = (CloudDatabaseMetaData) connection.getMetaData();
	            CloudResultSet result = (CloudResultSet)dbmeta.getTablets(schema, table);
	            return  this.getMoreData(result, 20);
//	            long recordCount = result.getRecordCount();
//	            array.add(new String[] { String.valueOf(recordCount) });
//	            String[] head = getHeadData(result);
//	            array.add(head);
//	            CloudResultSet result1 = (CloudResultSet)dbmeta.getTablets(schema, table);
//	            String[][] rows = getMoreData(result,20); 
//	            for (int i = 0; i < rows.length; i ++) {
//	              //  array.add(rows[i]);
	            //}
	          //  return array.toArray(new String[0][]);
	        } catch (Exception t) {
	           t.printStackTrace();
	           return null;
	        }
	       
	    }
	    public String[] getHeadData(CloudResultSet result) throws SQLException {
	        ResultSetMetaData meta = result.getMetaData();
	        int columnCount = meta.getColumnCount();
	        int baseIndex = 0;
	        if (meta.getColumnName(1).equals(AUTOKEY_COLUMN)) {
	            baseIndex = 1;
	        }
	        columnCount -= baseIndex; 
	        if (columnCount < 1) {
	            return new String[0];
	        }
	        
	        String[] header = new String[columnCount];
	        for (int i = 0; i < header.length; i ++) {
	            header[i] = meta.getColumnName(baseIndex + i + 1);
	        }
	        return header;
	    }
	    
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
	public void testSystemCPU(){
		Connection conn=jdbcConnectionTest();
		CloudConnection connect=((CloudConnection) conn);
		System.out.println("cpu running");
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
	@Test
	public void testSystemMemory(){
		Connection conn=jdbcConnectionTest();
		CloudConnection connect=((CloudConnection) conn);
		System.out.println("memory");
		/////////////////////////////////////////////////
		try {
			String[][] a=this.getMemorySize(connect);
			
			
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
	}
	@Test
	public void testGetSchema(){
		Connection conn=jdbcConnectionTest();
		CloudConnection connect=((CloudConnection) conn);
		System.out.println("schema");
		try {
			String[] a=this.getSchemaNameList(connect,"default");
			for(String i:a){
				System.out.println(i);
			}
		} catch (GwtException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Test
	public void testGetTables(){
		Connection conn=jdbcConnectionTest();
		CloudConnection connect=((CloudConnection) conn);
		System.out.println("schema tables");
		try {
			String[] a=this.getTableNameList(connect,"ITEST");
			for(String i:a){
				System.out.println(i);
			}
		} catch (GwtException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Test
	public void testGetView(){
		Connection conn=jdbcConnectionTest();
		CloudConnection connect=((CloudConnection) conn);
		System.out.println("view");
		try {
			String[] a=this.getViewList(connect,"default","ITEST");
			for(String i:a){
				System.out.println(i);
			}
		} catch (GwtException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Test
	public void testTableDefinition(){
		Connection conn=jdbcConnectionTest();
		CloudConnection connect=((CloudConnection) conn);
			String[][] a=this.getTableDistribution(connect,"ITEST1c","tgfhfgh");
			System.out.println(a.length);
			for(String[] i:a){
				System.out.println(i.length);
				for(String j:i){
					System.out.print(j+" ");
				}
				System.out.println();
			}
		
	}
}
