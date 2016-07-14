package com.hyun.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyun.dao.MemoryDao;
import com.hyun.exception.GwtException;
import com.hyun.service.MemoryService;
import com.hyun.vo.totalCPUpercent;

@Service
public class MemoryServiceImpl implements MemoryService {
    @Autowired
    private MemoryDao dao;
    private LinkedList<totalCPUpercent> MemoryInformation;

    public LinkedList<totalCPUpercent> getMemoryInformation() {
        return MemoryInformation;
    }

    public void setMemoryInformation(LinkedList<totalCPUpercent> memoryInformation) {
        MemoryInformation = memoryInformation;
    }

    @Override
    public String[][] getTotalMemoryInformation() {
        String[][] temp = null;
        LinkedList<totalCPUpercent> memoryTemp = new LinkedList<totalCPUpercent>();
        String[] temp3 = { "提交内存", "已用内存" };
        try {
            temp = dao.getMemorySize(dao.getConnection());
        } catch (Exception e) {

        }
        if (temp != null) {
            String[][] value = new String[temp.length][temp[0].length - 2];
            for (int i = 0; i < temp.length; i++) {
                totalCPUpercent temp1 = new totalCPUpercent();
                LinkedList<Double> temp2 = new LinkedList<Double>();
                for (int j = 0; j < temp[i].length; j++) {
                    if (j == (temp[i].length - 1)) {
                        value[i][j - 2] = temp[i][j];
                    } else if (j < 5) {
                        value[i][j] = temp[i][j];
                    } else {
                        if (i != 0) {
                            temp2.add(Double.parseDouble(temp[i][j]));
                        }
                    }
                }
                if (i != 0) {
                    temp1.setCurName(temp3);
                    temp1.setDataArray(temp2);
                    temp1.setObjName(temp[i][0]);
                    Date date = new Date();
                    DateFormat format = new SimpleDateFormat("HH:mm:ss");
                    temp1.setTime(format.format(date));
                    if (StringUtils.indexOf(temp[i][2], "Standby", 0) == -1) {
                        memoryTemp.add(temp1);
                    }
                }
            }
            this.setMemoryInformation(memoryTemp);
            return value;
        }
        return new String[1][1];
    }

}
