<#assign path="${request.getContextPath()}">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link rel="stylesheet" href="${path}/page/css/frameStyle.css" type="text/css" />

</head>

<body>
<div class="top">
<div class="top_logo"></div>
<div class="top_right">
<ul>
<li><div class="top_right_welcome">
<div class="top_right_welcome_text">欢迎，${username}</div>
</div></li>

<li><div class="top_right_user"><a href="${path}/page/frame/contact.jsp" target="rightFrame">联系方式</a></div></li>
<li><div class="top_right_user"><a href="javascript:top.window.location='${path}/user/quite.do'">退 出</a></div> </li>
</ul>
</div>
</div>
</body>
</html>