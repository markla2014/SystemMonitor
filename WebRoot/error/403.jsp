<%@ page language="java" contentType="text/html; charset=GB2312" pageEncoding="gb2312" isELIgnored="false"%>
<%@page import="java.util.Enumeration"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>">
		<meta http-equiv="Content-Type" content="text/html; charset=GB2312" />
		<link href="resources/css/frameRight/content.css" type="text/css" rel="stylesheet" />
		<script type="text/javascript" src="resources/js/jquery-1.4.4.js"></script> 
		<script type="text/javascript" src="resources/js/frameRight/layout.js"></script>
		<script type="text/javascript" src="resources/js/validate.js"></script>
		<script language="javascript" type="text/javascript">
		</script>
		<title>没有访问权限</title>
	</head>

	<body>
		<div>
			<div class="fl" style="width:231px"><img src="resources/images/frameRight/qx_01.jpg" width="231" height="254" /></div>
			<div class="fl" style="width:479px">
		        <div><img src="resources/images/frameRight/qx_02.jpg" /></div>
		        <div style="float:right; padding:5px 0 0 "><a href="javascript:window.history.go(-1)"><img src="resources/images/frameRight/fhsy.jpg"  onmouseover="this.src='resources/images/frameRight/fhsy01.jpg'" onmouseout="this.src='resources/images/frameRight/fhsy.jpg'"/></a></div>
		  </div>
		</div>
	</body>
</html>