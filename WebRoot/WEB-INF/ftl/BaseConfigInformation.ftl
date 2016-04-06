<#assign path="${request.getContextPath()}">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>基础配置</title>
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
    <li class="title_text">基础配置</li>
  </ul>  
</div>
<div class="titlebg">
<ul>
<li>基础配置信息</li>
</ul>
</div>
<div id="content" class="daily_mid">
</div>
</body>
<script type="text/javascript">
function creatMap(mes){
          var mes=JSON.parse(mes)
		var str="<table border='1' border='0' cellspacing='0' cellpadding='0' class='tablelist'>";
		for(var i=0;i<mes.length;i++){
		     var xxbn=mes[i];
		     str+="<tr>";
			for(var j=0;j<xxbn.length;j++){
			      if(i==0){
			         str+='<td width="285" bgcolor="#6fb3e0" style="color:#FFF;">'+xxbn[j]+"</td>"
			        }else{
					str+="<td height='40'>"+xxbn[j]+"</td>";
					}
			}
			str+="</tr>";
		}
		str+="</table> ";
		var mm=document.getElementById('content1');
		 mm.innerHTML=str;
	}

</script>
