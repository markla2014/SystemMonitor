package com.hyun.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyun.dao.SessionDao;
import com.hyun.exception.GwtException;
import com.hyun.service.SessionListService;
@Service
public class SessionListServiceImpl implements SessionListService {
  @Autowired
  private SessionDao dao;
	@Override
	public String[][] getSessionList() {
		// TODO Auto-generated method stub
		String[][] temp=null;
		try {
			temp=dao.getOnlineSessions(dao.getConnection());
		} catch (GwtException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return temp;
	}

}
