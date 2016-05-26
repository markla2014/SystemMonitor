<#assign path="${request.getContextPath()}">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户</title>
<link rel="stylesheet" type="text/css" href="${path}/page/css/frameStyle.css"/>
	<script src="${path}/page/js/jquery-1.10.2.min.js" type="text/javascript"></script>
		<script src="${path}/page/js/view/common.js" type="text/javascript"></script>
	<script type="text/javascript">
	$(document).ready(function(){
	    var user='${admin!""}';
	    if(user!="system"){
	      $(".tool2li").hide();
	    }
	    
	});
function createUser(){
openWindow("${path}/page/frame/createuser.jsp");
}
function deleteUser(obj){
var username=obj.parent().parent().children("td").eq(0).text();
 location.href = "${path}/user/deleteUser.do?name="+username;
}
function changePassword(obj){
var username=obj.parent().parent().children("td").eq(0).text();
openWindow("${path}/user/changePasswordInterface.do?name="+username);
}
</script>
</head>
<body>
<div class="title">
<div class="Titleicon"><img src="../page/images/home03.gif" width="18" height="38" /></div>
<ul class="placeul">
    <li>权限管理</li>
    <li class="title_text">用户管理</li>
  </ul>  
</div>
<div class="tool2li">
  <ul class="toollist">
   <li><img src="../page/images/icon14.gif" width="18" height="38" /></li>
    <li><a href="javascript:createUser();">添加用户</a></li>
  </ul>
</div>
<div class="daily_mid">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tablelist">
<#assign x=0 />
<#list info as row>
<#assign len=0/>
<tr>
<#list row as col>
<#if col=="system">
<#assign len=x/>
</#if>
<#if x==0>
<th width="285" bgcolor="#6fb3e0" style="color:#FFF;">${col}</th>
<#else>
<td height="40">${col}</td>
</#if>
</#list>
<#if x!=0>
<#if len==0>
<td><a href="#" onclick="deleteUser($(this))">删除</a></td>
<#else>
<td></td>
</#if>
<td><a href="#" onclick="changePassword($(this))">更改密码</a></td>
<#else>
<th width="285" bgcolor="#6fb3e0" style="color:#FFF;"></th>
<th width="285" bgcolor="#6fb3e0" style="color:#FFF;"></th>
</#if>
</tr>
<#assign x=x+1 />
</#list>
</table>

</div>
<div class="Errors">${errorMessage!""}</div>
</body>

</html>
