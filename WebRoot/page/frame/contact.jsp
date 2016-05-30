<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>联系我们</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="page/css/frameStyle.css" type="text/css" />
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
<div class="title">
<div class="Titleicon"><img src="page/images/home01.gif" width="18" height="38" /></div>
<ul class="placeul">
    <li>联系我们</li>
  </ul>  
</div>
<div class="daily_box">
    
<ul class="contact">
<li><label>公司名:</label> 翰云时代</li>
<li><label>邮箱：</label>hysd@cloudwave.cn</li>
<li><label>联系电话：</label>010-82894432M</li>
<li><label>手机：</label>15522082697</li>
<li><label>投诉邮箱：</label>jie.lang@cloudwave.cn</li>
</ul>
    

</Div>
</body>
</html>