<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>


<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>"><!--如果找不到基础路径加上这个标签 -->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link rel="stylesheet" href="page/css/frameStyle.css" type="text/css" />
<script src="page/js/jquery-1.10.2.min.js" type="text/javascript"></script>
<script type="text/javascript" src="page/js/menu_min.js"></script>
<script type="text/javascript">
$(document).ready(function (){
  $(".menu ul li").menu();
}); 
</script>
</head>

<body>

<table width="189" border="0" cellSpacing=0 cellPadding=0  background="page/images/menu_bg_06.gif" height="100%">
  <tr>
<td height="40"><div class="menu_bg1">
<ul>
<li><a href="page/frame/menu.jsp" target="menu"><div class="menu_bg1_DP2"></div></a></li>
<li><a href="page/frame/menu2.jsp" target="menu"><div class="menu_bg1_monitor1"></div></a></li>
</ul>

</div></td>
</tr>
<tr>
    <td><div class="menu" >
	<ul >
		<li><a href="CPU.html"  target="rightFrame">
        <table width="280" border="0">
  <tr>
    <td width="30"><img src="page/images/3.png" width="26" height="20" /></td>
    <td>CPU</td>
  </tr>
</table>
 </a>
			
		</li>
		<li><a href="#"><table width="280" border="0">
  <tr>
    <td width="30"><img src="page/images/disk.png" width="26" height="20" /></td>
    <td>硬盘</td>
  </tr>
</table></a>
        
        </li>
		<li><a href="#"><table width="280" border="0">
  <tr>
    <td width="30"><img src="page/images/2.png" width="26" height="20" /></td>
    <td>内存</td>
  </tr>
</table></a>
        
        </li>
        		<li><a href="#"><table width="280" border="0">
  <tr>
    <td width="30"><img src="page/images/1.png" width="26" height="20" /></td>
    <td>线程</td>
  </tr>
</table></a>
        
        </li>
        <li>
		<a href="#"><table width="280" border="0">
  <tr>
    <td width="30"><img src="page/images/ico_D.png" width="26" height="20" /></td>
    <td>日志</td>
  </tr>
</table></a>
        			<ul style="display:none;">
				<li><a href="#" > <table width="280" border="0">
  <tr>
    <td width="30"><img src="page/images/ico_.png" width="26" height="20" /></td>
    <td>创建日志</td>
  </tr>
</table></a></li>

				<li><a href="RZCX.html" target="rightFrame"> <table width="280" border="0">
  <tr>
    <td width="30"><img src="page/images/ico.png" width="26" height="20" /></td>
    <td>查询日志</td>
  </tr>
</table></a></li>
</ul>	
		</li>
	</ul>
</div></td>
  </tr>
</table>
</body>
</html>