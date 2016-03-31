<#assign path="${request.getContextPath()}">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>Em 系统信息</title>
	<link rel="stylesheet" type="text/css" href="../page/css/frameStyle.css">
	<script src="../page/js/jquery-1.10.2.min.js" type="text/javascript"></script>
	<script src="../page/js/diagram/pie.js" type="text/javascript"></script>
<script type="text/javascript">
var request_time = [];
var xAxis=[];
var pie;
var line; 
var count=0;
var myChart0;
var myChart1;
 $(document).ready(function(){
 myChart0 = document.getElementById('line1');
myChart1 = document.getElementById('pie1');
loadCharts();

 var temp1='${lineValue}'
 var temp2='${pieValue}'
getPie(temp2);
getLine(temp1);

timeTicket = setInterval(function () {
getInfomation();
  
}, 3000);

	});

</script>
  </head>

  <body>
<div class="title">
<div class="Titleicon"><img src="../page/images/home03.gif" width="18" height="38" /></div>
<ul class="placeul">
    <li>监控</li>
    <li class="title_text">主页</li>
  </ul>  
</div>
<div class="titlebg">
<ul>
<li>概览信息</li>
</ul>

</div>


<Div class="daily_mid">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tablelist">
  <tr>
    <td height="40">Cloudwave 数据库版本号</td>
    <td>${info.version}</td>

  </tr>
  <tr>
    <td height="40">安装包发布时间</td>
    <td>${info.compileDate}</td>
    </tr>
   <tr>
    <td height="40">发行版本</td>
    <td>${info.mode}</td>
    </tr>
     <tr>
    <td height="40">文件块大小</td>
    <td>${info.blockSize}</td>
    </tr>
       <tr>
    <td height="40">文件备份数</td>
    <td>${info.replication}</td>
    </tr>
        <tr>
    <td height="40">系统运行时间</td>
    <td>${info.startedDate}</td>
    </tr>
    </table>
<div class="titlebg">
<ul>
<li>状态概览</li>
</ul>
</div>
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tablelist">
    <tr>
    <td height="40">主节点运行状态</td>
    <td>${info.masterServer}</td>
  </tr>
    <tr>
    <td height="40">存储节点运行状态</td>
    <td>${info.tabletServers}</td>

  </tr>
    <tr>
    <td height="40">硬盘使用状况概述</td>
    <td>${info.systemSpaces}</td>

  </tr>
    <tr>
    <td height="40">CPU使用状况概述</td>
    <td>${info.systemUsage}</td>

  </tr>
    <tr>
    <td height="40">当前数据库对话</td>
    <td>${info.onlineSessions}</td>

  </tr>
    <tr>
    <td height="40">当前运行的sql</td>
    <td>${info.runningSQL}</td>

  </tr>
</table>
<div class="chart_box">
<ul>
<li><div class="chart_T">CPU监控</div>
<div id="line1" style="width:550px;height:260px;"></div>
</li>
<li><div class="chart_T" >硬盘监控</div>
<div id="pie1" style="width:340px;height:376px;"></div>
</li>
</ul>
</div>
<script src="../page/js/echarts.js" type="text/javascript"></script>
<script src="../page/js/diagram/flow.js" type="text/javascript"></script>
<script type="text/javascript">
var itemserie={name:'',data:[]};
function getInfomation(){
    $.ajax({
         type: "get",
            dataType: "json",
            url: "${path}/main/getCPUSum.do",
            //complete :function(){$("#load").hide();},AJAX请求完成时隐藏loading提示
            error:function(data){  
            console.log(data);
            clearInterval();
        },  
            success: function(msg){
            if(msg.info!=null){
              	var resobj = msg.info;
               var opt=addData(resobj);
                 console.log(opt);
                line.setOption(opt);
                }
            }
    }); 
}
function loadCharts(){

     function eConsolePieClick(e) {
                console.log(e); // 3.0 e有嵌套结构，不可以JSON.stringify
        }
		  pie = echarts.init(myChart1, 'infograph'); // 带主题的初始化
        pie.setOption(pieopt);
      //  pie.on('click',  eConsolePieClick); // 点击事件绑定 与2.0不同
      line =  echarts.init(myChart0);
      line.setOption(lineopt);
        // 多图联动 3.0.1fix
       // line.group =  'group1'; 
		//pie.group= 'group1';
        //echarts.connect('group1');
        //    echarts.connect([pie, bar, line]); // 联动的两种写法

}

function addData(resobj) {
var series=[];
	count++;
	var now=new Date();
   var now1= resobj.time;
    xAxis.push(now1);
   
  $(totalcpu).each(function(i){
     var temp=totalcpu[i];
     var num=resobj.dataArray[i] 
     temp.push(num);
  totalcpu[i]=temp
  });
      console.log(totalcpu);
 if (count>10) {
    count=11;
      $(totalcpu).each(function(i){
          totalcpu[i].shift();
      });
          xAxis.shift();
      }
    for(var i=0;i<totalcpu.length;i++){
        var new_series = JSON.parse(JSON.stringify(itemserie));
         new_series.name=lineLedge[i];
          new_series.data=totalcpu[i];
         series.push(new_series);
    }
     var opt={
          xAxis: {
            data: xAxis
        },
        series:series
      }
      return opt;
}
</script>
</body>
</html>
