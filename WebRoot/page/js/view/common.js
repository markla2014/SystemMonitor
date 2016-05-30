/**
 * 
 */
function creatMapByXxbh(mes){
          var mes=JSON.parse(mes)
		var str="<table border='1' border='0' cellspacing='0' cellpadding='0' class='tablelist'>";
		for(var i=0;i<mes.length;i++){
		     var xxbn=mes[i];
		     if(xxbn[0]!=null){
		     str+="<tr>";
			for(var j=0;j<xxbn.length;j++){
			      if(i==0){
			         str+='<td width="285" bgcolor="#6fb3e0" style="color:#FFF;">'+xxbn[j]+"</td>"
			        }else{
					str+="<td height='40'>"+xxbn[j]+"</td>";
					}
			}
			str+="</tr>";
		}
		}
		str+="</table> ";
		var mm=document.getElementById('content');
		 mm.innerHTML=str;
	}
function createMenus(mes){
	var mes=JSON.parse(mes);
	var str="<ul>";
	for(var i=0;i<mes.length;i++){
		var temp=mes[i];
		str+='<li><a><table width="280" border="0" id="thisSchema"><tr><td width="30"><img src="page/images/1_03.png" width="26" height="20" /></td><td>'
		str+=temp+"</td>";
		str+='</tr></table></a><ul style="display: none;"><li><a href="query/getTables.do?schema='+temp+'" target="rightFrame"> <table width="280" border="0"><tr><td width="30"><img src="page/images/2_07.png" width="26" height="20" /></td><td>表</td></tr></table></a>';
		str+='<ul><li><a href="query/createTableInterface.do?schema='+temp+'" target="rightFrame"><table width="280" border="0"><tr><td width="30"><img src="page/images/2_08.png" width="26" height="20" /></td><td>创建表</td></tr></table></a></li></ul></li>';
	     str+='<li><a href="query/getViews.do?schema='+temp+'" target="rightFrame"><table width="280" border="0"><tr><td width="30"><img src="page/images/2_10.png" width="26" height="20" /></td><td>视图</td></tr></table></a>';
	     str+='<ul><li><a href="query/createViewInterface.do?schema='+temp+'" target="rightFrame"><table width="280" border="0"><tr><td width="30"><img src="page/images/2_11.png" width="26" height="20" /></td><td>创建视图</td></tr></table></a></li></ul></li>';
	     str+='<li><a a href="query/getBfileInterface.do?schema='+temp+'" target="rightFrame"><table width="280" border="0"><tr><td width="30"><img src="page/images/Folder.png" width="26" height="20" /></td><td>文件</td></tr></table></a></li>';
	    // str+='<ul><li><a href="" target="rightFrame" ><table width="280" border="0"><tr><td width="30"><img src="page/images/2_16.png" width="26" height="20" /></td><td>创建函数</td></tr></table></a></li></ul></li>';
	     str+='<li><a><table width="280" border="0"><tr><td width="30"><img src="page/images/2_19.png" width="26" height="20" /></td><td>命令行</td></tr></table></a><ul><li><a href="command/getCommandInterface.do" target="rightFrame"><table width="280" border="0"><tr><td width="30"><img src="page/images/2_20.png" width="26" height="20" /></td><td>创建查询</td></tr></table></a></li></ul></li></ul></li>';
	   
	}
	str+='</ul>'
   
	$(".menu").html(str);
}
function createTables(mes){
	//var mes=JSON.parse(mes);
	var str='<table border="0" cellspacing="5" cellpadding="0" width="100%">';
	for(var i=0;i<mes.length;i++){
		str+='  <tr align="center" valign="middle">';
		for(var j=0;j<mes[i].length;j++){
			var value=mes[i][j];
			if(value!=null){
		
			str+=' <td width="10%" ><a class="listbutton grow" onclick="openNewWindow(\''+value+'\');">'+value+'</a></td>';
			}else{
				str+=' <td width="10%" ></td>';
			}
		}
		str+='</tr>';
	}
	str+='</table>';
	var test=$(".listbox");
	var mm=document.getElementById('list');
	 mm.innerHTML=str;
}
function createViews(mes){
	//var mes=JSON.parse(mes);
	var str='<table border="0" cellspacing="5" cellpadding="0" width="100%">';
	for(var i=0;i<mes.length;i++){
		str+='  <tr align="center" valign="middle">';
		for(var j=0;j<mes[i].length;j++){
			var value=mes[i][j];
			if(value!=null){
				var url='getViewDataInterface.do?schema='+schema+'&table='+value+'';
			str+=' <td width="10%" ><a class="listbutton grow" onclick="openViewWindow(\''+url+'\');">'+value+'</a></td>';
			}else{
				str+=' <td width="10%" ></td>';
			}
		}
		str+='</tr>';
	}
	str+='</table>';
	var test=$(".listbox");
	var mm=document.getElementById('list');
	 mm.innerHTML=str;
}
function loadingShow(opDiv){
	$("#popm").css('display','block'); 
	document.getElementById("tis").innerHTML="数据加载中……";
	$(opDiv).css('display','none');
}
function loadingHide(opDiv){
	   document.getElementById("popm").style.display="none";	
       $(opDiv).css('display','block');
}
function openWindow(temp){
	window.open(temp,'_blank','left=280,top=130,height=341,width=420,menubar=no,toolbar=no,location=no,status=no,scrollbars=no');
}
function openViewWindow(temp){
	window.open(temp,'_blank','left=280,top=130,height=800,width=1200,menubar=no,toolbar=no,location=no,status=no,scrollbars=yes');
}
function openWindowtimes(temp,name,po){
	window.open(temp,name,'left='+po+',top=130,height=341,width=420,menubar=no,toolbar=no,location=no,status=no,scrollbars=no');
}
function adjustTable(){var pageHight=$(".main_box").width()-25;
$('.tablelist').tableScroll({height:1024,width:pageHight});}