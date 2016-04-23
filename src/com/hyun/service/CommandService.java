package com.hyun.service;


public interface CommandService {

  public int getTotalRows();
  public long getRowsCount(String schema,String table);
  public String[][] getTableDate(String schema,String table,int jumpPage);
  public long getPageCount();
  public int getCurrent();
  public String createSchema(String schema,String name);
  public String deleteSchema(String schema);
  public String deleteTable(String schema,String table);
}
