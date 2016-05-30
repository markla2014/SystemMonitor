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
var charlen;
$(document).ready(function(){
var arrJson='${info}';
var dataNode='${datanode}';
creatMapByXxbh(dataNode);
createTage(arrJson);
loadCharts(arrJson);
});
</script>
<body>

<div class="title">
<div class="Titleicon"><img src="../page/images/home03.gif" width="18" height="38" /></div>
<ul class="placeul">
    <li>监控</li>
    <li class="title_text">硬盘</li>
  </ul>  
</div>
<div class="titlebg">
<ul>
<li>DFS节点信息</li>
</ul>
</div>
<div id="content2" class="daily_mid">
<table border='1' border='0' cellspacing='0' cellpadding='0' class='tablelist'>
<#assign x = 0 />
<#list result as su>
<tr>
<#list su as col>
<#if x==0>
<td width="285"  bgcolor="#6fb3e0"  style="color:#FFF;">${col}</td>
<#else>
<td height="40">${col}</td>
</#if>	
</#list>
</tr>
<#assign x=x+1 />
</#list>
</table>
</div>
<div class="titlebg">
<ul>
<li>DFS配置</li>
</ul>
</div>
<div id="content" class="daily_mid">
</div>
<div class="titlebg">
<ul>
<li>硬盘信息</li>
</ul>
<div>
<div id="content1" class="chart_box">
</div>
</body>
<script src="../page/js/echarts.js" type="text/javascript"></script>
<script src="../page/js/diagram/flow.js" type="text/javascript"></script>
<script type="text/javascript">
function createTage(message){
var ms=JSON.parse(message)
var myChart0 = document.getElementById('content1');
var tag="<url>"
charlen=ms.length;
for(var i=0;i<ms.length;i++){
var item=ms[i];
 tag+='<li><div class="chart_T"></div>';
 tag+='<div id="pie'+i+'" style="width:550px;height:260px;"></div>';
 tag+="</li>";
}
tag+='</url>';
myChart0.innerHTML=tag;
}
function loadCharts(message){
var ms=JSON.parse(message)
for(var i=0;i<ms.length;i++){

var charName='pie'+i;
var Tchart= document.getElementById(charName);
pie =  echarts.init(Tchart);
pie.setOption(pieopt);
getPie(JSON.stringify(ms[i]));
}
}
</script>
</html>