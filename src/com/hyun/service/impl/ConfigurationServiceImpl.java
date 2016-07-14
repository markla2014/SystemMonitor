package com.hyun.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudwave.jdbc.CloudConnection;
import com.hyun.common.ServerMonitorConstant;
import com.hyun.dao.ConfigureDao;
import com.hyun.exception.GwtException;
import com.hyun.service.ConfigurationSerice;
import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;

@Service
public class ConfigurationServiceImpl implements ConfigurationSerice {
    @Autowired
    private ConfigureDao dao;
    private LinkedList<String[]> DataNode;
    private Logger logge = Logger.getLogger(ConfigurationServiceImpl.class);
    private final static String[] CLOUDWAVE_CONFIG = { "cloudwave.root.path", "dfs.replication", "dfs.blocksize", "cloudwave.master.servers", "cloudwave.tablet.servers", "cloudwave.masterserver.rpc.port", "cloudwave.load.weights", "cloudwave.masterserver.task.maximum.threadpool.size", "cloudwave.server.session.maximum.active.statements", "cloudwave.check.point.interval", "cloudwave.olap", "cloudwave.tx.log.file.maximum.count", "cloudwave.bfile.maximum.duration", "cloudwave.bfile.maintain.hours", "cloudwave.bfile.store", "cloudwave.full.text.analyzer", "cloudwave.server.exclude.schemas" };

    public LinkedList<String[]> getDataNode() {
        return DataNode;
    }

    public void setDataNode(LinkedList<String[]> dataNode) {
        DataNode = dataNode;
    }

    @Override
    public String[][] getDFSConfigure() {
        // TODO Auto-generated method stub
        String[] temp = null;
        try {
            CloudConnection conn = dao.CreateConnection();
            temp = dao.getDfsStatus(conn);
            conn.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logge.error(e.getMessage());
        }
        LinkedList<String[]> temp3 = new LinkedList<String[]>();
        String[] tag = { "节点名", "IP 地址", "运行状态", "硬盘容量", "使用容量", "使用比率", "剩余容量", "剩余比率" };
        temp3.add(tag);
        String[][] temp2 = new String[5][2];
        temp2[0][0] = "配置项";
        temp2[0][1] = "数据";
        int c = 0;
        for (String i : temp) {
            c++;
            String[] t = i.split("=");
            String value = (t.length > 1) ? t[1] : "";
            String col = t[0].substring(3);
            String reutrnValue = "";
            if ("Datanode".equals(col)) {
                String[] valueTemp = value.split("#");
                for (int j = 0; j < valueTemp.length; j++) {
                    if (valueTemp[j].charAt(0) == '.') {
                        valueTemp[j] = 0 + valueTemp[j];
                    }
                }
                temp3.add(valueTemp);
            } else {
                switch (col.trim()) {
                case "IsLocal":
                    reutrnValue = "是否本地设置";
                    temp2[1][0] = reutrnValue;
                    temp2[1][1] = value;
                    break;
                case "Schema":
                    reutrnValue = "DFS 模式";
                    temp2[2][0] = reutrnValue;
                    temp2[2][1] = value;
                    break;
                case "Replication":
                    reutrnValue = "DFS分布主机数";
                    temp2[3][0] = reutrnValue;
                    temp2[3][1] = value;
                    break;
                case "BlockSize":
                    reutrnValue = "块大小";
                    temp2[4][0] = reutrnValue;
                    temp2[4][1] = value;
                    break;
                }
                // temp2[c][0]=reutrnValue;
                //
                // temp2[c][1]=value;
            }

        }
        this.setDataNode(temp3);
        return temp2;
    }

    @Override
    public String[][] getBaseConfigure() {
        // TODO Auto-generated method stub
        String[] temp = null;
        try {
            CloudConnection conn = dao.CreateConnection();
            temp = dao.getConfigOptions(conn);
            conn.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logge.error(e.getMessage());
        }
        String[][] temp2 = new String[temp.length + 1][2];
        int c = 0;
        temp2[0][0] = "参数名";
        temp2[0][1] = "参数值";
        for (String i : temp) {
            c++;
            String[] t = i.split("=");
            String value = (t.length > 1) ? t[1] : "";
            String returnValue = "";
            switch (t[0].trim()) {
            case "Olap Mode":
                returnValue = "Olap模式";
                break;
            case "Master Servers":
                returnValue = "主节点";
                break;
            case "Tablet Servers":
                returnValue = "子节点";
                break;
            case "Deploy Mode":
                returnValue = "部署模式";
                break;
            case "Root Path":
                returnValue = "安装路径";
                break;
            case "Replication":
                returnValue = "数据块的复制数";
                break;
            case "Block Size":
                returnValue = "文件存储块大小";
                break;
            case "Node Weight":
                returnValue = "Node Weight";
                break;
            case "Master Pool Size":
                returnValue = "主节点的线程池";
                break;
            case "Tablet Pool Size":
                returnValue = "子节点的线程池";
                break;
            case "Max Sessions":
                returnValue = "最大连接数";
                break;
            case "Checkpoint Interval":
                returnValue = "checkpoint 数量";
                break;
            case "Fulltext Analyzer":
                returnValue = "全文索引解析器";
                break;
            case "Fulltext Top Search":
                returnValue = "全文索引的top数量";
                break;
            case "Bfile Duration":
                returnValue = "Bfile 文件保存时间";
                break;
            case "Exclude Schemas":
                returnValue = "Exclude模式";
                break;
            case "Zookeeper Uri":
                returnValue = "Zookeeper集群管理网址";
                break;
            case "Hadoop Uri":
                returnValue = "Hadoop集群管理网址";
                break;
            }
            temp2[c][0] = returnValue;
            temp2[c][1] = value;

        }
        return temp2;
    }

    @Override
    public String[][] getCloudwaveConfigure() {
        String[] temp = null;
        try {
            CloudConnection conn = dao.CreateConnection();
            temp = dao.getCloudwaveOptions(conn);
            conn.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logge.error(e.getMessage());
        }
        ArrayList<String[]> temp3 = new ArrayList<String[]>();
        temp3.add(new String[] { "参数名", "参数值" });
        for (String i : temp) {
            String[] temp2 = new String[2];
            String[] t = i.split("=");
            String value = (t.length > 1) ? t[1] : "";
            String returnValue = "";
            switch (t[0].trim()) {
            case "cloudwave.root.path":
                returnValue = "cloudwave 数据库的主目录";
                temp2[0] = returnValue;
                temp2[1] = value;
                temp3.add(temp2);
                break;
            case "dfs.replication":
                returnValue = "文件系统的备份数量";
                temp2[0] = returnValue;
                temp2[1] = value;
                temp3.add(temp2);
                break;
            case "dfs.blocksize":
                returnValue = "文件系统的块大小";
                temp2[0] = returnValue;
                temp2[1] = value;
                temp3.add(temp2);
                break;
            case "cloudwave.master.servers":
                returnValue = "master服务器地址设定";
                temp2[0] = returnValue;
                temp2[1] = value;
                temp3.add(temp2);
                break;
            case "cloudwave.tablet.servers":
                returnValue = "tablet服务器地址设定";
                temp2[0] = returnValue;
                temp2[1] = value;
                temp3.add(temp2);
                break;
            case "cloudwave.masterserver.rpc.port":
                returnValue = "cloudwave接口设定";
                temp2[0] = returnValue;
                temp2[1] = value;
                temp3.add(temp2);
                break;
            case "cloudwave.load.weights":
                returnValue = "服务器节点权重";
                temp2[0] = returnValue;
                temp2[1] = value;
                temp3.add(temp2);
                break;
            case "cloudwave.masterserver.task.maximum.threadpool.size":
                returnValue = "主服务器任务线程大小";
                temp2[0] = returnValue;
                temp2[1] = value;
                temp3.add(temp2);
                break;
            case "cloudwave.tabletserver.task.maximum.threadpool.size":
                returnValue = "小表服务器服务线程数";
                temp2[0] = returnValue;
                temp2[1] = value;
                temp3.add(temp2);
                break;
            case "cloudwave.server.session.maximum.active.statements":
                returnValue = "每个会话同时开启的最大句柄数";
                temp2[0] = returnValue;
                temp2[1] = value;
                temp3.add(temp2);
                break;
            case "cloudwave.check.point.interval":
                returnValue = "落盘时间间隔";
                temp2[0] = returnValue;
                temp2[1] = value;
                temp3.add(temp2);
                break;
            case "cloudwave.olap":
                returnValue = "Olap 模式";
                temp2[0] = returnValue;
                temp2[1] = value;
                temp3.add(temp2);
                break;
            case "cloudwave.masterserver.maximum.memory":
                returnValue = "主控服务器的最大内存";
                temp2[0] = returnValue;
                temp2[1] = value;
                temp3.add(temp2);
                break;
            case "cloudwave.tabletserver.maximum.memory":
                returnValue = "小表服务器的最大内存";
                temp2[0] = returnValue;
                temp2[1] = value;
                temp3.add(temp2);
                break;
            case "cloudwave.server.maximum.active.sessions":
                returnValue = "同时处于连接状态的 最大会话数";
                temp2[0] = returnValue;
                temp2[1] = value;
                temp3.add(temp2);
                break;
            case "cloudwave.bfile.maximum.duration":
                returnValue = "BFILE文件的存储周期";
                temp2[0] = returnValue;
                temp2[1] = value;
                temp3.add(temp2);
                break;
            case "cloudwave.bfile.local.assignments":
                returnValue = "BFILE分片的指定方式分配";
                temp2[0] = returnValue;
                temp2[1] = value;
                temp3.add(temp2);
                break;
            case "cloudwave.bfile.maintain.hours":
                returnValue = "BFILE文件自动维护的时间";
                temp2[0] = returnValue;
                temp2[1] = value;
                temp3.add(temp2);
                break;
            case "cloudwave.bfile.store":
                returnValue = "Bfile文件存储";
                temp2[0] = returnValue;
                temp2[1] = value;
                temp3.add(temp2);
                break;
            case "cloudwave.full.text.analyzer":
                returnValue = "文本分析器设定";
                temp2[0] = returnValue;
                temp2[1] = value;
                temp3.add(temp2);
                break;
            case "cloudwave.full.text.create.sync":
                returnValue = "全文索引创建是否同步";
                temp2[0] = returnValue;
                temp2[1] = value;
                temp3.add(temp2);
                break;
            case "cloudwave.server.exclude.schemas":
                returnValue = "启动设定不加载的模式";
                temp2[0] = returnValue;
                temp2[1] = value;
                temp3.add(temp2);
                break;
            case "cloudwave.tabletserver.execute.timeout":
                returnValue = "tabletServer 运行过期时间";
                temp2[0] = returnValue;
                temp2[1] = value;
                temp3.add(temp2);
                break;
            case "cloudwave.zookeeper.ensemble":
                returnValue = "数据库集群地址";
                temp2[0] = returnValue;
                temp2[1] = value;
                temp3.add(temp2);
                break;
            case "cloudwave.bfile.local.root.path":
                returnValue = "bfile存储位置";
                temp2[0] = returnValue;
                temp2[1] = value;
                temp3.add(temp2);
                break;
            // default:
            // returnValue=t[0].trim();
            // break;
            }

        }
        return temp3.toArray(new String[0][]);
    }
}
