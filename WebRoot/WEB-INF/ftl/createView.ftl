<#assign path="${request.getContextPath()}">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>命令行</title>
<link rel="stylesheet" type="text/css" href="${path}/page/css/frameStyle.css"/>
<script src="${path}/page/js/jquery-1.10.2.min.js" type="text/javascript"></script>
<script src="${path}/page/js/view/common.js" type="text/javascript"></script>
<script  type="text/javascript">
function submit(){
var sql=$("#sql").val();
if($.trim(sql)==""){
alert("请输入一个查询语句");
return false;
}
var schema='${schema}';
var name=$("#name").val();
window.location.href="${path}/query/createView.do?schema="+schema+"&sql="+sql+"&name="+name;
}
</script>
</head>
<body>
	<div class="title">
		<div class="Titleicon">
			<img src="../page/images/home03.gif" width="18" height="38" />
		</div>
		<ul class="placeul">
			<li>数据库</li>
			<li class="title_text">建立视图</li>
		</ul>
	</div>
<div class="daily_box">
<div class="daily_top">

<table border="0" cellspacing="0" cellpadding="0" >
  <tr>
    <td height="35"><img src="../page/images/r_012.gif" width="93" height="23" /></td>
    <td colspan="6" >&nbsp;</td>
  </tr>
    <tr>
      <td>视图名：</td>
      <td><input type="text" name="name" id="name" class="daily_top_input"  style="width:100px"/></td>
      <td height="35" >建立视图的语句：</td>
      <td><input name="sql" id="sql" type="text" class="daily_top_input"  style="width:200px"/></td>
      <td align="right" valign="middle" ><input name="submit" id="submit" type="button" class="daily_top_button" onclick="submit()" /></td>
    </tr>
</table>
</div>
<div class="Errors">${error!""}</div>
</div>
</body>
</html>
