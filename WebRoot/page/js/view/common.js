/**
 * 
 */
function creatMapByXxbh(mes){
          var mes=JSON.parse(mes)
		var str="<table border='1' border='0' cellspacing='0' cellpadding='0' class='tablelist'>";
		for(var i=0;i<mes.length;i++){
		     var xxbn=mes[i];
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
		str+="</table> ";
		var mm=document.getElementById('content');
		 mm.innerHTML=str;
	}
