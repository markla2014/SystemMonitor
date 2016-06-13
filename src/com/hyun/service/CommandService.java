package com.hyun.service;

import java.io.OutputStream;

import com.cloudwave.jdbc.CloudConnection;
import com.cloudwave.jdbc.bfile.CloudBfile;
import com.hyun.vo.DataTable;


public interface CommandService {

  public long getTotalRows();
  public long getRowsCount(String schema,String table);
  public String createSchema(String schema,String name);
  public String deleteSchema(String schema);
  public String deleteTable(String schema,String table);
  public String createTable(DataTable forntValue);
  public String deleteView(String schema,String view);
  public String getGernalQuery(String sql);
	public int getSreachQueryCount(String sql);
	  public String[][] getSreachQuery(String sql,int start,int end);
	public String[][] getBfileInterface(String schema);
	public String[][] getBfile(String schema,int start, int end);
	public CloudBfile getBFileDownlaod(long id) throws Exception;
	String createView(String sql, String schema, String viewname);
	public String[][] withQuery(String sql);
	public String[][] withQueryPage(long commandId,int pageNumer);
}
