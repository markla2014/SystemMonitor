package com.hyun.service;


public interface UserService {

	public String[][] getUsertable();
	public String createUser(String username,String password);
	public String changUserPassword(String username,String newPassword,String oldPassword);
	public String dropUser(String username);
	public boolean checkUsername(String username);
	public boolean restartServer(boolean all);
	
}
