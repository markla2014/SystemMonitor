package com.hyun.vo;

import java.util.LinkedList;

import com.cloudwave.jdbc.CloudConnection;
import com.cloudwave.jdbc.CloudResultSet;

public class CommandVo {

	private long rowCount;
	private String[] header;
	private LinkedList<String[][]> Commandrecorder=new LinkedList<String[][]>();
	private long totalPage;
    private int currentSize;
    private CloudResultSet currentSet;
    private CloudConnection connection;
    
	public CloudConnection getConnection() {
		return connection;
	}
	public void setConnection(CloudConnection connection) {
		this.connection = connection;
	}
	public CloudResultSet getCurrentSet() {
		return currentSet;
	}
	public void setCurrentSet(CloudResultSet currentSet) {
		this.currentSet = currentSet;
	}
	public long getRowCount() {
		return rowCount;
	}
	public void setRowCount(long rowCount) {
		this.rowCount = rowCount;
	}
	public String[] getHeader() {
		return header;
	}
	public void setHeader(String[] header) {
		this.header = header;
	}
	public LinkedList<String[][]> getCommandrecorder() {
		return Commandrecorder;
	}
	public void setCommandrecorder(String[][] comresult) {
		Commandrecorder.add(comresult);
	}
	public long getTotalPage() {
	  totalPage=((rowCount%20)==0)?(rowCount/20):((rowCount/20)+1);
		return totalPage;
	}
	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
	}
	public int getCurrentSize() {
		return this.getCommandrecorder().size();
	}
	public void setCurrentSize(int currentSize) {
		this.currentSize = currentSize;
	}
}
