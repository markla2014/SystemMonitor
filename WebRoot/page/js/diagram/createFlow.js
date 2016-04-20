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
                //var res = '时间 : ' + params[0].name +'<br/>';
			   var test=params[0];
			   var res="<br/>";
                for (var i = 0, l = params.length; i < l; i++) {
                   res += '<br/>' + params[i].seriesName + ' : ' + params[i].value+"%";
                }    
                return res;
            }
	},
	toolbox : {
		show : true,
		feature : {
			dataView : {
				show : true
			},
			restore : {
				show : true
			},
			saveAsImage : {
				show : true
			}
		}
	},
	color:['#b6a2de','#2ec7c9','#3d7dec'], 
	grid : {
		x : 60,
		y : 30,
		x2 : 40,
		y2 : 60,

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
				color : '#008acd',
				fontFamily : 'verdana',
			},
		},
		splitLine : {
			show : true,
			lineStyle : {
				type : 'dashed',
				color : '#008acd'
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
                formatter: '{value}',
                textStyle : {
    				color : '#008acd',
    				fontFamily : 'verdana',
    			},
            },
            splitLine : {
    			show : true,
    			lineStyle : {
    				type : 'dashed',
    				color : '#008acd'
    			},

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
		    	 type:'slider',
		    	 show : true,
		        start: 0,
		        end: 100
		    }, {
		    	 type: 'inside',
		        start: 0,
		        end: 10
		    }],
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
    	smooth:true,
		//stack : '总量',
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

function addData(resobj,resValue,resTime) {
	var series=[];
	var xAxis=resTime;
	var totalcpu=[];
	   var now1= resobj.time;
	    xAxis.push(now1);
	   
	  $(resValue).each(function(i){
	     var temp=resValue[i].data;
	     var num=resobj.dataArray[i] 
	     temp.push(num);
	  totalcpu.push(temp);
	  });
	      
	 if (xAxis.length>20) {
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