package com.hyun.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyun.dao.MainInformationDao;
import com.hyun.exception.GwtException;
import com.hyun.service.ServerListService;

@Service
public class ServerListServiceImpl implements ServerListService {

	@Autowired
	private MainInformationDao dao;

	@Override
	public String[][] getServerlist() {
		// TODO Auto-generated method stub
		String[][] temp = null;
		try {
	    temp=dao.getServerList(dao.getConnection());
		} catch (GwtException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return temp;
	}
	
	
}
