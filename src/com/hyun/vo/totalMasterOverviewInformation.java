package com.hyun.vo;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;

import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;

import org.apache.commons.lang.StringUtils;
import org.springframework.jca.cci.core.InteractionCallback;
public class totalMasterOverviewInformation {
	 public final static String USE_SPACE_SIGE="AllLoad";
	 public final static String FREE_SPACE_SIGE="AllRemain";
 private String version;
 private String mode;
 private String blockSize;
 private String replication;
 private String startedDate;
 private String masterServer;
 private String tabletServers;
 private String compileDate;
 public String systemSpaces;
 public String systemUsage;
 public String onlineSessions;
 public String runningSQL;
 public double minCPUse;
 public double avgCPUse;
 public double maxCPUse;
 public LinkedList<tableUseSpace> useSpace=new LinkedList<tableUseSpace>();
 public long diskUsedSpace;
 public long diskFreeSpace;

 
 public long getDiskUsedSpace() {
	return diskUsedSpace;
}
public void setDiskUsedSpace(long diskUsedSpace) {
	this.diskUsedSpace = diskUsedSpace;
}
public long getDiskFreeSpace() {
	return diskFreeSpace;
}
public void setDiskFreeSpace(long diskFreeSpace) {
	this.diskFreeSpace = diskFreeSpace;
}
public double getMinCPUse() {
	return minCPUse;
}
public void setMinCPUse(int minCPUse) {
	this.minCPUse = minCPUse;
}
public double getAvgCPUse() {
	return avgCPUse;
}
public void setAvgCPUse(int avgCPUse) {
	this.avgCPUse = avgCPUse;
}
public double getMaxCPUse() {
	return maxCPUse;
}
public void setMaxCPUse(int maxCPUse) {
	this.maxCPUse = maxCPUse;
}

public String getSystemSpaces() {
	return systemSpaces;
}
public void setSystemSpaces(String systemSpaces) {
	this.systemSpaces = systemSpaces;
}

public LinkedList<tableUseSpace>  getUseSpace() {
	return useSpace;
}
public void setUseSpace(String useSpace) {
	Hashtable<String,Long> temp=this.processSpaceInfor(useSpace);
     Iterator<String> il=temp.keySet().iterator();
   if(temp.containsKey(USE_SPACE_SIGE)){
	    this.diskUsedSpace=temp.get(USE_SPACE_SIGE);
	     if(this.getUseSpace().size()<1){
	    	 while(il.hasNext()){
	    	   String key=il.next();
	    	   if(!USE_SPACE_SIGE.equals(key)){
	    	tableUseSpace a=new tableUseSpace();
	    	a.setAddr(key);
	    	a.setUsedSpace(temp.get(key));
	    	this.useSpace.add(a);
	    	   }
	    	 }
	     }else{
	    	 LinkedList temp2=new LinkedList<tableUseSpace>();
    		 for(tableUseSpace i:this.getUseSpace()){
    			 tableUseSpace e=new tableUseSpace();
    			 e.setUsedSpace(temp.get(i.getAddr()));
    			 e.setAddr(i.getAddr());
    			 e.setFreeSpace(i.getFreeSpace());
    			 temp2.add(e);
    		 }
    		 this.useSpace=temp2;
    	 }
   }else{
	   this.diskFreeSpace=temp.get(FREE_SPACE_SIGE);
	   if(this.getUseSpace().size()<1){
	    	 while(il.hasNext()){
	    	   String key=il.next();
	    	   if(!USE_SPACE_SIGE.equals(key)){
	    	tableUseSpace a=new tableUseSpace();
	    	a.setAddr(key);
	    	a.setFreeSpace(temp.get(key));
	    	this.useSpace.add(a);
	    	   }
	    	 }
	     }else{
	    	 LinkedList temp2=new LinkedList<tableUseSpace>();
    		 for(tableUseSpace i:this.getUseSpace()){
    			 tableUseSpace e=new tableUseSpace();
    			 e.setFreeSpace(temp.get(i.getAddr()));
    			 e.setAddr(i.getAddr());
    			 e.setUsedSpace(i.getUsedSpace());
    			 temp2.add(e);
    		 }
    		 this.useSpace=temp2;
    	 }
	   
   }
}
public String getSystemUsage() {
	String returnValue=this.minCPUse+"%/";
	returnValue+=this.avgCPUse+"%/";
	returnValue+=this.maxCPUse+"%";
	return returnValue;
}
public void setSystemUsage(String systemUsage) {
	   
	this.systemUsage = systemUsage;
	 String[] UsageTemp=StringUtils.split(this.systemUsage," / ");
       this.maxCPUse=Double.parseDouble(StringUtils.substringBefore(UsageTemp[UsageTemp.length-1],"%")); 
       this.avgCPUse=Double.parseDouble(StringUtils.substringBefore(UsageTemp[UsageTemp.length-2],"%")); 
       this.minCPUse=Double.parseDouble(StringUtils.substringBefore(UsageTemp[UsageTemp.length-3],"%")); 
}
public String getOnlineSessions() {
	return onlineSessions;
}
public void setOnlineSessions(String onlineSessions) {
	this.onlineSessions = onlineSessions;
}
public String getRunningSQL() {
	return runningSQL;
}
public void setRunningSQL(String runningSQL) {
	this.runningSQL = runningSQL;
}

public String getCompileDate() {
	return compileDate;
}
public void setCompileDate(String compileDate) {
	this.compileDate = compileDate;
}
public String getVersion() {
	return version;
}
public void setVersion(String version) {
	this.version = version;
}
public String getMode() {
	return mode;
}
public void setMode(String mode) {
	this.mode = mode;
}
public String getBlockSize() {
	return blockSize;
}
public void setBlockSize(String blockSize) {
	this.blockSize = blockSize;
}
public String getReplication() {
	return replication;
}
public void setReplication(String replication) {
	this.replication = replication;
}
public String getStartedDate() {
	return startedDate;
}
public void setStartedDate(String startedDate) {
	this.startedDate = startedDate;
}
public String getMasterServer() {
	return masterServer;
}
public void setMasterServer(String masterServer) {
	this.masterServer = masterServer;
}
public String getTabletServers() {
	return tabletServers;
}
public void setTabletServers(String tabletServers) {
	this.tabletServers = tabletServers;
}
public Hashtable<String, Long> processSpaceInfor(String message){
	  Hashtable<String,Long> temp=new Hashtable<String,Long>();
        message=message.replace("#",":0,");
      String[] messageTemp=StringUtils.split(message,",");
       for(String i:messageTemp){
    	   String[] test2=StringUtils.split(i,":");
    	   temp.put(test2[0],Long.parseLong(test2[1]));
       }
       return temp;
}
}
