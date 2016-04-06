/**
 * 
 */
var lineObjects=[];
function createTage(message){
var ms=JSON.parse(message)
var myChart0 = document.getElementById('content1');
var tag="<url>"
charlen=ms.length;
for(var i=0;i<ms.length;i++){
var item=ms[i];
 tag+='<li><div class="chart_T"></div>';
 tag+='<div id="line'+i+'" style="width:1024px;height:510px;"></div>';
 tag+="</li>";
}
tag+='</url>';
myChart0.innerHTML=tag;
}

function loadCharts(message){
var ms=JSON.parse(message)
for(var i=0;i<ms.length;i++){
var charName='line'+i;
var Tchart= document.getElementById(charName);
line = echarts.init(Tchart);
line.setOption(lineopt);
lineObjects.push(line);
getLine(JSON.stringify(ms[i]));
}
}
  var totalcpu=[];
	var lineLedge=[];
var lineopt = {
		title : {
			text : '',
		},
	tooltip : {
		trigger : 'axis',
		   formatter:function (params){ // tip的样式
                var res = '时间 : ' + params[0].name +'<br/>';
                for (var i = 0, l = params.length; i < l; i++) {
                    res += '<br/>' + params[i].seriesName + ' : ' + params[i].value+"%";
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
 var totalcpu=[];
	var series=[];
var xAxis=[];
var now=new Date();
 var now1= resobj.time;
           xAxis.push(now1);
 $(resobj.dataArray).each(function(i){
	 var temp=new Array();
	 temp.push(resobj.dataArray[i]);
	 totalcpu.push(temp);
 });
  
	 for (var i=0; i<resobj.dataArray.length; i++)
     {
         var new_series = JSON.parse(JSON.stringify(lineserie));  // 可以用预设的不同风格的曲线
         new_series.name = resobj.curName[i];
         new_series.data=totalcpu[i];
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
		    dataZoom: [{
		        type: 'inside',
		        start: 0,
		        end: 100
		    }, {
		        start: 0,
		        end: 10
		    }],
			  xAxis:{
	                data:xAxis
	            },
	            series:series
	}
	console.log(opt);
	line.setOption(opt);
}
var lineserie =  {
        name:'',
    	type : 'line',
		stack : '总量',
        markPoint: {
                  data: [
                    {type: 'max', name: '最大值'},
                ]
        },
        markLine: {
            data: [
                {type: 'average', name: '平均值'}
            ]
        },
		//itemStyle : {
			//normal : {
				//areaStyle : {
					//type : 'default'
				//}
			//}
		//},
		
		data:[]
};
var itemserie={name:'',data:[]};