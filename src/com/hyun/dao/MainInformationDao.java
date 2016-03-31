package com.hyun.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.cloudwave.jdbc.CloudConnection;
import com.cloudwave.jdbc.CloudDatabaseMetaData;
import com.hyun.exception.GwtException;
@Repository
public class MainInformationDao extends BaseDao{
	  private static DecimalFormat  numberS2Format = new DecimalFormat("###,##0.00");
	    public static final int FETCH_PAGE = 1;
	    public static final int PAGE_SIZE = 20;
	    private static final String AUTOKEY_COLUMN = "__CLOUDWAVE_AUTO_KEY__";
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
	 public String[][] getServerMoreData(ResultSet result, long count) throws GwtException {
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
	}
	
