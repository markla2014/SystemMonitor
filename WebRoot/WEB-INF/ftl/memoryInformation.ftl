<#assign path="${request.getContextPath()}">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>内存信息</title>
	<link rel="stylesheet" type="text/css" href="${path}/page/css/frameStyle.css">
	<script src="${path}/page/js/jquery-1.10.2.min.js" type="text/javascript"></script>
	<script src="${path}/page/js/diagram/cpuInformation.js" type="text/javascript"></script>
	<script src="${path}/page/js/view/common.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function(){
var arrJson='${info}';
var memory='${memoryList}';
creatMapByXxbh(arrJson);
createTage(memory);
loadCharts(memory);
var timeTicket=setInterval(function(){getInfomation();},3000);
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
<li>总内存信息</li>
</ul>
<div>
<div id="content" class="daily_mid">
</div>
<div class="titlebg">
<ul>
<li>各节点memory信息</li>
</ul>
<div>
<div id="content1" class="chart_box">
</div>
</body>
<script src="../page/js/echarts.js" type="text/javascript"></script>
<script type="text/javascript">
function getInfomation(){
    $.ajax({
         type: "get",
            dataType: "json",
            url: "${path}/memory/getMemory.do",
            //complete :function(){$("#load").hide();},AJAX请求完成时隐藏loading提示
            error:function(data){  
           console.error(data);
            clearInterval();
        },  
            success: function(msg){
            if(msg.info!=null){
              	var resobj = msg.info;
              	for(var i=0;i<lineObjects.length;i++){
              	var testv=lineObjects[i].getOption().series;
              	var testt=lineObjects[i].getOption().xAxis[0].data;
               var opt=addData(resobj[i],testv,testt);
                lineObjects[i].setOption(opt);
                }
                }
            }
    }); 
}
</script>
</html>