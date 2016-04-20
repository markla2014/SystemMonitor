package com.hyun.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyun.dao.CommandDao;
import com.hyun.service.CommandService;
@Service
public class CommandServiceImpl implements CommandService {

	@Autowired
	public CommandDao dao;
	
	  public int getTotalRows(){
		  return dao.runCommnad();
	  }

}
