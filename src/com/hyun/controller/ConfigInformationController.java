package com.hyun.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hyun.common.JasonCover;
import com.hyun.service.impl.ConfigurationServiceImpl;

@Controller
@RequestMapping("/config")
public class ConfigInformationController {
 @Resource
 private ConfigurationServiceImpl service; 
 
 @RequestMapping("/baseInfo.dao")
 public ModelAndView getBaseConfigInfomation(HttpServletRequest req,HttpServletResponse response) {
	 ModelAndView mv = new ModelAndView("BaseConfigInformation");
	 String[][] temp=service.getBaseConfigure();
	 mv.addObject("info", JasonCover.toJason(temp));
	 return mv;
 }
}
