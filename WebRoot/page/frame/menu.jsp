<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>


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
<script type="text/javascript" src="page/js/jquery-smartMenu.js"></script>
<script type="text/javascript" src="page/js/menu_min.js"></script>
<script type="text/javascript" src="page/js/view/common.js"></script>
<script type="text/javascript">

var getSchema='${schema}';
var menus=[];
$(document).ready(function (){
createMenus(getSchema);
  $(".menu ul li").menu();
  var obj=[{
				text:'删除模块',
				func:function(){
					location.href = "query/getTables.do?schema="+menus;
				}},
		{text:'添加模块',func:function(){  window.parent.rightFrame.location.replace("query/createSchema.do?schema="+menus); }}];
// $(".menu ul li:first").smartMenu(obj, {
  //  name: "body"    
//});
}); 

</script>
</head>

<body>

<table width="189" border="0" cellSpacing=0 cellPadding=0  background="page/images/menu_bg_06.gif" height="100%">
  <tr>
<td height="40"><div class="menu_bg1">
<ul>
<li><a href="${pageContext.request.contextPath}/query/getSchema.do" target="menu"><div class="menu_bg1_DP1"></div></a></li>
<li><a href="page/frame/menu2.jsp" target="menu"><div class="menu_bg1_monitor2"></div></a></li>
</ul>

</div></td>
</tr>
<tr>
  <td>
  <div class="menu">
  </div>
	</td>
  </tr>
</table>
</body>
</html>