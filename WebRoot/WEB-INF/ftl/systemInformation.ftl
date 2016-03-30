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

var pie;
var line; 
var now = new Date();
var count=0;
 $(document).ready(function(){

 var myChart0 = document.getElementById('line1');
		var myChart1 = document.getElementById('pie1');
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
         var temp1='${lineValue}'
         var temp2='${pieValue}'
getPie(temp2);
getLine(temp1);
/*
timeTicket = setInterval(function () {
    addData();
    line.setOption({
        xAxis: {
            data: request_time
        },
        series: [{
            name:'点击大小',
            data: click_size
        },{
		  name:'响应大小',
		  data:request_size
		}]
    });
}, 3000);*/	
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
<!--<script src="../page/js/diagram/flow.js" type="text/javascript"></script>-->
<script type="text/javascript">
var lineopt = {
		title : {
			text : '',
		},
	tooltip : {
		trigger : 'axis',
		   formatter:function (params){ // tip的样式
                var res = '时间 : ' + params[0].name +'<br/>';
                for (var i = 0, l = params.length; i < l; i++) {
                    res += '<br/>' + params[i].seriesName + ' : ' + params[i].value;
                }    
                return res;
            }
	},
	toolbox : {
		show : true,
		feature : {
			mark : {
				show : true
			},
			dataView : {
				show : true,
				readOnly : false
			},
			restore : {
				show : true
			},
			saveAsImage : {
				show : true
			}
		}
	},
	grid : {
		x : 60,
		y : 30,
		x2 : 40,
		y2 : 30,

		borderWidth : 0,
		borderColor : "#ccc"
	},
	calculable : true,
	xAxis : [ {
		type : 'category',
		boundaryGap : false,
		axisLabel : {
			show : true,
			interval : 'auto', // {number}
			margin : 10,
			formatter : '{value}', // Template formatter!
			textStyle : {
				color : 'black',
				fontFamily : 'verdana',
			},
		},
		splitLine : {
			show : true,
			lineStyle : {
				type : 'dashed',
			},

		},
		data:[]
	} ],
	yAxis : [ {
		  name:'',
            nameLocation:'end',
            type: 'value',
            scale:true,
            axisLabel: {
                formatter: '{value}'
            }
	} ],
	series : []
};
function getLine(resp){
	var resobj = JSON.parse(resp);
	var lineLedge=[];
	var series=[];
	var xAxis=[]
var minCPU = [];
var maxCPU = [];
var avgCPU=[];
 var now1= now.getHours() +":"+ now.getMinutes()
            +":"+ now.getSeconds();
           xAxis.push(now1);

 maxCPU.push(resobj.dataArray[0]);
 minCPU.push(resobj.dataArray[1]);
 avgCPU.push(resobj.dataArray[2]);
  var totalcpu=[];
  totalcpu.push(maxCPU);
  totalcpu.push(minCPU);
  totalcpu.push(avgCPU);
  
	 for (var i=0; i<resobj.dataArray.length; i++)
     {
         var new_series = JSON.parse(JSON.stringify(lineserie));  // 可以用预设的不同风格的曲线
         new_series.name = resobj.curName[i];
         new_series.data=totalcpu[i];
         console.log(new_series.data);
         lineLedge.push(resobj.curName[i]); // 必须和曲线的name一致 每条曲线的名字必须不一样，否则图例会出错
         series.push(new_series);
     }
    
	var opt = {
			title : {
				text : resobj.objName
			},
			legend : {
				data : lineLedge
			},
			  xAxis:{
	                data:xAxis
	            },
	            series:series
	}
	
	line.setOption(opt);
}
var lineserie =  {
        name:'',
    	type : 'line',
		stack : '总量',
		itemStyle : {
			normal : {
				areaStyle : {
					type : 'default'
				}
			}
		},
		data:[]
};
</script>
</body>
</html>
