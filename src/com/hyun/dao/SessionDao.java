package com.hyun.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.cloudwave.jdbc.CloudConnection;
import com.cloudwave.jdbc.CloudResultSet;
import com.hyun.exception.GwtException;

@Repository
public class SessionDao extends BaseDao {
    private static final String AUTOKEY_COLUMN = "__CLOUDWAVE_AUTO_KEY__";
	public String[][] getOnlineSessions(CloudConnection connection)
			throws GwtException {
		ArrayList<String[]> sessions = new ArrayList<String[]>();
		try {
			String[] rows = connection.getOnlineUser();
			//sessions.add(new String[] { String.valueOf(rows.length) });
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
	public String[][] getRunningSQL(CloudConnection connection) throws GwtException {
        ArrayList<String[]> allRows = new ArrayList<String[]>();  
        try {
            CloudResultSet result = (CloudResultSet) connection.getRunningSql();
            //allRows.add(new String[] {String.valueOf(result.getRecordCount())});
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
}
