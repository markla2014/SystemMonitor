<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>翰云时代EM系统</title>
<meta http-equiv=Content-Type content="text/html; charset=utf8">
<meta http-equiv=Pragma content=no-cache>
<meta http-equiv=Cache-Control content=no-cache>
<meta http-equiv=Expires content=-1000>
<link rel="stylesheet" href="page/css/frameStyle.css" type="text/css" />
</head>
<frameset border=0 frameSpacing=0 rows="44, *" frameBorder=0>
<frame name=header  src="${pageContext.request.contextPath}/main/header.do" frameBorder=0 noResize scrolling=no>
<frameset cols="189, *" >
<frame name=menu src="page/frame/menu2.jsp" frameBorder=0 noResize scrolling="no">
<frame name=rightFrame src="${pageContext.request.contextPath}/main/mainInformtion.do" frameBorder=0 noResize scrolling=yes>
</frameset>
</frameset>
<noframes></noframes>
<body>
</body>
</html>
