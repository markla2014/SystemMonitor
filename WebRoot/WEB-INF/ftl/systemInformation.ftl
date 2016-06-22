<#assign path="${request.getContextPath()}">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Em 系统信息</title>
	<link rel="stylesheet" type="text/css" href="../page/css/frameStyle.css">
	<link rel="stylesheet" href="../page/css/bootstrap.min.css">
	<script src="../page/js/jquery-1.10.2.min.js" type="text/javascript"></script>
	<script src="../page/js/bootstrap.min.js" type="text/javascript"></script>
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
  <div class="container-fluid">
<div class="title">
<div class="Titleicon"><img src="../page/images/home03.gif" width="18" height="38" /></div>
<ul class="placeul">
    <li>监控</li>
    <li class="title_text">系统概述</li>
  </ul>  
</div>
<div class="titlebg">
<ul>
<li>系统概述</li>
</ul>

</div>

<table class="table table-condensed">
  <tr>
    <td style="width:35%">版本号</td>
    <td>${info.version}</td>

  </tr>
  <tr>
    <td style="width:35%">版本发布时间</td>
    <td>${info.compileDate}</td>
    </tr>
   <tr>
    <td style="width:35%">部署系统</td>
    <td>${info.mode}</td>
    </tr>
     <tr>
    <td style="width:35%">文件块大小</td>
    <td>${info.blockSize}</td>
    </tr>
       <tr>
    <td style="width:35%">文件备份数</td>
    <td>${info.replication}</td>
    </tr>
        <tr>
    <td style="width:35%">系统开机时间</td>
    <td>${info.startedDate}</td>
    </tr>
    </table>
<div class="titlebg">
<ul>
<li>状态概览</li>
</ul>
</div>
    <table class="table table-condensed">
    <tr>
    <td style="width:35%">主节点运行状态</td>
    <td>${info.masterServer}</td>
  </tr>
    <tr>
    <td style="width:35%" >子节点运行状态</td>
    <td>${info.tabletServers}</td>

  </tr>
    <tr>
    <td style="width:35%">硬盘使用状态</td>
    <td>${info.systemSpaces}</td>

  </tr>
    <tr>
    <td style="width:35%">CPU使用状态</td>
    <td>${info.systemUsage}</td>

  </tr>
    <tr>
    <td style="width:35%">在线会话数量</td>
    <td>${info.onlineSessions}</td>

  </tr>
    <tr>
    <td style="width:35%">当前运行sql的  数量</td>
    <td>${info.runningSQL}</td>

  </tr>
</table>
<div class="col-md-12">
<ul>
<li><!--<div class="chart_T">CPU监控</div>-->
<div id="line1" class="col-md-8 col-sm-8 col-lg-8 pull-left" style="height:318px;min-width:518px"></div>
</li>
<li><!--<div class="chart_T" >硬盘监控</div>-->
<div id="pie1" class="col-md-4 col-sm-4 col-lg-4 pull-right" style="height:318px;min-width:318"></div>
</li>
</ul>
</div>
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
/**
**/


</script>
</body>
</html>
