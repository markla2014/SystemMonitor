<#assign path="${request.getContextPath()}">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>模式建立</title>
	<link rel="stylesheet" href="../page/css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="../page/css/frameStyle.css">
	<script src="${path}/page/js/jquery-1.10.2.min.js" type="text/javascript"></script>
	<script src="${path}/page/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="${path}/page/js/form.js" type="text/javascript"></script>
<body>
<div class="container">
<div class="title">
<div class="Titleicon"><img src="../page/images/home03.gif" width="18" height="38" /></div>
<ul class="placeul">
    <li>数据库</li>
    <li class="title_text">建立模式</li>
  </ul> 
</div>

<form role="form" class="form-horizontal">
   <div class="form-group">
      <label for="schema" class="col-sm-2 control-label">名字</label>
      <div class="col-sm-10">
         <input type="text" class="form-control" name="schema" id="schema" 
            placeholder="模式名">
      </div>
      <label for="name" class="col-sm-2 control-label">选择列表</label>
	  <div class="col-sm-10">
      <select class="form-control" name="name" id="name">
    <#list userlist as user>
    <option>${user}</option>
     </#list>
      </select>
	  </div>
      </div>
   <div class="form-group">
      <div class="col-sm-offset-2 col-sm-10">
         <button type="submit" class="btn btn-default">创建</button>
      </div>
   </div>
</form>
<div class="Errors" style="display:'none'"></div>
</body>
<script type="text/javascript">
    $('form').on('submit', function() {
        var schema = $("#schema").val();
         var table = $("#name").find("option:selected").text();

        $(this).ajaxSubmit({
            type: 'get', // 提交方式 get/post
            url: '${path}/query/createSchema.do', // 需要提交的 url
            data: {
                'schmema': schema,
                'table': table
            },
            success: function(data) { // data 保存提交后返回的数据，一般为 json 数据
                // 此处可对 data 作相关处理
                //$("form").reset();
                if(data=="success"){
              opener.location.href="${path}/query/getSchema.do";
              window.opener=null;
              window.close();
              //  window.close();  
                }else{
                $(".Errors").html(data);
                $(".Errors").css('display','block'); 
                }
            }// 提交后重置表单
        });
        return false; // 阻止表单自动提交事件
    });
</script>
</html>
</body>
</html>