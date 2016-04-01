<#assign path="${request.getContextPath()}">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>内存信息</title>
	<link rel="stylesheet" type="text/css" href="${path}/page/css/frameStyle.css">
	<script src="${path}/page/js/jquery-1.10.2.min.js" type="text/javascript"></script>
	<script src="${path}/page/js/diagram/pie.js" type="text/javascript"></script>
	<script src="${path}/page/js/view/common.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function(){
var arrJson='${serverList}';
creatMapByXxbh(arrJson);
});
</script>
<body>

<div class="title">
<div class="Titleicon"><img src="../page/images/home03.gif" width="18" height="38" /></div>
<ul class="placeul">
    <li>监控</li>
    <li class="title_text">memory</li>
  </ul>  
</div>
<div class="titlebg">
<ul>
<li>总服务器信息</li>
</ul>
<div>
<div id="content" class="daily_mid">
</div>
</body>
</html>