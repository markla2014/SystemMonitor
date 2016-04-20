package com.hyun.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.hyun.connectionpool.currentTemplate;
@Repository
public class CommandDao extends BaseDao {
   
	@Autowired
	private currentTemplate template;
      public int runCommnad(){
    	return template.getTemplate().queryForObject("select count(*) from ITEST.ITEST ",Integer.class); 
      }
}
