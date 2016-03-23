package com.hyun.common;

import java.util.List;

public class Pager<T> {
  private int totalNum;
  private List<T> pageList;
public int getTotalNum() {
	return totalNum;
}
public void setTotalNum(int totalNum) {
	this.totalNum = totalNum;
}
public List<T> getPageList() {
	return pageList;
}
public void setPageList(List<T> pageList) {
	this.pageList = pageList;
}
  
}
