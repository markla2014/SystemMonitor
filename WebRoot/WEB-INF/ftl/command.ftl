<#assign path="${request.getContextPath()}">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>命令行</title>
<link rel="stylesheet" type="text/css" href="${path}/page/css/frameStyle.css"/>
<script src="${path}/page/js/jquery-1.10.2.min.js" type="text/javascript"></script>
<script src="${path}/page/js/view/common.js" type="text/javascript"></script>
<script  type="text/javascript">
function onDownload(){
var input=$("#commandInput").val();
var url='${path}/query/download.do?commandInput='+input;
window.location.href=url;
}
</script>
</head>
<body>
<div class="title">
<div class="Titleicon"><img src="../page/images/home03.gif" width="18" height="38" /></div>
<ul class="placeul">
    <li>数据库</li>
    <li >命令</li>
   <li class="title_text">命令行</li>
  </ul>  
</div>
<div class="titlebg">
<ul>
<li><img src="../page/images/main_05.gif" width="24" height="40" /></li>
<li>命令</li>
</ul>

</div>
<div class="toolsli">
  <ul class="toollist">
   <li><img src="../page/images/main_08.gif" width="18" height="38" /></li>
    <li><a onclick="splitString()">运行</a></li>
       <li><img src="../page/images/main_10.gif" width="18" height="38" /></li>
      <li><a onclick="onClear()">清空</a></li>
  <!--   <li> <img src="../page/images/main_12.gif" width="18" height="38" /></li>
      <li><a href="">导出向导</a></li>
     <li><img src="../page/images/main_14.gif" width="1" height="40" /></li>-->
     <li> <img src="../page/images/main_16.gif" width="18" height="38" /></li>
      <li><a onclick="onDownload()">保存</a></li>
  </ul>
</div>
<div class="main_box">
<div class="itab">
  	<ul> 
  <!--   <li><a href="#tab1">查询创建工具</a></li> -->
    <li><a href="#tab2" class="selected">查询编辑器</a></li> 
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
<table cellSpacing=0 cellPadding=0 width="100%" align=center border=0 >
  <tr>
    <td id="commandarea"><textarea class="forminfo" style=" width:100%; height:100%;" id="commandInput"></textarea></td>
  </tr>
</table>

 </div>
 
 <div class="itab">
  	<ul> 
   <!--   <li><a>信息</a></li>
    <li><a class="selected">结果</a></li> 
    <!-- <li><a>概况</a></li>  -->
    <li><a class="selected">状态</a></li> 
  	</ul>
  </div>
 <Div class="tabson">
<table cellSpacing=0 cellPadding=0 width="100%" align=center border=0 >
  <tr>
    <td><textarea class="forminfo" style=" width:100%; height:100%;" id="runOutcome" readonly="readonly"></textarea></div></td>
  </tr>
  <!--  
  <tr>
    <td><div class="formtools">
    <ul>
    <li><a href="#"><img src="../page/images/tool_10.gif" width="11" height="20" /></a></li>
       <li><a href="#"><img src="../page/images/tool_12.gif" width="11" height="20" /></a></li>
           <li><a href="#"><img src="../page/images/tool_14.gif" width="11" height="20" /></a></li>
     <li><a href="#"><img src="../page/images/tool_16.gif" width="11" height="20" /></a></li>
        <li><a href="#"><img src="../page/images/tool_18.gif" width="11" height="20" /></a></li> 
          <li><a href="#"><img src="../page/images/tool_20.gif" width="11" height="20" /></a></li> 
    <li><a href="#"><img src="../page/images/tool_22.gif" width="11" height="20" /></a></li>
      <li><a href="#"><img src="../page/images/tool_24.gif" width="11" height="20" /></a></li>  
      <li><a href="#"><img src="../page/images/tool_26.gif" width="11" height="20" /></a></li>  
      <li><a href="#"><img src="../page/images/tool_28.gif" width="11" height="20" /></a></li>  
       <li><a href="#"><img src="../page/images/tool_30.gif" width="11" height="20" /></a></li>    
    </ul>
    </div></td>
  </tr>
    <tr>
    <td><div class=" form_textbox">select * from t_adm_county</div></td>
  </tr>-->
</table>

 </div>
</Div>   
</Div>   
</div>

</body>
<script type="text/javascript" >
$(".itab li a").click(function(){
$(this).addClass('selected').parent().siblings().children().removeClass('selected');
});
function addNotice(notice){
var annount=$("#runOutcome").val();
annount+=notice+"\n";
$("#runOutcome").val(annount);
}
function onClear(){
$("#runOutcome").val("");
$("#commandInput").val("");
}
 function splitString(){
   $("#runOutcome").val("");
 var commandString=$("#commandInput").val();
 if($.trim(commandString)==""){
   $("#runOutcome").val("不能为空");
 }
 var commands=$.trim(commandString).replace(/[\r\n]/g,"").split(";");
 var selectPattern=/^(select|SELECT).*/;
 var insertPattern=/^(INSERT|insert).*/;
 var desPattern=/^(DESC|desc).*/;
 var createPattern=/^(create|CREATE).*/;
 var dropPattern=/^(drop|DROP).*/;
 var deletePattern=/^(delete|DELETE).*/;
 var alterPattern=/^(alter|ALTER).*/;
 var withPattern=/^(with|WITH).*/;
  $.each(commands,function(i,key){
  if(key==""||key=='undefined'){
       commands.splice(i,i);
 }
 });
 $.each(commands,function(i,key){
  addNotice("当前运行语句 "+key);
  if( withPattern.test(key)){
   var url="${path}/command/withQuery.do?sql="+key;
     addNotice("运行成功请到页面查看");
     var postitation=280-i;
    openWindowtimes(url,url+i,postitation);
  }else if(selectPattern.test(key)){
    var url="${path}/command/getSreachquery.do?sql="+key;
     addNotice("运行成功请到页面查看");
     var postitation=280-i;
    openWindowtimes(url,url+i,postitation);
 }else if(insertPattern.test(key) || desPattern.test(key) || createPattern.test(key) || dropPattern.test(key) || deletePattern.test(key) || alterPattern.test(key)){
    var url="${path}/command/getGernalCommand.do?sql="+key;
    sendRequest(url)
 }else{
   addNotice("又不是以正规开头的sql 语句");
   return;
 }
 });
 }  
   function sendRequest(urlpath){
   loadingShow("#commandarea");
       $.ajax({
         type: "get",
            dataType: "json",
            url: urlpath,
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            complete :function(){},
            error:function(data){  
            addNotice(data);
              loadingHide("#commandarea"); },  
            success: function(msg){
          
                      if(msg.result=="success"){
                         addNotice('运行成功');
                      }else{
                        addNotice(msg.result);
                      }         
              loadingHide("#commandarea");
            }
            });
   
   }      
</script>
</html>
