/**
 * 
 */
var lineopt = {
		title : {
			text : '',
		},
	tooltip : {
		trigger : 'axis',
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
		type : 'value',
		splitNumber : 3,
		precision : 2,
		name : 'MB',
		axisLabel : {
			show : true,
			interval : 10, // {number}
			margin : 5,
			formatter : function(v) {
				// Function formatter
				return v;
				// return v;
			},
			textStyle : {
				color : 'black',
				fontFamily : 'verdana',

			}
		},
		axisLine : { // 轴线
			show : true,
			lineStyle : {
				color : '#999',
				type : 'solid'
			}
		},
		splitLine : {
			show : true,
			lineStyle : {
				type : 'dashed',
			},

		},
	} ],
	series : []
};
function getLine(resp){
	var respobj = JSON.parse(resp);
	var lineLedge=[];
	avgCPU.push(respobj.avgCPU);
	minCPU.push(respobj.minCPU);
	minCPU.push(respobj.avgCPU);
	$(respobj.value).each(function(i) {
		lineLedge.push(respobj.curName[i]);
	});
	 for (var i=0; i<respobj.dataArray.length; i++)
     {
         var new_series = JSON.parse(JSON.stringify(lineserie));  // 可以用预设的不同风格的曲线
         new_series.name = respobj.curveName[i];
         new_series.data = respobj.dataArray[i]; 
         legend.push(respobj.curveName[i]); // 必须和曲线的name一致 每条曲线的名字必须不一样，否则图例会出错
         series.push(new_series);
     }
	var opt = {
			title : {
				text : respobj.objName
			},
			legend : {
				data : lineLegend
			},
			  xAxis:{
	                data:xAxis
	            },
	            serise:serise
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
		data:[0.0]
};
function addData() {
    //now = [now.getHours(), now.getMinutes(), now.getSeconds()].join('');
	count++;
    now1= now.getHours() +":"+ now.getMinutes()
            +":"+ now.getSeconds();
    request_time.push(now1);
    click_size.push((Math.random() - 0.4) * 10 );
	request_size.push(Math.random()*10);
    if (count>10) {
        request_time.shift();
        click_size.shift();
		request_size.shift();
    }
    now = new Date(Date.parse(now) + 24 * 3600 * 1000);
}