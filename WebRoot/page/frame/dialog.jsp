<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>重启</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="${pageContext.request.contextPath}/page/js/jquery-1.10.2.min.js"></script>
   <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/page/css/styles.css"/>
   <script type="text/javascript">
   //   function restart(){
     // alert($(this).serialize());
       // window.location.href="${pageContext.request.contextPath}/user/restart.do";
        //window.close();
        //window.opener.close();
      //}
$(document).ready(function(){
 $("form").submit(function(e){
window.location = "${pageContext.request.contextPath}/user/restart.do?"+$(this).serialize(); 
        window.close();
       window.opener.close();
  });
});
</script>
     </script>
    </head>
    <body>
        <form id="myform" method="post" action="${pageContext.request.contextPath}/user/restart.do">
            <p >
           <select id="all" name="all">
           <option value=false>重启数据库</option>
           <option value=true>全部重启</option>
           </select>
            </p>
            <p>
                <input class="submit" type="submit" value="重启"/>
            </p>
        </form>
 </body>
</html>