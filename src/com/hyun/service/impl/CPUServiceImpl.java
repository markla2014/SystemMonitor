package com.hyun.service.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyun.dao.CPUDao;
import com.hyun.exception.GwtException;
import com.hyun.service.CPUService;
import com.hyun.vo.totalCPUpercent;
import org.apache.commons.lang.StringUtils;

@Service
public class CPUServiceImpl implements CPUService{
       @Autowired
	private CPUDao dao;
 	  private static DecimalFormat  numberS2Format = new DecimalFormat("0.0000");

	@Override
	public LinkedList<totalCPUpercent> getCPUInformation() {
		// TODO Auto-generated method stub
		String[][] temp=null;
		try {
		temp=dao.getSystemUtilization(dao.getConnection());
		} catch (GwtException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LinkedList<totalCPUpercent> returnValue=new LinkedList<totalCPUpercent>();
		for(String[] i:temp){
		totalCPUpercent temp1=new totalCPUpercent();
        LinkedList<Double> temp2=new LinkedList<Double>();
        BigDecimal testValue=(new BigDecimal(i[i.length-2]).multiply(new BigDecimal(100))).setScale(4, BigDecimal.ROUND_HALF_UP);
        BigDecimal testprocess=(new BigDecimal(i[i.length-1]).multiply(new BigDecimal(100))).setScale(4, BigDecimal.ROUND_HALF_UP);
        double tempValue=testValue.doubleValue();
        double tempprocess=testprocess.doubleValue();
        temp2.add(tempValue);
        temp2.add(tempprocess);
        temp1.setDataArray(temp2);
		temp1.setObjName(i[0]);
		String[] temp3={"系统利用率","进程利用率"};
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("HH:mm:ss");
		 temp1.setTime(format.format(date));
		temp1.setCurName(temp3);
		if(StringUtils.indexOf(i[2],"Standby",0)==-1){
		returnValue.add(temp1);
		}
		}
		return returnValue;
	}
}
