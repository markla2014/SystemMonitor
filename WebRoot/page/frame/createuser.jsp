<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="${pageContext.request.contextPath}/page/js/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/page/js/jquery.validate.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/page/js/form.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/page/js/jquery.format.js"></script>
   <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/page/css/styles.css"/>
   <script type="text/javascript">
        $(function(){  var validate = $("#myform").validate({
                debug: true, //调试模式取消submit的默认提交功能   
                //errorClass: "label.error", //默认为错误的样式类为：error   
                focusInvalid: false, //当为false时，验证无效时，没有焦点响应  
                onkeyup: false,   
                submitHandler: function(form){   //表单提交句柄,为一回调函数，带一个参数：form   
                 form.submit();
                 window.opener = null; 
                  window.close() ;
                },   
                
                rules:{
                    myname:{
                        required:true
                    },
                    password:{
                        required:true,
                        rangelength:[3,10]
                    },
                    confirm_password:{
                        required:true,
                        rangelength:[3,10],
                        equalTo:"#password"
                    }                   
                },
                messages:{
                    myname:{
                        required:"必填"
                    },
                    password:{
                        required: "不能为空",
                        rangelength: $.format("密码最小长度:{0}, 最大长度:{1}。")
                    },confirm_password:{
                        required: "不能为空",
                        rangelength: $.format("密码最小长度:{0}, 最大长度:{1}。"),
                         equalTo: "请再次输入相同的值"
                    }                                
                }
                          
            });    });
        </script>
    </head>

    <body>
        <form id="myform" class="basic-grey" method="post" action="${pageContext.request.contextPath}/user/createUser.do" target="rightFrame">
            <p>
                <label for="myname">用户名:</label>
                <!-- id和name最好同时写上 -->
                <input id="myname" name="myname" />
            </p>
            <p>
                <label for="password">登陆密码：</label>
                <input id="password" name="password" type="password" />
            </p>
             <p >
                <label for="confirm_password">确认登陆密码：</label>
                <input id="confirm_password" name="confirm_password" type="password" />
            </p>
            <p>
                <input class="submit" type="submit" value="创建用户" />
            </p>
        </form>
    </body>
</html>