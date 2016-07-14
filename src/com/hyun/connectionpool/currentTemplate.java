package com.hyun.connectionpool;

import java.sql.SQLException;

import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.JdbcTemplate;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

@Component
public class currentTemplate {
    private BasicDataSource datasource;
    private JdbcTemplate template;

    public BasicDataSource getDatasource() {
        return datasource;
    }

    public void setDatasource(BasicDataSource datasource) {
        this.datasource = datasource;
    }

    public JdbcTemplate getTemplate() {
        template = new JdbcTemplate();
        template.setDataSource(this.datasource);
        template.setLazyInit(true);
        return template;
    }

    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

}
