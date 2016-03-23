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

<title>EM系统登陆界面</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<script type="text/javascript" src="page/js/js.js"></script>
	<link rel="stylesheet" type="text/css" href="page/css/styles.css">
</head>

<body>
<div class="login_box">
   <form id="login" action="main/login" method="post">
  <table width="400" border="0" align="center" cellpadding="0" cellspacing="0" >
    <tr>
      <td width="417" height="90" colspan="2">&nbsp;</td>
    </tr>
    <tr>
      <td height="76" colspan="2"><input type="text" class=" login_input1" placeholder="账号" name="username" /></td>
    </tr>
        <tr>
      <td height="58" colspan="2" ><input type="text" class=" login_input2" placeholder="密码" name="Password" /></td>
    </tr>
         <tr>
      <td height="59" colspan="2"><table width="370" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="260" height="48">
            <select name="select" id="select"  class="login_select">
              <option selected="selected">更换服务器地址</option>
              <option>192.168.0.10</option>
              <option>192.168.0.10:8080</option>
</select>

   </td>
          <td width="3" align="center" valign="middle"><img src="page/images/login_11.gif" width="3" height="16" /></td>
          <td width="125" align="center" valign="middle"><div class="password"><a href="">忘记密码？</a></div></td>
        </tr>
        
      </table>
      
  <tr>
      <td height="40" align="center" valign="middle"><table width="260" border="0">
  <tr>
    <td align="center" valign="middle"><a class="button grow" onclick="form_reset()">重置</a></td>
    <td align="center" valign="middle"><a class="button grow" onclick="form_submit()">登录</a></td>
  </tr>
   </form>
</table>

</td>

    </tr>
  </table>

</div>
</body>
</html>
