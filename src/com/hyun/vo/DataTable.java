package com.hyun.vo;

import java.util.LinkedList;

public class DataTable {

    private String table;
    private String schema;
    private LinkedList<dataInfo> cols;

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public LinkedList<dataInfo> getCols() {
        return cols;
    }

    public void setCols(LinkedList<dataInfo> cols) {
        this.cols = cols;
    }

}
