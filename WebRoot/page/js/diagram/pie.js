/**
 * base pie digram
 */

var pieopt = {
	title : {
		text : '',
	},
	tooltip : {
		trigger : 'item',
		formatter : "{b} : {c}G ({d}%)"
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
	series : [ {
		name : 'pie1',
		type : 'pie',
		data : [],
		itemStyle : {
			emphasis : {
				shadowBlur : 200,
				shadowColor : 'rgba(0, 0, 0, 0.5)'
			}
		}
	} ]
};

function getPie(resp) {
	// var resp = "{\"objName\":\"告警\", \"dispName\":[\"已使用\",\"未使用\"],
	// \"value\":[10.0, 20.0], \"id\":[\"111\", \"112\"]}";
	console.log("getParamet"+resp);
	var respobj = JSON.parse(resp); // 数据
	// 饼图的名字从控件选择中拼出来
	var piedata = []; // 饼的数字
	var pieLegend = []; // 饼的图例
	$(respobj.value).each(function(i) {
		var temp=respobj.value[i]/(1024*1024*1024);
		temp=temp.toFixed(2);
		
		piedata.push({
			name : respobj.dispName[i],
			value : temp
		}); // 饼的数据是对象 {name:"str", value:number}
		pieLegend.push(respobj.dispName[i]); // 图例和每块饼的name要一致
	});
	var opt = {
		title : {
			text : respobj.objName
		},
		legend : {
			orient : 'vertical',
			x : 'right',
			y : "bottom",
			data : pieLegend
		},
		series : [ {
			name : "pie1",
			type : 'pie',
			data : piedata
		} ]
	};
	pie.setOption(opt);
}