package com.hyun.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.cloudwave.jdbc.CloudDatabaseMetaData;
import com.hyun.exception.GwtException;

@Repository
public class CPUDao extends BaseDao {
    public static final int FETCH_PAGE = 1;
    public static final int PAGE_SIZE = 20;
    private static final String AUTOKEY_COLUMN = "__CLOUDWAVE_AUTO_KEY__";

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

            // rows.add(new String[] {String.valueOf(records.length)});
            // rows.add(new String[] {"服务器", "类型", "状态", "系统利用率", "进程利用率"});
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
                for (int i = 0; i < columnCount; i++) {
                    String value = result.getString(baseIndex + i + 1);
                    record[i] = value != null ? value : "NULL";
                }
                recordCount++;
                records.add(record);
            }
            return records.toArray(new String[0][]);
        } catch (Throwable t) {
            t.printStackTrace();
            throw new GwtException(t.getMessage());
        }
    }
}
