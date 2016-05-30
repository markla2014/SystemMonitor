package com.hyun.dao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import com.cloudwave.jdbc.CloudConnection;
import com.cloudwave.jdbc.CloudDatabaseMetaData;
import com.cloudwave.jdbc.CloudResultSet;
import com.hyun.common.ServerMonitorConstant;
import com.hyun.exception.GwtException;

@Repository
public class QueryDao extends BaseDao {
	private static final String AUTOKEY_COLUMN = "__CLOUDWAVE_AUTO_KEY__";
	private static final Logger logge = Logger.getLogger(QueryDao.class);
	private CloudResultSet currentResultSet;
	private String[] currenthead;
	
	public String[] getCurrenthead() {
		return currenthead;
	}

	public void setCurrenthead(String[] currenthead) {
		this.currenthead = currenthead;
	}

	public CloudResultSet getCurrentResultSet() {
		return currentResultSet;
	}

	public void setCurrentResultSet(CloudResultSet currentResultSet) {
		this.currentResultSet = currentResultSet;
	}
	private LinkedList<String[][]> templateResultCont=new LinkedList<String[][]>();
   public LinkedList<String[][]> getTemplateResultCont() {
		return templateResultCont;
	}

	public void setTemplateResultCont(LinkedList<String[][]> templateResultCont) {
		this.templateResultCont = templateResultCont;
	}
private long rowCount=0;
   
	public long getRowCount() {
	return rowCount;
}

public void setRowCount(long rowCount) {
	this.rowCount = rowCount;
}

	public QueryDao() {
		// TODO Auto-generated constructor stub
	}

	public String[] getSchemaNameList(Connection connection, String catalog)
			throws GwtException {
		try {

			CloudDatabaseMetaData meta = (CloudDatabaseMetaData) connection
					.getMetaData();
			CloudResultSet result = (CloudResultSet) meta.getSchemas();
			String[] schemas = new String[(int) result.getRecordCount()];
			for (int i = 0; i < schemas.length && result.next(); i++) {
				schemas[i] = result.getString(1);
			}
			result.close();
			Arrays.sort(schemas);
			return schemas;
		} catch (Throwable t) {
			throw new GwtException(t.getMessage());
		}
	}

	public String[][] getTableNameList(Connection connection, String schema)
			throws GwtException {
		try {
			CloudDatabaseMetaData meta = (CloudDatabaseMetaData) connection
					.getMetaData();
			CloudResultSet result = (CloudResultSet) meta.getTables("default",
					schema, null, new String[] { "TABLE" });
			double count = (int) result.getRecordCount();
			int col = (int) Math.ceil(count / 10);
			String[][] tables = new String[col][10];
			for (int i = 0; i < col; i++) {
				for (int j = 0; j < 10 && result.next(); j++) {
					tables[i][j] = result.getString(3);
				}
			}
			result.close();
			Arrays.sort(tables);
			return tables;
		} catch (Throwable t) {
			throw new GwtException(t.getMessage());
		}
	}
	public String[] checkTableName(Connection connection, String schema)
			throws GwtException {
		try {
			CloudDatabaseMetaData meta = (CloudDatabaseMetaData) connection
					.getMetaData();
			CloudResultSet result = (CloudResultSet) meta.getTables("default",
					schema, null, new String[] { "TABLE" });
			int count = (int) result.getRecordCount();
	         String[] temp=new String[count];
				for (int j = 0; j < count && result.next(); j++) {
				    temp[j]=result.getString(3);
				}
			result.close();
		  return temp;
		} catch (Throwable t) {
			throw new GwtException(t.getMessage());
		}
	}
	public String[][] getViewList(Connection connection, String schema)
			throws GwtException {
		try {
			CloudDatabaseMetaData meta = (CloudDatabaseMetaData) connection
					.getMetaData();
			CloudResultSet result = (CloudResultSet) meta.getTables("DEFAULT",
					schema, null, new String[] { "VIEW" });
			int count = (int) result.getRecordCount();
			this.setRowCount(count);
			int col = ServerMonitorConstant.getMath(count, 10);
			String[][] tables = new String[col][10];
			for (int i = 0; i < col; i++) {
				for (int j = 0; j < 10 && result.next(); j++) {
					tables[i][j] = result.getString(3);
				}
			}
			result.close();
			Arrays.sort(tables);
			return tables;
		} catch (Throwable t) {
			throw new GwtException(t.getMessage());
		}
	}

	public String[][] getTableColumns(CloudConnection connection,
			String schema, String table) throws GwtException {
		 ArrayList<String[]> array = new ArrayList<String[]>();
	        try {
	            CloudDatabaseMetaData meta = (CloudDatabaseMetaData) connection.getMetaData();
	            CloudResultSet result = (CloudResultSet) meta.getColumns("DEFAULT_", schema, table, null);
	            
	            long recordCount = result.getRecordCount();
	            this.setRowCount(recordCount);
	            int[] columns = new int[] { 4, 6, 7, 13, 18 };
	            String[] head = new String[] {
	                    "字段", "类型", "长度", "默认值", "可空"
	            };
	            String[] head2 = new String[head.length + 6];
	            System.arraycopy(head, 0, head2, 0, head.length);
	            head2[head.length] = "索引";
	            head2[head.length + 1] = "全文";
	            head2[head.length + 2] = "文本";
	            head2[head.length + 3] = "主键";
	            head2[head.length + 4]=   "唯一";
	            head2[head.length + 5] = "键序";
	            
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
	            ArrayList<String> textArray = new ArrayList<String>();
	            String textIndexString=meta.getTextIndexColumns(schema, table);
	            tokens = textIndexString.split(",");
	            for (String tok : tokens) {
	                if (tok.length() > 0) {
	                	textArray.add(tok);
	                }
	            }
	            ArrayList<String> uniqueArray = new ArrayList<String>();
	        	CloudResultSet uniqueSet=(CloudResultSet)meta.getUniqueKeys("DEFAULT_",schema,table);
	        	while(uniqueSet.next()){
	        		uniqueArray.add(uniqueSet.getString(4));
	        	}
	        	uniqueSet.close();
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
	            
	           // array.add(new String[] { String.valueOf(recordCount) });
	            array.add(head2);
	            for (int i = 0; i < rows.length; i ++) {
	                String[] record = rows[i]; 
	                if (record[0].equals(AUTOKEY_COLUMN)) {
	                    continue;
	                }
	                String[] record2 = new String[record.length + 6];
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
	                if(record[4]=="YES"){
	                	record[4]="NO";
	                }else{
	                	record[4]="YES";
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
	                int text=textArray.indexOf(record[0]);
	                if(text>=0){
	                	  record2[record.length + 2] = "YES";
	                } else {
	                    record2[record.length + 2] = "NO";
	                }
	                int unique=uniqueArray.indexOf(record[0]);
	                if(unique>=0){
	                	record2[record.length + 4] = "YES";
	                } else {
	                    record2[record.length + 4] = "NO";
	                }
	                int pki = pkColumns.indexOf(record[0]);
	                if (pki >= 0) {
	                    record2[record.length + 3] = "YES";
	                    record2[record.length + 5] = String.valueOf(pkSequences.get(pki));
	                } else {
	                    record2[record.length + 3] = "NO";
	                    record2[record.length + 5] = "-";
	                }
	                array.add(record2);
	            }
	            return array.toArray(new String[0][]);
		} catch (Throwable t) {
			throw new GwtException(t.getMessage());
		}
	}

	public String[][] getMoreData(ResultSet result, long count, int[] columns)
			throws GwtException {
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
				for (int i = 0; i < columns.length; i++) {
					String value = result.getString(baseIndex + columns[i]);
					record[i] = value != null ? value : "NULL";
				}
				recordCount++;
				records.add(record);
			}
			return records.toArray(new String[0][]);
		} catch (Throwable t) {
			throw new GwtException(t.getMessage());
		}
	}

	public String[][] getViewDefinition(CloudConnection connection,
			String schema, String table) throws GwtException {
		ArrayList<String[]> rows = new ArrayList<String[]>();
		try {
			DatabaseMetaData meta = connection.getMetaData();
			CloudResultSet crs = (CloudResultSet) meta.getTables(null, schema,
					table, new String[] { "VIEW" });
			//rows.add(new String[] { String.valueOf(crs.getRecordCount()) });
			rows.add(new String[] { "VIEW", "DESCRIPTION" });
			int[] columns = new int[] { 3, 5 };
			String[][] records = getMoreData(crs, crs.getRecordCount(), columns);
			for (String[] rec : records) {
				rows.add(rec);
			}
			crs.close();
			return rows.toArray(new String[0][]);
		} catch (Throwable t) {
			t.printStackTrace();
			throw new GwtException(t.getMessage());
		}
	}
	public String[][] getTableDistributionRecord(CloudConnection connection,
			String schema, String table,int pagenum) throws Exception{
		int currentsize=this.getTemplateResultCont().size();
	   if(currentsize>0){
		    if(currentsize>=pagenum){
		    	if(pagenum==1){
		    		this.setTemplateResultCont(new LinkedList<String[][]>());
		    		   String[][] tesult=getTableDistribution(connection,schema,table);
		    		   this.getTemplateResultCont().add(tesult);
		    		   return tesult;
		    	}else
		       return this.getTemplateResultCont().get((pagenum-1));
		    }
		    else{
		       String[][] tesult=readRowBypage(this.getCurrentResultSet(),20);
		        this.getTemplateResultCont().add(tesult);
		    	return tesult;
		    }
	   }else{
		   String[][] tesult=getTableDistribution(connection,schema,table);
		   this.getTemplateResultCont().add(tesult);
		   return tesult;
	   }
	}
	
	public String[][] getTableDistribution(CloudConnection connection,
			String schema, String table) throws GwtException {
		ArrayList<String[]> array = new ArrayList<String[]>();
		try {
			CloudDatabaseMetaData dbmeta = (CloudDatabaseMetaData) connection
					.getMetaData();
			CloudResultSet result = (CloudResultSet) dbmeta.getTablets(schema,
					table);
            this.setRowCount(result.getRecordCount());
            this.setCurrentResultSet(result);
			// array.add(new String[] { String.valueOf(recordCount) });
			String[] head = getHeadData(result);
			this.setCurrenthead(head);
			array.add(head);
            
			// CloudResultSet result1 =
			// (CloudResultSet)dbmeta.getTablets(schema, table);
			String[][] rows = getMoreData(result, 20);
			for (int i = 0; i < rows.length; i++) {
				array.add(rows[i]);
			}
		    String[][] resultTemp= array.toArray(new String[0][]);
			return resultTemp;
		} catch (Exception t) {
			  
			throw new GwtException(t.getMessage());
		    
		}

	}
	public String[][] readRowBypage(CloudResultSet result,int count){
		ArrayList<String[]> array = new ArrayList<String[]>();
		try {
			
		 array.add(this.getCurrenthead());
		String[][] rows = getMoreData(result, 20);
		this.setCurrentResultSet(result);
		for (int i = 0; i < rows.length; i++) {
			array.add(rows[i]);
		}
	    String[][] resultTemp= array.toArray(new String[0][]);
		return resultTemp;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logge.error(e.getMessage());
			String[][] errorTemp=new String[1][1];
			errorTemp[0][0]=e.getMessage();
		return 	errorTemp;
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
		for (int i = 0; i < header.length; i++) {
			header[i] = meta.getColumnName(baseIndex + i + 1);
		}
		return header;
	}

	public String[][] getMoreData(ResultSet result, long count)
			throws GwtException {
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
	   public String[] getUserNameList(CloudConnection connection) throws GwtException {
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
}