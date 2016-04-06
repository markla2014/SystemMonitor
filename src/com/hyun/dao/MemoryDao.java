package com.hyun.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.DecimalFormat;
import java.util.ArrayList;

import com.cloudwave.jdbc.CloudDatabaseMetaData;
import com.hyun.exception.GwtException;

public class MemoryDao extends BaseDao {
    public static final int FETCH_PAGE = 1;
    public static final int PAGE_SIZE = 20;
    private static DecimalFormat  numberS2Format = new DecimalFormat("###,##0.00");
    private static final String AUTOKEY_COLUMN = "__CLOUDWAVE_AUTO_KEY__";
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
	   public String[][] getMoreData(ResultSet result) throws GwtException {
	        return getMoreData(result, FETCH_PAGE * PAGE_SIZE);
	    }
}
