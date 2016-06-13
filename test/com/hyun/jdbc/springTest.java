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
			 long id=System.currentTimeMillis();
			String[][] test=dao.getTableInfor("SMC4A","SA_COMPLEMENT", 1, id);
			String[][] test1=dao.getTableInfor("SMC4A","SA_COMPLEMENT", 2,id);
			long id1=System.currentTimeMillis();
			String[][] test2=dao.getTableInfor("ETLTEST","CUSTOMER", 1, id1);
			String[][] test4=dao.getTableInfor("ETLTEST","CUSTOMER", 2, id1);
			String[][] test5=dao.getTableInfor("SMC4A","SA_COMPLEMENT", 3,id);
			for(String[] i:test4){
				for(String j:i){
					System.out.print(" "+j+" ");
				}
				System.out.println();
			}
			
			for(String[] i:test5){
				for(String j:i){
					System.out.print(" "+j+" ");
				}
				System.out.println();
			}
		} catch (GwtException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
    }  
 
	}

