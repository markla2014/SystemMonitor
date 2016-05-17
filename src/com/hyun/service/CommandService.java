package com.hyun.service;

import java.io.OutputStream;

import com.cloudwave.jdbc.CloudConnection;
import com.cloudwave.jdbc.bfile.CloudBfile;
import com.hyun.vo.DataTable;


public interface CommandService {

  public int getTotalRows();
  public long getRowsCount(String schema,String table);
  public String[][] getTableDate(String schema,String table,int jumpPage);
  public long getPageCount();
  public int getCurrent();
  public String createSchema(String schema,String name);
  public String deleteSchema(String schema);
  public String deleteTable(String schema,String table);
  public String createTable(DataTable forntValue);
  public String[][] getViewData(String schema,String view,int jumpPage);
  public String deleteView(String schema,String view);
  public String getGernalQuery(String sql);
	public int getSreachQueryCount(String sql);
	  public String[][] getSreachQuery(String sql,int start,int end);
	public String[][] getBfileInterface(String schema);
	public String[][] getBfile(String schema,int start, int end);
	public CloudBfile getBFileDownlaod(long id) throws Exception;
}
