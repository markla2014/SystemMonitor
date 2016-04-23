package com.hyun.service;

public interface QueryService {

	
            public String[] getSchema();
              public String[][] getTables(String schema);
              public String[][] getView(String schema);
              public String[][] getTableColumn(String schema,String table);
              public long getRowCount();
			String[][] getTableDistriution(String schema, String table);
			public String[] getUsers();
}
