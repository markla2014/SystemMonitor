package com.hyun.service.impl;

import java.sql.SQLException;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyun.common.ServerMonitorConstant;
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
		String[][] temp2=new String[5][2];
		 temp2[0][0]="配置项";
		 temp2[0][1]="数据";
		 int c=0;
		for(String i:temp){
			c++;
			String[] t=i.split("=");
		    String value=(t.length>1)?t[1]:"";
		    String col=t[0].substring(3);
		    String reutrnValue="";
		    if("Datanode".equals(col)){
			    String[] valueTemp=value.split("#");
			    for(int j=0;j<valueTemp.length;j++){
			        if(valueTemp[j].charAt(0)=='.'){
			        	 valueTemp[j]=0+valueTemp[j];
			        }
			    }
			     temp3.add(valueTemp);
		    }else{
		    	switch(col.trim()){
		    	case "IsLocal":
		    		reutrnValue="是否本地设置";
		    		temp2[1][0]=reutrnValue;
		    		temp2[1][1]=value;
		    		break;
		    	case "Schema":
		    	  reutrnValue="DFS 模式";
		    	  temp2[2][0]=reutrnValue;
		    		temp2[2][1]=value;
		    		break;
		    	case "Replication":
		    		reutrnValue="DFS分布主机数";
		    		  temp2[3][0]=reutrnValue;
			    		temp2[3][1]=value;
		    		break;
		    	case "BlockSize":
		    		reutrnValue="块大小";
		    		  temp2[4][0]=reutrnValue;
			    		temp2[4][1]=value;
		    		break;
		    	}
//		    	temp2[c][0]=reutrnValue;
//		     
//				temp2[c][1]=value;
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
		 temp2[0][0]="参数名";
		 temp2[0][1]="参数值";
		for(String i:temp){
			c++;
			String[] t=i.split("=");
			 String value=(t.length>1)?t[1]:"";
			 String returnValue="";
			 switch (t[0].trim()){
			 case "Olap Mode":
			    returnValue="Olap模式";
			    break;
			 case "Master Servers":
				    returnValue="主节点";
				    break;
			 case "Tablet Servers":
				    returnValue="子节点";
				    break;
			 case "Deploy Mode":
				    returnValue="部署模式";
				    break;
			 case "Root Path":
				    returnValue="安装路径";
				    break;
			 case "Replication":
				    returnValue="数据块的复制数";
				    break;
			 case "Block Size":
				    returnValue="文件存储块大小";
				    break;
			 case "Node Weight":
				    returnValue="Node Weight";
				    break;
			 case "Master Pool Size":
				    returnValue="主节点的线程池";
				    break;
			 case "Tablet Pool Size":
				    returnValue="子节点的线程池";
				    break;
			 case "Max Sessions":
				    returnValue="最大连接数";
				    break;
			 case "Checkpoint Interval":
				    returnValue="checkpoint 数量";
				    break;
			 case "Fulltext Analyzer":
				 returnValue="全文索引解析器";
				 break;
			 case "Fulltext Top Search":
			 returnValue="全文索引的top数量";
			 break;
			 case "Bfile Duration":
				 returnValue="Bfile 文件保存时间";
				 break;
			 case "Exclude Schemas":
				 returnValue="Exclude模式";
				 break;
			 case "Zookeeper Uri":
				 returnValue="Zookeeper集群管理网址";
				 break;
			 case "Hadoop Uri":
				 returnValue="Hadoop集群管理网址";
				 break;
			 }
			temp2[c][0]=returnValue;
			temp2[c][1]=value;
			
		}
		return temp2;
	}
	}

