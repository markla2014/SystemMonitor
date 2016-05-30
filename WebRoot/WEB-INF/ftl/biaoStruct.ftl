<#assign path="${request.getContextPath()}">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>表信息</title>
	<link rel="stylesheet" type="text/css" href="${path}/page/css/frameStyle.css"/>
	<link rel="stylesheet" type="text/css" href="${path}/page/css/jquery.tablescroll.css"/>
	<script src="${path}/page/js/jquery-1.10.2.min.js" type="text/javascript"></script>
	<script src="${path}/page/js/view/common.js" type="text/javascript"></script>
	<script src="${path}/page/js/SimpleTree.js" type="text/javascript"></script>
<script src="../page/js/jquery.tablescroll.js" type="text/javascript"></script>
	<script  type="text/javascript">
$(document).ready(function() {
adjustTable();
});
$(window).resize(function() {
	adjustTable();
	});
</script>
</head>
<body>
<div class="title">

<ul class="placeul">
    <li>${table}表</li>
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
    <li><a id="column"class="selected">表定义</a></li> 
   <!-- <li><a id="index">索引</a></li> -->
    <li><a id="data">表数据</a></li> 
    <li><a id="distribution">小表分布</a></li> 
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
    	<div class="message">共<i class="blue" id="recordCount">${recordCount}</i>条记录，当前显示第&nbsp;<i class="blue" id="currentPage">${currentpage}&nbsp;</i>页</div>
        <ul class="paginList">
        <li class="paginItem"><a onclick="pagebackward()"><img src="../page/images/pre.png" width="28" height="30" id="pre"/></a></li>
        <li class="paginItem"><a onclick="pageForward()"><img src="../page/images/next.png" width="28" height="30" id="next"/></a></li>
        </ul>
    </div></td>
  </tr>
    <tr>
    <td><div class="form_textbox">栏位数: ${rowCount}</div></td>
  </tr>
</table>

 </div>
</Div>
</body>
<script type="text/javascript" >
var totalpage=${pageCount};
var current=$("#currentPage").text().trim();
var urlpath="";
function pageForward(){
var next=current+1;
if(next>totalpage){
alter("这是最后一页");
return;
}
//urlpath="${path}/command/getTableData.do";
datapath={schema:"${schema}",table:"${table}",pageNum:next};
updateTable(urlpath,datapath);
$("#currentPage").html(next);
}

function pagebackward(){
var next=current-1;
if(next<1){
alter("这是第一页");
return;
}
//urlpath="${path}/command/getTableData.do";
datapath={schema:"${schema}",table:"${table}",pageNum:next};
updateTable(urlpath,datapath);
$("#currentPage").html(next);
}

$(".itab li a").click(function(){

$(this).addClass('selected').parent().siblings().children().removeClass('selected');
var tagename=$(this).attr("id");
urlpath="";
var datapath;
if(tagename=='distribution'){
$(".container-fluid").css({width:'100%'});
 urlpath="${path}/query/getTableDistributionpage.do";
 datapath={schema:"${schema}",table:"${table}",pageNum:1};
 	$(".form_textbox").hide();
 	$(".pagin").hide();
}else if(tagename=='data'){
$(".container-fluid").css({width:'auto'});
 urlpath="${path}/command/getTableData.do";
 datapath={schema:"${schema}",table:"${table}",pageNum:1};
 $(".form_textbox").hide();
 if(totalpage>1){  $(".pagin").show(); }
}else if(tagename=='column'){
$(".container-fluid").css({width:'100%'});
urlpath="${path}/query/getTableColumn.do";
datapath={schema:"${schema}",table:"${table}"};
	$(".form_textbox").show();
	$(".pagin").hide();
}
updateTable(urlpath,datapath);
});
function updateTable(url,urldata){
loadingShow(".tabson")
    $.ajax({
         type: "get",
            dataType: "json",
            data:urldata,
            url: url,
            complete :function(){},
            error:function(msg){  
               $(".tablelist").html("数据库返回异常");
               loadingHide(".tabson");
        },  
            success: function(msg){
            if(msg.info!=null){
              	var resobj =JSON.parse(msg.info);
              	$("#recordCount").html(msg.totalcount);
              	if(urlpath.indexOf("TableColumn")<0){
              	totalpage=msg.pageNumber;
              	if(totalpage>1){
              	 $(".pagin").show();
              	}
              	}
              	if(msg.current!=null){
                current=parseInt(msg.current);
               $("#currentPage").html(current);
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
</html>
