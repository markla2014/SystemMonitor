package com.hyun.jdbc;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hyun.dao.BaseDao;
import com.hyun.dao.CommandDao;

public class springTest {

	private CommandDao testDao;

	public springTest() {
		// TODO Auto-generated constructor stub
	}

	@Before
	public void getDao(){
		   ClassPathXmlApplicationContext beanFactory = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");  
	        //BaseDao BaseDao = (BaseDao) beanFactory.getBean("baseDao");  
	        testDao=(CommandDao)beanFactory.getBean("commandDao");
	}
	@Test
	public void test(){
		System.out.println(testDao.runCommnad());
	}
}
