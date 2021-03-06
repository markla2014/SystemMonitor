package com.hyun.service.impl;

import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.cloudwave.jdbc.CloudConnection;
import com.cloudwave.jdbc.CloudResultSet;
import com.hyun.dao.QueryDao;
import com.hyun.exception.GwtException;
import com.hyun.service.QueryService;

@Service
public class QueryServiceImpl implements QueryService {
    @Autowired
    private QueryDao dao;
    private static Logger logger = Logger.getLogger(QueryServiceImpl.class);
    private String[] checkedSchemaList;

    public String[] getCheckedSchemaList() {
        return checkedSchemaList;
    }

    public void setCheckedSchemaList(String[] checkedSchemaList) {
        this.checkedSchemaList = checkedSchemaList;
    }

    public QueryServiceImpl() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public String[] getSchema() {
        // TODO Auto-generated method stub

        try {
            CloudConnection conn = dao.CreateConnection();
            this.setCheckedSchemaList(dao.getSchemaNameList(conn, "default"));
            conn.close();
            return this.getCheckedSchemaList();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage());
        }
        return null;
    }

    @Override
    public String[][] getTables(String schema) {
        // TODO Auto-generated method stub
        String[][] temp = null;
        try {
            CloudConnection conn = dao.CreateConnection();
            temp = dao.getTableNameList(conn, schema);
            conn.close();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage());
        }
        return temp;
    }

    @Override
    public String[][] getView(String schema) {
        // TODO Auto-generated method stub
        try {
            CloudConnection conn = dao.CreateConnection();
            String[][] thisTemp = dao.getViewList(conn, schema);
            conn.close();
            return thisTemp;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage());
            String[][] error = new String[1][1];
            error[0][0] = e.getMessage();
            return error;
        }
    }

    @Override
    public String[][] getTableColumn(String schema, String table) {
        // TODO Auto-generated method stub
        try {
            CloudConnection conn = dao.CreateConnection();
            String[][] thisTemp = dao.getTableColumns(conn, schema, table);
            conn.close();
            return thisTemp;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e.getStackTrace());
            String[][] errorRetrun = new String[1][1];
            errorRetrun[0][0] = e.getMessage();
            return errorRetrun;
        }
    }

    @Override
    public String[][] getTableDistriution(String schema, String table) {
        try {
            dao.setTemplateResultCont(new LinkedList<String[][]>());

            String[][] thisTemp = dao.getTableDistribution(dao.getConnection(), schema, table);
            return thisTemp;
        } catch (Exception e) {
            if (e.getMessage().indexOf("connection") != 0) {
                dao.getConnection();
                CloudConnection conn = dao.CreateConnection();
                dao.setConnection(conn);
                return getTableDistriution(schema, table);
            } else {
                logger.error(e.getStackTrace());
                String[][] errorRetrun = new String[1][1];
                errorRetrun[0][0] = e.getMessage();
                return errorRetrun;
            }
        }
    }

    @Async
    public String[][] getTableDistributionByPage(String schema, String table, int pagenum) {
        try {
            String[][] thisTemp = dao.getTableDistributionRecord(dao.getConnection(), schema, table, pagenum);
            return thisTemp;
        } catch (Exception e) {
            if (e.getMessage().indexOf("connection") != 0) {
                dao.getConnection();
                CloudConnection conn = dao.CreateConnection();
                dao.setConnection(conn);
                return getTableDistriution(schema, table);
            } else {
                logger.error(e.getStackTrace());
                String[][] errorRetrun = new String[1][1];
                errorRetrun[0][0] = e.getMessage();
                return errorRetrun;
            }
        }
    }

    @Override
    public long getRowCount() {

        return dao.getRowCount();
    }

    public String[][] getViewDefination(String schema, String view) {
        try {
            CloudConnection conn = dao.CreateConnection();
            String[][] thisTemp = dao.getViewDefinition(conn, schema, view);
            conn.close();
            return thisTemp;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            String[][] temp = new String[1][1];
            temp[0][0] = e.getMessage();
            logger.error(e.getMessage());
            return temp;
        }
    }

    public String[] checkTableName(String schema) {
        try {
            CloudConnection conn = dao.CreateConnection();
            String[] thisTemp = dao.checkTableName(conn, schema);
            conn.close();
            return thisTemp;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            String[] temp = new String[1];
            temp[0] = e.getMessage();
            logger.error(e.getMessage());
            return temp;
        }

    }

    @Override
    public String[] getUsers() {

        try {
            CloudConnection conn = dao.CreateConnection();
            String[] thisTemp = dao.getUserNameList(conn);
            return thisTemp;
        } catch (GwtException e) {
            // TODO Auto-generated catch block
            logger.error(e.getStackTrace());
            String[] error = { e.getMessage() };
            return error;
        }
    }
}
