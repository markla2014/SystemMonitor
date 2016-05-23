<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
		
	String sessionValue=request.getSession().getAttribute("UserName").toString() ;
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link rel="stylesheet" href="../css/frameStyle.css" type="text/css" />
<script src="../js/jquery-1.10.2.min.js" type="text/javascript"></script>
<script type="text/javascript" src="../js/menu_min.js"></script>
<script type="text/javascript" src="../js/view/common.js"></script>


<script type="text/javascript">
	$(document).ready(function() {
		$(".menu ul li").menu();
		var admin="<%=sessionValue%>";
		if(admin!="system"){
		$(".menu li").eq(1).hide();
		}
	});
  function restart(){
    openWindow("${pageContext.request.contextPath}/page/frame/dialog.jsp");
  }
	</script>
</head>

<body STYLE="OVERFLOW:SCROLL;OVERFLOW-X:HIDDEN;OVERFLOW-Y:HIDDEN">

	<table width="189" border="0" cellSpacing=0 cellPadding=0
		background="../images/menu_bg_06.gif" height="100%">
		<tr>
			<td height="40"><div class="menu_bg1">
					<ul>
						<li><a
							href="${pageContext.request.contextPath}/query/getSchema.do"
							target="menu"><div class="menu_bg1_DP2"></div></a></li>
						<li><a href="${pageContext.request.contextPath}/page/frame/menu2.jsp" target="menu"><div
									class="menu_bg1_monitor2"></div></a></li>
						<li><a href="${pageContext.request.contextPath}/page/frame/user.jsp" target="menu"><div
									class="menu_bg1_daily1"></div></a></li>
					</ul>

				</div></td>
		</tr>
		<tr>
			<td><div class="menu">
					<ul>
						<li><a
							href="${pageContext.request.contextPath}/user/getUserList.do?errorMessage=none"
							target="rightFrame">
								<table width="280" border="0">
									<tr>
										<td width="30"><img src="../images/user.png" width="26"
											height="20" /></td>
										<td>用户管理</td>
									</tr>
								</table>
						</a></li>
						</li>
						<li><a href="javascript:restart()"><table width="280" border="0">
									<tr>
										<td width="30"><img src="../images/restart.png" width="26"
											height="20" /></td>
										<td>重启</td>
									</tr>
								</table></a></li>
					</ul>
				</div></td>
		</tr>
	</table>
</body>
</html>

