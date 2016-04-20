<#assign path="${request.getContextPath()}">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>内存信息</title>
	<link rel="stylesheet" type="text/css" href="${path}/page/css/frameStyle.css">
	<script src="${path}/page/js/jquery-1.10.2.min.js" type="text/javascript"></script>
	<script src="${path}/page/js/view/common.js" type="text/javascript"></script>
	<script src="${path}/page/js/SimpleTree.js" type="text/javascript"></script>
</head>

<body>
<div class="title">

<ul class="placeul">
    <li>${table}表</li>
  </ul>
</div>
<div class="navwrap">
<ul id="nav">
<li><a href="#">查询</a></li>

<li><a href="#">编辑</a></li>

<li><a href="#">窗口</a></li>

<li><a href="#">帮助</a></li>
</ul>

</div>
<div class="toolsli">
  <ul class="toollist">
   <li><img src="../page/images/icon.gif" width="18" height="38" /></li>
    <li><a href="">新建</a></li>
     <li><img src="../page/images/main_16.gif" width="18" height="38" /></li>
      <li><a href="">保存</a></li>
     <li> <img src="../page/images/icon3.gif" width="18" height="38" /></li>
      <li><a href="">另存为</a></li>
     <li><img src="../page/images/main_14.gif" width="1" height="40" /></li>
     <li> <img src="../page/images/icon4.gif" width="18" height="38" /></li>
      <li><a href="">添加栏位</a></li>
           <li> <img src="../page/images/icon5.gif" width="18" height="38" /></li>
      <li><a href="">插入栏位</a></li>
 <li> <img src="../page/images/icon6.gif" width="18" height="38" /></li>
      <li><a href="">删除栏位</a></li>
     <li><img src="../page/images/main_14.gif" width="1" height="40" /></li>
<li> <img src="../page/images/icon7.gif" width="18" height="38" /></li>
      <li><a href="">主键</a></li>
           <li><img src="../page/images/main_14.gif" width="1" height="40" /></li>
      <li> <img src="../page/images/icon8.gif" width="18" height="38" /></li>
      <li><a href="">上移</a></li>     
       <li> <img src="../page/images/icon9.gif" width="18" height="38" /></li>
      <li><a href="">下移</a></li>      
  </ul>
</div>
<div class="main_box">
  <div class="itab">
  	<ul> 
    <li><a class="selected" onclick="isChoose()">栏位</a></li> 
    <li><a id="index">索引</a></li> 
    <li><a id="data">数据</a></li> 
    <li><a id="distribution">分布</a></li> 
  	</ul>
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
    <td>&nbsp;</td>
  </tr>
    <tr>
    <td><div class=" form_textbox">栏位数：</div></td>
  </tr>
</table>

 </div>
</Div>

</body>
<script type="text/javascript" >
$(".itab li a").click(function(){
$(this).addClass('selected').parent().siblings().children().removeClass('selected');
console.log();
});
          
</script>
</html>
