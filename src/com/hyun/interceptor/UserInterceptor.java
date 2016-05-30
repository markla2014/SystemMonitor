package com.hyun.interceptor;

import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
import javax.servlet.http.HttpSession;

import org.junit.runner.Request;
import org.springframework.web.servlet.HandlerInterceptor;  
import org.springframework.web.servlet.ModelAndView;  
  
public class UserInterceptor implements HandlerInterceptor  {  
    
    private static final String LOGIN_URL = "/page/frame/index.jsp";  
  //拦截前处理  
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {  
	  HttpSession session = request.getSession(true);  
      // 从session 里面获取用户名的信息  
      Object obj = session.getAttribute("UserName");  
      String url=request.getRequestURI();
      // 判断如果没有取到用户信息，就跳转到登陆页面，提示用户进行登陆 
  
      if (obj == null || "".equals(obj.toString())) {  
    	  response.sendRedirect(request.getContextPath()+LOGIN_URL);  
      }  
    
      return true;  
  }  
  //拦截后处理  
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object obj, ModelAndView mav) throws Exception { }  
  //全部完成后处理  
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object obj, Exception e) throws Exception { }  
}  