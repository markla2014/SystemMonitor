package com.hyun.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudwave.jdbc.CloudConnection;
import com.hyun.dao.MainInformationDao;
import com.hyun.exception.GwtException;
import com.hyun.service.ServerListService;

@Service
public class ServerListServiceImpl implements ServerListService {

    @Autowired
    private MainInformationDao dao;
    Logger logge = Logger.getLogger(ServerListServiceImpl.class);

    @Override
    public String[][] getServerlist() {
        // TODO Auto-generated method stub
        String[][] temp = null;
        try {
            CloudConnection conn = dao.CreateConnection();
            temp = dao.getServerList(conn);
            conn.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logge.error(e.getMessage());
        }
        return temp;
    }

}
