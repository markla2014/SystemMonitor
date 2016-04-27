<#assign path="${request.getContextPath()}">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>内存信息</title>
	<link rel="stylesheet" type="text/css" href="${path}/page/css/frameStyle.css"/>
	<script src="${path}/page/js/jquery-1.10.2.min.js" type="text/javascript"></script>
	<script src="${path}/page/js/view/common.js" type="text/javascript"></script>
	<script src="${path}/page/js/SimpleTree.js" type="text/javascript"></script>
	</head>
<body>
<div class="title">

<ul class="placeul">
    <li>${runningsql}</li>
  </ul>
</div>
<div id="popm">
		<p></p>
		<div>
			<div>
				<img
					src="../page/images/loading.gif" /><br />
				<span id="tis">数据加载中……</span>
			</div>
		</div>
</div> 
 <Div class="tabson">
<table cellSpacing=0 cellPadding=0 width="100%" align=center border=0  >
  <tr>
    <td><div class="forminfo">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tablelist">
<#assign x=0 />
<#list result as su>
<tr>
<#import "tableCol.ftl" as my/>
<@my.columns col=su cou=x>
</@my.columns>
</tr>
<#assign x=x+1 />
</#list>
</table>
    </div></td>
  </tr>
  <tr>
    <td><div class="pagin">
    	<div class="message">共<i class="blue">${recordCount}</i>条记录，当前显示第&nbsp;<i class="blue" id="currentPage">${currentpage}&nbsp;</i>页</div>
        <ul class="paginList">
        <li class="paginItem"><a onclick="pagebackward()"><img src="../page/images/pre.png" width="28" height="30" id="pre"/></a></li>
        <li class="paginItem"><a onclick="pageForward()"><img src="../page/images/next.png" width="28" height="30" id="next"/></a></li>
        </ul>
    </div></td>
   </tr>
</table>
</Div>
</body>