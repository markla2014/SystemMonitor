package com.hyun.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hyun.common.ServerMonitorConstant;
import com.hyun.service.impl.UserServiceImpl;

@Controller
@RequestMapping("/user")
public class UserInformationController extends BaseController {
    @Autowired
    private UserServiceImpl service;

    @RequestMapping("/getUserList.do")
    public String getUserList(HttpServletRequest req, HttpServletResponse response, Map<String, Object> map) {
        String error = req.getParameter("errorMessage");
        String admin = req.getSession().getAttribute("UserName").toString();
        String[][] temp = service.getUsertable(admin);
        if ("none".equals(error)) {
            error = "";
        }

        map.put("admin", admin);
        map.put("errorMessage", error);
        map.put("info", temp);
        return "userList";
    }

    @RequestMapping("/deleteUser.do")
    public ModelAndView deleteUser(HttpServletRequest req) {
        String username = req.getParameter("name");
        String url = "forward:getUserList.do";
        String result = service.dropUser(username);
        url = url + "?errorMessage=" + result;
        return new ModelAndView(url);
    }

    @RequestMapping("/createUser.do")
    public ModelAndView createUser(HttpServletRequest req) {
        String username = req.getParameter("myname");
        String password = req.getParameter("password");
        String url = "forward:getUserList.do";
        String result = service.createUser(username, password);
        if (ServerMonitorConstant.SUCCESSFUL.equals(result)) {
            url = url + "?errorMessage=" + result;
            return new ModelAndView(url);
        } else {
            url = url + "?errorMessage=" + result;

            return new ModelAndView(url);
        }
    }

    @RequestMapping("/changePasswordInterface.do")
    public ModelAndView changePasswordInterface(HttpServletRequest req) {
        String username = req.getParameter("name");
        ModelAndView mv = new ModelAndView("changePassword");
        mv.addObject("username", username);
        if ("system".equals(req.getSession().getAttribute("UserName").toString())) {
            mv.addObject("admin", true);
            return mv;
        } else {
            mv.addObject("admin", false);
            return mv;
        }
    }

    @RequestMapping("/changePassword.do")
    public ModelAndView changePassword(HttpServletRequest req) {
        String username = req.getParameter("myname");
        String oldPassword = req.getParameter("password");
        String newPassword = req.getParameter("confirm_password");
        String result = service.changUserPassword(username, newPassword, oldPassword);
        String url = "forward:getUserList.do";
        url = url + "?errorMessage=" + result;
        return new ModelAndView(url);

    }

    @RequestMapping("/restart.do")
    @ResponseBody
    public String restart(HttpServletRequest req) {
        String all = req.getParameter("all");
        boolean switch1 = Boolean.parseBoolean(all);

        if (service.restartServer(switch1)) {
            return "success";
        } else
            return "fail";
    }

    @RequestMapping("/quite.do")
    public String quite(HttpServletRequest req) {
        req.getSession().removeAttribute("UserName");
        // ((HttpSession) req.getAttribute("UserName")).invalidate();
        service.userQuite();
        return "index";
    }
}
