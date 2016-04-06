<#assign path="${request.getContextPath()}">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>内存信息</title>
	<link rel="stylesheet" type="text/css" href="../page/css/frameStyle.css">
	<script src="../page/js/jquery-1.10.2.min.js" type="text/javascript"></script>
	<script src="../page/js/diagram/pie.js" type="text/javascript"></script>
		<script src="../page/js/view/common.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function(){
var arrJson='${info}';
creatMapByXxbh(arrJson);
});
</script>
<body>

<div class="title">
<div class="Titleicon"><img src="../page/images/home03.gif" width="18" height="38" /></div>
<ul class="placeul">
    <li>配置</li>
    <li class="title_text">DFS配置信息</li>
  </ul>  
</div>
<div class="titlebg">
<ul>
<li>DFS文件配置</li>
</ul>
</div>
<div id="content" class="daily_mid">
</div>
</body>

</html>