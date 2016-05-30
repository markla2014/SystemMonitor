package com.hyun.common;

import java.util.LinkedList;

public class indexPager {
	private long totalRecord;
	private LinkedList<pageNumber> pageCount=new LinkedList<pageNumber>();
	private int interval;
	private long totalpages;
	private int currentPage;
	
	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public long getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(long l) {
		this.totalRecord = l;
	}

	public LinkedList<pageNumber> getPageCount() {
	 for(int i=0;i<this.getTotalpages();i++){
		 pageNumber temp=new pageNumber();
		 temp.setStart(i*this.getInterval());
		 temp.setEnd((i*this.getInterval())+this.getInterval());
	    pageCount.add(temp);
	 }
		return pageCount;
	}

	public void setPageCount(LinkedList<pageNumber> pageCount) {
		this.pageCount = pageCount;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public long getTotalpages() {
     long tem=this.totalRecord%this.interval;
     long tm1=this.totalRecord/this.interval;
        if(tem!=0){
        	tm1++;
        }
		return tm1;
		
	}

	public void setTotalpages(long totalpages) {
		this.totalpages = totalpages;
	}

	

}
