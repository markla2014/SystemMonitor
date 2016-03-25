package com.hyun.vo;

public class totalMasterOverviewInformation {
 private String version;
 private String mode;
 private String blockSize;
 private String Replication;
 private String startedDate;
 private String masterServer;
 private String tabletServers;
 private String CompileDate;
 public String systemSpaces;
 public String systemUsage;
 public String onlineSessions;
 public String getSystemSpaces() {
	return systemSpaces;
}
public void setSystemSpaces(String systemSpaces) {
	this.systemSpaces = systemSpaces;
}
public String getSystemUsage() {
	return systemUsage;
}
public void setSystemUsage(String systemUsage) {
	this.systemUsage = systemUsage;
}
public String getOnlineSessions() {
	return onlineSessions;
}
public void setOnlineSessions(String onlineSessions) {
	this.onlineSessions = onlineSessions;
}
public String getRunningSQL() {
	return RunningSQL;
}
public void setRunningSQL(String runningSQL) {
	RunningSQL = runningSQL;
}
public String RunningSQL;
public String getCompileDate() {
	return CompileDate;
}
public void setCompileDate(String compileDate) {
	CompileDate = compileDate;
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
	return Replication;
}
public void setReplication(String replication) {
	Replication = replication;
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
  
}
