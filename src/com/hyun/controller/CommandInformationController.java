package com.hyun.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cloudwave.jdbc.bfile.CloudBfile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyun.common.JasonCover;
import com.hyun.service.impl.CommandServiceImpl;
import com.hyun.service.impl.QueryServiceImpl;
import com.hyun.vo.DataTable;
import com.hyun.vo.dataInfo;

@Controller
@RequestMapping("/command")
public class CommandInformationController extends BaseController {
    @Autowired
    private CommandServiceImpl service;
    @Autowired
    private QueryServiceImpl service1;

    @RequestMapping("/getTableData.do")
    @ResponseBody
    public Map<String, String> getTableData(HttpServletRequest req, HttpServletResponse response) {
        String schema = req.getParameter("schema");
        String table = req.getParameter("table");
        int pageNum = Integer.parseInt(req.getParameter("pageNum"));
        Long id = Long.parseLong(req.getParameter("id"));
        String[][] result = service.getTableData(schema, table, pageNum, id);
        long totalcount = service.getTotalRows();
        long pageNumber = ((totalcount % 20) == 0) ? (totalcount / 20) : (totalcount / 20) + 1;
        Map<String, String> temp = new HashMap<String, String>();
        temp.put("info", JasonCover.toJason(result));
        temp.put("totalPage", JasonCover.toJason(service.getTotalRows()));
        temp.put("currentId", JasonCover.toJason(service.getCurrentCommandId()));
        temp.put("totalcount", JasonCover.toJason(totalcount));
        temp.put("pageNumber", JasonCover.toJason(pageNumber));
        temp.put("current", JasonCover.toJason(pageNum));
        return temp;
    }

    /**
     * view 和table 一致如有时间更改
     * 
     * @param req
     * @param response
     * @return
     */
    @RequestMapping("/getViewData.do")
    @ResponseBody
    public Map<String, String> getViewData(HttpServletRequest req, HttpServletResponse response) {

        String schema = req.getParameter("schema");
        String table = req.getParameter("table");
        int pageNum = Integer.parseInt(req.getParameter("pageNum"));
        long id = Long.parseLong(req.getParameter("id"));
        String[][] result = service.getTableData(schema, table, pageNum, id);
        Map<String, String> temp = new HashMap<String, String>();
        temp.put("info", JasonCover.toJason(result));
        temp.put("current", JasonCover.toJason(pageNum));
        return temp;
    }

    @RequestMapping(value = "/createTable.do", method = RequestMethod.POST)
    @ResponseBody
    public String create(@ModelAttribute("table") String table, @ModelAttribute("cols") String test, @ModelAttribute("schema") String schema) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> list = new LinkedList<Map<String, Object>>();
        try {
            list = objectMapper.readValue(test, List.class);
            DataTable temp = new DataTable();
            temp.setSchema(schema);
            temp.setTable(table);
            LinkedList<dataInfo> cols = new LinkedList<dataInfo>();
            for (Map<String, Object> t : list) {

                dataInfo col = new dataInfo();
                for (String a : t.keySet()) {
                    Field field = col.getClass().getDeclaredField(a);
                    String types = field.getType().toString();
                    field.setAccessible(true);
                    if (types.equals("class java.lang.Integer") || types.equals("int")) {
                        int value = 0;
                        if (t.get(a) != "") {
                            value = Integer.parseInt((String) t.get(a));
                        }
                        field.set(col, value);
                    } else {
                        field.set(col, t.get(a));
                    }
                }
                cols.add(col);
            }
            Collections.sort(cols);
            DataTable temp1 = new DataTable();
            temp1.setSchema(schema);
            temp1.setTable(table);
            temp1.setCols(cols);
            return JasonCover.toJason(service.createTable(temp1));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            return JasonCover.toJason(e.getMessage());
        }
    }

    @RequestMapping("/getCommandInterface.do")
    public ModelAndView getCommandInterface(HttpServletRequest req, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("command");
        return mv;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping("/getGernalCommand.do")
    @ResponseBody
    public Map<String, String> getGernalCommand(HttpServletRequest req, HttpServletResponse response) {
        Map<String, String> temp = new HashMap<String, String>();
        String sql = req.getParameter("sql");
        try {
            sql = new String(sql.getBytes("iso8859-1"), "utf-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        temp.put("runned", sql);
        String returnValue = service.getGernalQuery(sql);
        temp.put("result", returnValue);
        return temp;
    }

    @RequestMapping("/getSreachquery.do")
    public ModelAndView getSqueryQuery(HttpServletRequest req, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("sreachResult");
        String sql = req.getParameter("sql");
        try {
            sql = new String(sql.getBytes("iso8859-1"), "utf-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int count = service.getSreachQueryCount(sql);
        mv.addObject("recordCount", count);
        String[][] value = service.getSreachQuery(sql, 0, 20);
        if (sql != null) {
            Pattern p = Pattern.compile("\t|\n");
            Matcher m = p.matcher(sql);
            sql = m.replaceAll("");
        }
        mv.addObject("runningsql", sql);
        mv.addObject("result", value);
        mv.addObject("current", 1);
        int totalpage = count / 20;
        if ((count % 20) != 0) {
            totalpage++;
        }
        mv.addObject("totalpage", totalpage);
        return mv;
    }

    @RequestMapping("/getSreach.do")
    @ResponseBody
    public Map<String, String> getSquery(HttpServletRequest req, HttpServletResponse response) {
        Map<String, String> temp = new HashMap<String, String>();
        String sql = req.getParameter("sql");
        try {
            sql = new String(sql.getBytes("iso8859-1"), "utf-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String pageNum = req.getParameter("pageNum");
        int current = Integer.parseInt(pageNum);
        int start = (current - 1) * 20;
        int end = current * 20;
        String[][] value = service.getSreachQuery(sql, start, end);
        temp.put("info", JasonCover.toJason(value));
        temp.put("current", current + "");
        return temp;
    }

    @RequestMapping("/getBFile.do")
    @ResponseBody
    public Map<String, String> getBFile(HttpServletRequest req) {
        Map<String, String> temp = new HashMap<String, String>();
        String schema = req.getParameter("schema");
        String pageNum = req.getParameter("pageNum");
        int current = Integer.parseInt(pageNum);
        int start = (current - 1) * 20;
        int end = current * 20;
        String[][] value = service.getBfile(schema, start, end);
        temp.put("info", JasonCover.toJason(value));
        temp.put("current", current + "");
        return temp;
    }

    @RequestMapping("/getBFileDownload.do")
    public String getBFileDownload(HttpServletRequest req, HttpServletResponse response) throws Exception {
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        String fileId = req.getParameter("id");
        long id = Long.parseLong(fileId);
        CloudBfile file = service.getBFileDownlaod(id);
        String fileName = file.getName();
        response.setHeader("Content-Disposition", "attachment;fileName=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
        InputStream is = file.getInputStream(0L);
        OutputStream os = response.getOutputStream();
        byte[] bytes = new byte[4096];
        int length;
        while ((length = is.read(bytes)) > 0) {
            os.write(bytes, 0, length);
        }
        os.close();
        is.close();
        return null;
    }

    @RequestMapping("/withQuery.do")
    public ModelAndView getWithQuery(HttpServletRequest req, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("withResult");
        String sql = req.getParameter("sql");
        String[][] result = service.withQuery(sql);
        long countTemp = service.getTotalRows();
        mv.addObject("rowCount", countTemp);
        mv.addObject("result", result);
        if (sql != null) {
            Pattern p = Pattern.compile("\t|\n");
            Matcher m = p.matcher(sql);
            sql = m.replaceAll("");
        }
        mv.addObject("current", 1);
        mv.addObject("runningsql", sql);
        long recordCount = ((countTemp % 20) == 0) ? countTemp / 20 : (countTemp / 20) + 1;
        mv.addObject("recordCount", recordCount);
        mv.addObject("commandId", service.getCurrentCommandId());
        return mv;
    }

    @RequestMapping("/withQueryPage.do")
    @ResponseBody
    public Map<String, String> getWithQueryPage(HttpServletRequest req, HttpServletResponse response) {
        String index = req.getParameter("index");
        String pageNumer = req.getParameter("pageNum");
        String[][] result = service.withQueryPage(Long.parseLong(index), Integer.parseInt(pageNumer));
        Map<String, String> resultTemp = new HashMap<String, String>();
        resultTemp.put("info", JasonCover.toJason(result));
        resultTemp.put("current", pageNumer);

        return resultTemp;
    }

    @RequestMapping("/cancle.do")
    public void cancle(HttpServletRequest req) {
        String testId = req.getParameter("id").toString();
        Pattern pattern = Pattern.compile("[0-9]*");
        if (pattern.matcher(testId).matches()) {
            long id = Long.parseLong(testId);
            if (id != 0) {
                service.cancleTemplate(id);
            }
        }
    }
}
