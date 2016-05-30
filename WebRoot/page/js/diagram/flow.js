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
	color:['#36b8eb','#94eb2a','#3d7dec'],  
	toolbox : {
		show : true,
		feature : {
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

	var series=[];

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
		//stack : '总量',
		smooth:true,
		itemStyle : {
			normal : {
				areaStyle : {
					type : 'default',
					//color:'rgba(0,0,0,0.5)'
				}
			}
		},
		data:[]
};
