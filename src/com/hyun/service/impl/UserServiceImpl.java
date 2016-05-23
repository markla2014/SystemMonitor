package com.hyun.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyun.common.ServerMonitorConstant;
import com.hyun.dao.UserDao;
import com.hyun.service.UserService;
@Service
public class UserServiceImpl implements UserService{
@Autowired
private UserDao dao;
private String[] usernameList;

public String[] getUsernameList() {
	return usernameList;
}

public void setUsernameList(String[] usernameList) {
	this.usernameList = usernameList;
}
private static Logger logger=Logger.getLogger(UserServiceImpl.class);
	@Override
	public String[][] getUsertable()  {
		
		try{
		// TODO Auto-generated method stub
	   String[] temp=dao.getUserNameList("", dao.getConnection());
	    this.setUsernameList(temp);
	   String[][] returnValue=new String[temp.length+1][5];
	     String[] temp1={"用户名","默认数据库","拥有库","指派库","指派所属"};
	     returnValue[0]=temp1;
	   for(int i=0;i<temp.length;i++){
		   returnValue[i+1][0]=temp[i];
		  temp1=dao.getUserPrivileges(dao.getConnection(), temp[i]).split(",");
		  for(int j=0;j<temp1.length;j++){
			  String[] temp2=temp1[j].split("=");
		  switch(temp2[0]){
		  case "DEFAULT":
			  if(temp2.length>1){
				  returnValue[i+1][1]=temp2[1];
			  }else
				  returnValue[i+1][1]="";
			  break;
		  case "OWNED":
			  if(temp2.length>1){
				  returnValue[i+1][2]=temp2[1];
			  }else
				  returnValue[i+1][2]="";
			  break;
		  case "GRANTED":
			  if(temp2.length>1){
				  returnValue[i+1][3]=temp2[1];
			  }else
				  returnValue[i+1][3]="";

			  break;
		  case "GRANTED_TABLES":
			  if(temp2.length>1){
				  returnValue[i+1][4]=temp2[1];
			  }else
				  returnValue[i+1][4]="";

			  break;
		  }
		  }
	   }
	     return returnValue;
		}catch(Exception e){
		  String[][] temp=new String[1][1];
		  temp[0][0]=e.getMessage();
		  
		  return temp;
		}
	}

	@Override
	public String createUser(String username, String password) {
		// TODO Auto-generated method stub
	    try {
	    	//
			if(this.checkUsername(username)){
				int test=dao.createUser(username, password);
				if(test>-1)
			        return "成功建立用户";
				else
					return "建立失败";
			}else{
				return "该用户已经存在";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
		   return e.getMessage();
		}
		
	}

	@Override
	public String changUserPassword(String username, String newPassword,
			String oldPassword) {
		// TODO Auto-generated method stub
		try{
			if(dao.changePassword(dao.getConnection(), username, newPassword, oldPassword)>-1){
				return "修改密码成功";
			}else 
				return "修改密码失败";
		}catch(Exception e){
			return e.getMessage();
		}
	}

	@Override
	public String dropUser(String username) {
		// TODO Auto-generated method stub
	try{
		if(dao.deleteUser(username)>-1)
			return "成功删除";
		else
			return "删除失败";
	}catch(Exception e){
		return e.getMessage();
	}
	}
    
	public boolean checkUsername(String username){
		String[] temp=this.getUsernameList();
	for(int i=0;i<temp.length;i++){
		if(username.trim().equals(temp[i])){
		   return false;
		}
	}
	   return true;
	}
	public boolean restartServer(boolean all){
		try{
		if(all==true)
		return dao.doRestartServer(dao.getConnection(), "ALL");
		else
		return dao.doRestartServer(dao.getConnection(), "");
		}catch(Exception e){
			logger.error(e.getStackTrace());
			return false;
		}
	}
}
