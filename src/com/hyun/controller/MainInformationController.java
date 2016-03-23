package com.hyun.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/main")
public class MainInformationController {

	@RequestMapping("/show")
public ModelAndView showMainInformation(HttpServletRequest req){
		  ModelAndView mv = new ModelAndView("hello"); 
		  mv.addObject("title", "Spring MVC And Freemarker");  
	        mv.addObject("content", " Hello world ， test my first spring mvc ! ");  
	        return mv;  
	}
}
