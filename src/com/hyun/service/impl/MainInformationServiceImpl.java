package com.hyun.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyun.dao.MainInformationDao;
import com.hyun.exception.GwtException;
import com.hyun.service.MainInformationService;
import com.hyun.vo.totalMasterOverviewInformation;

@Service
public class MainInformationServiceImpl implements MainInformationService {
	@Autowired
	private MainInformationDao dao;

	
	@Override
	public totalMasterOverviewInformation getMasterInfroamtion() {
		
		totalMasterOverviewInformation returnValue=new totalMasterOverviewInformation();
		if(dao.getConnection()!=null){
	        try {
	        	String[] returnTemp=dao.getSystemOverview(dao.getConnection());
	        	for(String i:returnTemp){
	        		  String[] temp=i.split("=");
	        		  switch(temp[0]){
	        		  case "Cloudwave.Version":
	        			  returnValue.setVersion(temp[1]);
	        			  break;
	        		  case "Compile.Date":
	        			  returnValue.setCompileDate(temp[1]);
	        			  break;
	        		  case "Deploy.Mode":
	        			  returnValue.setMode(temp[1]);
	        			  break;
	        		  case "Data.Blocksize":
	        			  returnValue.setBlockSize(temp[1]);
	        			  break;
	        		  case "Data.Replication":
	        			  returnValue.setReplication(temp[1]);
	        			  break;
	        		  case "Started.Date":
	        			  returnValue.setStartedDate(temp[1]);
	        			  break;
	        		  case "Master.Nodes":
	        			  returnValue.setMasterServer(temp[1]);
	        			  break;
	        		  case "Tablet.Nodes":
	        			  returnValue.setStartedDate(temp[1]);
	        			  break;
	        		  case "System.Spaces":
	        			  returnValue.setSystemSpaces(temp[1]);
	        			  break;
	        		  case "System.Usages":
	        			  returnValue.setSystemUsage(temp[1]);
	        			  break;
	        		  case "Online.Sessions":
	        			  returnValue.setOnlineSessions(temp[1]);
	        			  break;
	        		  case "Running.SQLs":
	        			  returnValue.setRunningSQL(temp[1]);
	        			  break;
	        		
	        		  }
	        	}
	        	
			} catch (GwtException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}
		return returnValue;	
	}


	@Override
	public String getMasterLogin(String username, String password) {
	    return dao.getJdbcConnection("system", "CHANGEME");
	}
}
