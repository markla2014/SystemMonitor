package com.hyun.jdbc;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hyun.dao.CommandDao;
import com.hyun.exception.GwtException;
import com.hyun.service.impl.CommandServiceImpl;

public class springTest {
      @Test
	public void cacheTest() {
		// TODO Auto-generated constructor stub
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");  
		CommandServiceImpl service = (CommandServiceImpl) context.getBean("commandServiceImpl");  
		CommandDao dao=(CommandDao)context.getBean("commandDao");
		try {
			dao.getJdbcConnection("system","CHANGEME", "192.168.0.13");
		} catch (GwtException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
    }  
 
	}

