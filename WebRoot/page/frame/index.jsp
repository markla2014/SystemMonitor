<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>


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
<title>EM系统登陆界面</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<script type="text/javascript" src="page/js/js.js"></script>
<script src="page/js/jquery-1.10.2.min.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="page/css/styles.css">
<script type="text/javascript" >
function checkIP(value){
    var exp=/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
    if(value!="请输入服务器地址"){
     var reg = value.match(exp);
    if(reg==null)
    {
    alert("开始的IP地址不合法！");
    return false;
    }
    }
}
function keyLogin(e){
var theEvent = window.event || e;
var code = theEvent.keyCode || theEvent.which; 
	 if (code==13)  //回车键的键值为13
		 document.getElementById("login").submit();//调用登录按钮的登录事件
	}
	document.onkeypress=keyLogin; 
</script>
</head>

<body>
	<div class="login_box">
		<form id="login" action="main/login.do" method="post">
			<table width="400" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="417" height="90" colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td height="76" colspan="2"><input type="text"
						class=" login_input1" placeholder="账号" name="username" /></td>
				</tr>
				<tr>
					<td height="58" colspan="2"><input type="password"
						class=" login_input2" placeholder="密码" name="Password"/></td>
				</tr>
				<tr>
				<td> <div class="Errors">${errorMessage}</div></td>
				</tr>
				<tr>
					<td height="59" colspan="2"><table width="370" border="0"
							cellpadding="0" cellspacing="0">
							<tr>
								<td width="260" height="48"><input type="text" id="ipaddress" name="ipaddress" class="login_select" value="请输入服务器地址" onchange="checkIP($(this).val())"/></td>
								<!-- <td width="3" align="center" valign="middle"><img
									src="page/images/login_11.gif" width="3" height="16" /></td>
								<td width="125" align="center" valign="middle"><div
										class="password">
										<a href="">忘记密码？</a>
									</div></td> -->
							</tr>

						</table>
				<tr>
					<td height="40" align="center" valign="middle"><table
							width="260" border="0">
							<tr>
								<td align="center" valign="middle"><a class="button grow"
									onclick="form_reset()">重置</a></td>
								<td align="center" valign="middle"><a class="button grow"
									onclick="form_submit()">登录</a></td>
							</tr>
							</form>
						</table></td>

				</tr>
			</table>
	</div>
</body>
</html>
