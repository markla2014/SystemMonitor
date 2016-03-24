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

<table width="189" border="0" cellSpacing=0 cellPadding=0  background="menu_bg_06.gif" height="100%">
  <tr>
<td height="40"><div class="menu_bg1">
<ul>
<li><a href="page/frame/menu.jsp" target="menu"><div class="menu_bg1_DP1"></div></a></li>
<li><a href="page/frame/menu2.jsp" target="menu"><div class="menu_bg1_monitor2"></div></a></li>
</ul>

</div></td>
</tr>
<tr>
    <td><div class="menu" >
	<ul >
		<li><a class="active" href="#">
        <table width="280" border="0">
  <tr>
    <td width="30"><img src="page/images/1_03.png" width="26" height="20" /></td>
    <td>ABC数据库</td>
  </tr>
</table>
 </a>
			<ul style="display:none;">
				<li><a href="biao.html" target="rightFrame"> <table width="280" border="0">
  <tr>
    <td width="30"><img src="page/images/2_07.png" width="26" height="20" /></td>
    <td>表</td>
  </tr>
</table></a><ul>
	<li><a href="#"><table width="280" border="0">
  <tr>
    <td width="30"><img src="page/images/2_08.png" width="26" height="20" /></td>
    <td>创建表</td>
  </tr>
</table></a></li>
					</ul></li>
				<li><a href="#"><table width="280" border="0">
  <tr>
    <td width="30"><img src="page/images/2_10.png" width="26" height="20" /></td>
    <td>视图</td>
  </tr>
</table></a><ul>
						<li><a href="#"><table width="280" border="0">
  <tr>
    <td width="30"><img src="page/images/2_11.png" width="26" height="20" /></td>
    <td>创建视图</td>
  </tr>
</table></a></li>
					</ul></li>
				<li><a href="#"><table width="280" border="0">
  <tr>
    <td width="30"><img src="page/images/2_13.png" width="26" height="20" /></td>
    <td>函数</td>
  </tr>
</table></a><ul>
						<li><a href="hanshu.html" target="rightFrame"><table width="280" border="0">
  <tr>
    <td width="30"><img src="page/images/2_16.png" width="26" height="20" /></td>
    <td>创建函数</td>
  </tr>
</table></a></li>
					</ul></li>
                
				<li><a href="#"><table width="280" border="0">
  <tr>
    <td width="30"><img src="page/images/2_19.png" width="26" height="20" /></td>
    <td>查询</td>
  </tr>
</table></a><ul>
						<li><a href="chaxun.html" target="rightFrame"><table width="280" border="0">
  <tr>
    <td width="30"><img src="page/images/2_20.png" width="26" height="20" /></td>
    <td>创建查询</td>
  </tr>
</table></a></li>
					</ul></li>
			</ul>
		</li>
		<li><a href="#"><table width="280" border="0">
  <tr>
    <td width="30"><img src="page/images/1_03.png" width="26" height="20" /></td>
    <td>DEF数据库</td>
  </tr>
</table></a>
        <ul style="display: none;">
				<li><a href="#"> <table width="280" border="0">
  <tr>
    <td width="30"><img src="page/images/2_07.png" width="26" height="20" /></td>
    <td>表</td>
  </tr>
</table></a><ul>
	<li><a href="#"><table width="280" border="0">
  <tr>
    <td width="30"><img src="page/images/2_08.png" width="26" height="20" /></td>
    <td>创建表</td>
  </tr>
</table></a></li>
					</ul></li>
				<li><a href="#"><table width="280" border="0">
  <tr>
    <td width="30"><img src="page/images/2_10.png" width="26" height="20" /></td>
    <td>视图</td>
  </tr>
</table></a><ul>
						<li><a href="#"><table width="280" border="0">
  <tr>
    <td width="30"><img src="page/images/2_11.png" width="26" height="20" /></td>
    <td>创建视图</td>
  </tr>
</table></a></li>
					</ul></li>
				<li><a href="#"><table width="280" border="0">
  <tr>
    <td width="30"><img src="page/images/2_13.png" width="26" height="20" /></td>
    <td>函数</td>
  </tr>
</table></a><ul>
						<li><a href="#"><table width="280" border="0">
  <tr>
    <td width="30"><img src="page/images/2_16.png" width="26" height="20" /></td>
    <td>创建函数</td>
  </tr>
</table></a></li>
					</ul></li>
                
				<li><a href="#"><table width="280" border="0">
  <tr>
    <td width="30"><img src="page/images/2_19.png" width="26" height="20" /></td>
    <td>查询</td>
  </tr>
</table></a><ul>
						<li><a href="#"><table width="280" border="0">
  <tr>
    <td width="30"><img src="page/images/2_20.png" width="26" height="20" /></td>
    <td>创建查询</td>
  </tr>
</table></a></li>
					</ul></li>
			</ul>
        </li>

	</ul>
</div></td>
  </tr>
</table>
</body>
</html>