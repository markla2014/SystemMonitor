<#assign path="${request.getContextPath()}">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>查询信息</title>
	<link rel="stylesheet" type="text/css" href="${path}/page/css/frameStyle.css">
	<link rel="stylesheet" type="text/css" href="${path}/page/css/smartMenu.css">
	<script src="${path}/page/js/jquery-1.10.2.min.js" type="text/javascript"></script>
	<script src="${path}/page/js/view/common.js" type="text/javascript"></script>
	<script src="${path}/page/js/SimpleTree.js" type="text/javascript"></script>
	<script type="text/javascript" src="${path}/page/js/jquery-smartMenu.js"></script>
<script type="text/javascript">
var schema='${schemaName}';
$(document).ready(function(){
var array=${tableList};
createViews(array);
	var obj = [[{
		text:'删除视图',
				func:function(){
				   var table=$(this).children().html()
					location.href = "deleteView.do?schema="+schema+"&view="+table;
				}
			}]];
		var atemp= $("td");
atemp.smartMenu(obj, {
   name: "menus"    
});
});
</script>
</head>
<body>
<div class="title">
<div class="Titleicon"><img src="../page/images/home03.gif" width="18" height="38" /></div>
<ul class="placeul">
    <li>DEFAULT</li>
    <li class="title_text">${schemaName}</li>
  </ul>
</div>
<div class="listbox" id="list">

</div>
</body>
<script type="text/javascript"> 

</script>
</html>