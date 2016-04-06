package com.hyun.service.impl;

import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyun.dao.ConfigureDao;
import com.hyun.exception.GwtException;
import com.hyun.service.ConfigurationSerice;
@Service
public class ConfigurationServiceImpl implements ConfigurationSerice{
  @Autowired
  private ConfigureDao dao;
  private LinkedList<String[]> DataNode;
  


	public LinkedList<String[]> getDataNode() {
	return DataNode;
}


	public void setDataNode(LinkedList<String[]> dataNode) {
		DataNode = dataNode;
	}


	@Override
	public String[][] getDFSConfigure() {
		// TODO Auto-generated method stub
		 String[] temp=null;
		try {
			temp=dao.getDfsStatus(dao.getConnection());
		} catch (GwtException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 LinkedList<String[]> temp3=new LinkedList<String[]>();
	      String[] tag={"节点名","IP 地址","运行状态","硬盘容量","使用容量","使用比率","剩余容量","剩余比率"};
	     temp3.add(tag);
		String[][] temp2=new String[temp.length+1][2];
		 temp2[0][0]="配置项";
		 temp2[0][1]="数据";
		 int c=0;
		for(String i:temp){
			c++;
			String[] t=i.split("=");
		    String value=(t.length>1)?t[1]:"";
		    String col=t[0].substring(3);
		    if("Datanode".equals(col)){
			    String[] valueTemp=value.split("#");
			     temp3.add(valueTemp);
		    }else{
		    	temp2[c][0]=col;
				temp2[c][1]=value;
		    }
			
		}
		this.setDataNode(temp3);
		return temp2;
	}

	@Override
	public String[][] getBaseConfigure() {
		// TODO Auto-generated method stub
		 String[] temp=null;
		 try {
			temp=dao.getConfigOptions(dao.getConnection());
		} catch (GwtException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 String[][] temp2=new String[temp.length+1][2];
		 int c=0;
		 temp2[0][0]="配置项";
		 temp2[0][1]="数据";
		for(String i:temp){
			c++;
			String[] t=i.split("=");
			 String value=(t.length>1)?t[1]:"";
			temp2[c][0]=t[0];
			temp2[c][1]=value;
			
		}
		return temp2;
	}

}
