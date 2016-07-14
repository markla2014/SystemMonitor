package com.hyun.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudwave.jdbc.CloudConnection;
import com.hyun.dao.SessionDao;
import com.hyun.exception.GwtException;
import com.hyun.service.SessionListService;

@Service
public class SessionListServiceImpl implements SessionListService {
    @Autowired
    private SessionDao dao;
    Logger logge = Logger.getLogger(SessionListServiceImpl.class);

    @Override
    public String[][] getSessionList() {
        // TODO Auto-generated method stub
        String[][] temp = null;
        try {
            CloudConnection conn = dao.CreateConnection();
            temp = dao.getOnlineSessions(conn);
            conn.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logge.error(e.getMessage());
        }
        return temp;
    }

    @Override
    public String[][] getRunningSQL() {
        String[][] temp = null;
        try {
            CloudConnection conn = dao.CreateConnection();
            temp = dao.getRunningSQL(dao.getConnection());
            conn.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logge.error(e.getMessage());
        }
        return temp;
    }

}
