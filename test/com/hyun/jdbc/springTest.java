package com.hyun.jdbc;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hyun.service.impl.CommandServiceImpl;
import com.hyun.service.impl.MainInformationServiceImpl;

public class springTest {
      @Test
	public void cacheTest() {
		// TODO Auto-generated constructor stub
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");  
		CommandServiceImpl service = (CommandServiceImpl) context.getBean("commandServiceImpl");  
		MainInformationServiceImpl service12=(MainInformationServiceImpl)context.getBean("mainInformationServiceImpl");
		System.out.println(service.getRowsCount("SMC4C","VIEW_SA_AUDIT_TREASURY"));
		
		System.out.println("run 1");
		service.getViewData("SMC4C","VIEW_SA_AUDIT_TREASURY",1);
		System.out.println("run2");
		System.out.println(service.getRowsCount("SMC4C","VIEW_SA_AUDIT_TREASURY"));
		service.getViewData("SMC4C","VIEW_SA_AUDIT_TREASURY",1);
    }  
	}

