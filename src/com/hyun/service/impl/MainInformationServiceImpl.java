package com.hyun.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyun.dao.MainInformationDao;
import com.hyun.exception.GwtException;
import com.hyun.service.MainInformationService;
import com.hyun.vo.tableUseSpace;
import com.hyun.vo.totalCPUpercent;
import com.hyun.vo.totalMasterOverviewInformation;
import com.hyun.vo.diagram.totalDiskSpaceDiagram;

@Service
public class MainInformationServiceImpl implements MainInformationService {
	@Autowired
	private MainInformationDao dao;
	private static Logger logger=Logger.getLogger(MainInformationServiceImpl.class);
	
	@Override
	public totalMasterOverviewInformation getMasterInfroamtion() {
		
		totalMasterOverviewInformation returnValue=new totalMasterOverviewInformation();
		if(dao.getConnection()!=null){
	        try {
	        	String[] returnTemp=dao.getSystemOverview(dao.getConnection());
	        	for(String i:returnTemp){
	        		  String[] temp=i.split("=");
	        		     String value_temp="";
	        		     value_temp=(temp[1]==null)?"":temp[1];
	        		  switch(temp[0]){
	        		  case "Cloudwave.Version":
	        			  returnValue.setVersion(value_temp);
	        			  break;
	        		  case "Compile.Date":
	        			  returnValue.setCompileDate(value_temp);
	        			  break;
	        		  case "Deploy.Mode":
	        			  returnValue.setMode(value_temp);
	        			  break;
	        		  case "Data.Blocksize":
	        			  returnValue.setBlockSize(value_temp);
	        			  break;
	        		  case "Data.Replication":
	        			  returnValue.setReplication(value_temp);
	        			  break;
	        		  case "Started.Date":
	        			  returnValue.setStartedDate(value_temp);
	        			  break;
	        		  case "Master.Nodes":
	        			  returnValue.setMasterServer(value_temp);
	        			  break;
	        		  case "Tablet.Nodes":
	        			  returnValue.setTabletServers(value_temp);
	        			  break;
	        		  case "System.Spaces":
	        			  returnValue.setSystemSpaces(value_temp);
	        			  break;
	        		  case "System.Usages":
	        			  returnValue.setSystemUsage(value_temp);
	        			  break;
	        		  case "Online.Sessions":
	        			  returnValue.setOnlineSessions(value_temp);
	        			  break;
	        		  case "Running.SQLs":
	        			  returnValue.setRunningSQL(value_temp);
	        			  break;
	        			 default:
	        			returnValue.setUseSpace(value_temp);
	        				 break;
	        		  }
	        	}
	        	
			} catch (GwtException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error(e.getMessage());
			}		
		}
		return returnValue;
	}


	public String getMasterLogin(String username, String password,String ipaddress) {
	    try{
		return dao.getJdbcConnection(username, password,ipaddress);
	    }catch(GwtException e){
	    	return e.getMessage();
	    }
	}


	@Override
	public totalDiskSpaceDiagram perpareTotalDiskSpace(totalMasterOverviewInformation informationTemp) {
		// TODO Auto-generated method stub
	    totalDiskSpaceDiagram diagram=new totalDiskSpaceDiagram();
	     String displayName="硬盘空间比率图";
	     long[] spaceValue={informationTemp.getDiskUsedSpace(),informationTemp.getDiskFreeSpace()};
	       diagram.setObjName(displayName);
	       diagram.setValue(spaceValue);
	       diagram.setDispName(new String[]{"已用空间","未用空间"});
		return diagram;
	}


	@Override
	public totalCPUpercent perpareTotalCPU(
			totalMasterOverviewInformation informationTemp) {
		totalCPUpercent temp=new totalCPUpercent();
           LinkedList<Double> temp2=new LinkedList<Double>();
           temp2.add(informationTemp.getMaxCPUse());
           temp2.add(informationTemp.getMinCPUse());
           temp2.add(informationTemp.getAvgCPUse());
       temp.setDataArray(temp2);
		temp.setObjName("CPU占有率");
		String[] temp1={"maxCPU","minCPU","avgCPU"};
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("HH:mm:ss");
		 temp.setTime(format.format(date));
		temp.setCurName(temp1);
		return temp;
	}


	@Override
	public LinkedList<totalDiskSpaceDiagram> perpareTableDiskSpace(totalMasterOverviewInformation informationTemp) {
		// TODO Auto-generated method stub
		LinkedList<totalDiskSpaceDiagram> retrnValue=new LinkedList<totalDiskSpaceDiagram>();
	     LinkedList<tableUseSpace> temp=informationTemp.getUseSpace();
	    for(tableUseSpace i:temp){
	     totalDiskSpaceDiagram diagram=new totalDiskSpaceDiagram();
	     long[] spaceValue={i.getUsedSpace(),i.getFreeSpace()};
	       diagram.setObjName(i.getAddr());
	       diagram.setValue(spaceValue);
	       diagram.setDispName(new String[]{"已用空间","未用空间"});
	       retrnValue.add(diagram);
	    }
		return retrnValue;
	}

}
