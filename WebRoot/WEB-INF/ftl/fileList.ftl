<#assign path="${request.getContextPath()}">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>视图信息</title>
	<link rel="stylesheet" type="text/css" href="${path}/page/css/frameStyle.css"/>
	<link rel="stylesheet" href="../page/css/bootstrap.min.css"/>
	<script src="${path}/page/js/jquery-1.10.2.min.js" type="text/javascript"></script>
	<script src="${path}/page/js/view/common.js" type="text/javascript"></script>
	<script src="../page/js/bootstrap.min.js" type="text/javascript"></script>
<script  type="text/javascript">
$(document).ready(function() {
$(".form_textbox").hide();
if(totalpage>1){
$(".pagin").show();
}
});
</script>
</head>
<body>
  <div class="container-fluid" style="width:100%; float:left">
<div class="title">

<ul class="placeul">
    <li>${schema}下文件</li>
  </ul>
</div>
<!-- 
<div class="navwrap">
<ul id="nav">
 
<li><a href="#">查询</a></li>

<li><a href="#">编辑</a></li>

<li><a href="#">窗口</a></li>

<li><a href="#">帮助</a></li>

</ul>

</div>-->
<!--  <div class="toolsli">
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
</div>-->

<div class="main_box">
  <div class="itab">
  	<ul> 
    <!--  <li><a id="column"class="selected">栏位</a></li> -->
   <!-- <li><a id="index">索引</a></li> -->
    <li><a id="data" class="selected">数据</a></li> 
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
 <div class="tabson">
<table class="table table-bordered">
  <tr>
    <td><div class="forminfo">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tablelist">
<#assign x=0 />
<#list result as su>
<tr>
<#list su as col>
<#if x==0>
 <th height="40">${col}</th>
<#else>
<td height="40">${col}</td>
</#if>	
</#list>
</tr>
<#assign x=x+1 />
</#list>
</table>

    </div></td>
  </tr>
  <tr><td><div class="pagin">
    	<div class="message">共<i class="blue">${recordCount}</i>条记录，当前显示第&nbsp;<i class="blue" id="currentPage">${currentpage}&nbsp;</i>页</div>
        <ul class="paginList">
        <li class="paginItem"><a onclick="pagebackward()"><img src="../page/images/pre.png" width="28" height="30" id="pre"/></a></li>
        <li class="paginItem"><a onclick="pageForward()"><img src="../page/images/next.png" width="28" height="30" id="next"/></a></li>
        </ul>
    </div></td></tr>
    <tr>
    <td></td>
  </tr>
</table>

 </div>
</div>

</div>
<script type="text/javascript" >
var totalpage=${pageCount};
var current=${currentpage};
function pageForward(){
var next=current+1;
if(next>totalpage){
alter("这是最后一页");
return;
}
urlpath="${path}/command/getBFile.do";
 datapath={schema:"${schema}",pageNum:next};
updateTable(urlpath,datapath);
$("#currentPage").html(next);
}

function pagebackward(){
var next=current-1;
if(next<1){
alter("这是第一页");
return;
}
urlpath="${path}/command/getBFile.do";
 datapath={schema:"${schema}",pageNum:next};
updateTable(urlpath,datapath);
$("#currentPage").html(next);
}
function updateTable(url,urldata){
loadingShow(".tabson");
    $.ajax({
         type: "get",
            dataType: "json",
            data:urldata,
            url: url,
            complete :function(){},
            error:function(data){  
           console.error(data);
        },  
            success: function(msg){
            if(msg.info!=null){
              	var resobj =JSON.parse(msg.info);
              	if(msg.current!=null){
                current=parseInt(msg.current);
                
                if(current==totalpage){
                $("#pre").attr('src','../page/images/pre1.png');
                 $("#next").attr('src','../page/images/next1.png');
                }else if(current==1){
                   $("#pre").attr('src','../page/images/pre.png');
                 $("#next").attr('src','../page/images/next.png');
                }else{
                 $("#pre").attr('src','../page/images/pre1.png');
                 $("#next").attr('src','../page/images/next.png');
                }}
                 $(".tablelist").empty();
                 var value="";
                  for(var i=0;i<resobj.length;i++){
                     value+="<tr>";
                      for(var j=0;j<resobj[i].length;j++){
                          if(i==0){
                           value+='<td width="285"  bgcolor="#6fb3e0"  style="color:#FFF;">'+resobj[i][j]+'</td>'
                          }else{
                          value+='<td height="40">'+resobj[i][j]+'</td>'
                          }
                       }
                       value+="</tr>"
                  }
                  $(".tablelist").html(value);
               loadingHide(".tabson");
                }
            }
    }); 
}         
</script>
</body>

</html>
