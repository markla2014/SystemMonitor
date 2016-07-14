package com.hyun.vo;

public class dataInfo implements Comparable<dataInfo> {
    private String colName;
    private String datatype;
    private int length;
    private String inital;
    private int isNull;
    private int isIndex;
    private int iscover;
    private int isPrimary;
    private int keySquence;
    private int isUnique;
    private int isText;

    public int getIsText() {
        return isText;
    }

    public void setIsText(int isText) {
        this.isText = isText;
    }

    public int getIsUnique() {
        return isUnique;
    }

    public void setIsUnique(int isUnique) {
        this.isUnique = isUnique;
    }

    public dataInfo() {
        keySquence = 0;
        // TODO Auto-generated constructor stub
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getInital() {
        return inital;
    }

    public void setInital(String inital) {
        this.inital = inital;
    }

    public int getIsNull() {
        return isNull;
    }

    public void setIsNull(int isNull) {
        this.isNull = isNull;
    }

    public int getIsIndex() {
        return isIndex;
    }

    public void setIsIndex(int isIndex) {
        this.isIndex = isIndex;
    }

    public int getIscover() {
        return iscover;
    }

    public void setIscover(int iscover) {
        this.iscover = iscover;
    }

    public int getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(int isPrimary) {
        this.isPrimary = isPrimary;
    }

    public int getKeySquence() {
        return keySquence;
    }

    public void setKeySquence(int keySquence) {
        this.keySquence = keySquence;
    }

    public int compareTo(dataInfo arg0) {
        return (this.getKeySquence() + "").compareTo(arg0.getKeySquence() + "");
    }
}
