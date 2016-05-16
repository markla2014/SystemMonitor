package com.hyun.service;

public interface ConfigurationSerice {

	public String[][] getDFSConfigure();
	public String[][] getBaseConfigure();
	public String restartServer(String target);
	
}
