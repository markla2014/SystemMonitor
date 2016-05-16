<#assign path="${request.getContextPath()}">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>页面提交</title>
<link rel="stylesheet" type="text/css"
	href="${path}/page/css/frameStyle.css">
<script src="${path}/page/js/jquery-1.10.2.min.js"
	type="text/javascript"></script>
<script src="${path}/page/js/view/common.js" type="text/javascript"></script>
<script type="text/javascript" src="${path}/page/js/menu_min.js"></script>
<script type="text/javascript" src="${path}/page/js/jquery.validate.js"></script>
<script type="text/javascript" src="${path}/page/js/layout.js"></script>
<script src="${path}/page/js/form.js" type="text/javascript"></script>
</head>

<body>

	<div class="title">
		<div class="Titleicon">
			<img src="../page/images/home03.gif" width="18" height="38" />
		</div>
		<ul class="placeul">
			<li>数据库</li>
			<li class="title_text">建立表</li>
		</ul>
	</div>

	<div class="navwrap">
		<ul id="nav">
			<li><input id="table" name="table"></input></li>

			<li><a id="schema">${schema}</a></li>
			<li><input type="hidden" name="schema" value="${schema}"/></li>
		</ul>

	</div>
	<div class="toolsli">
		<ul class="toollist">
			<li><img src="../page/images/icon.gif" width="18" height="38" /></li>
			<li><a onclick="submitForm()">新建</a></li>
			<li><img src="../page/images/main_14.gif" width="1" height="40" /></li>
			<li><img src="../page/images/icon4.gif" width="18" height="38" /></li>
			<li><a onclick="addSelect(this)">添加栏位</a></li>
			<li><img src="../page/images/icon5.gif" width="18" height="38" /></li>
			<li><a onclick="insertTable()">插入栏位</a></li>
			<li><img src="../page/images/icon6.gif" width="18" height="38" /></li>
			<li><a onclick="removeSelect(this)">删除栏位</a></li>
			<li><img src="../page/images/main_14.gif" width="1" height="40" /></li>
			<li><img src="../page/images/icon7.gif" width="18" height="38" /></li>
			<li><a onclick="">主键</a></li>
			<li><img src="../page/images/main_14.gif" width="1" height="40" /></li>
			<li><img src="../page/images/icon8.gif" width="18" height="38" /></li>
			<li><a onclick=" moveUp()">上移</a></li>
			<li><img src="../page/images/icon9.gif" width="18" height="38" /></li>
			<li><a onclick="movedown()">下移</a></li>
		</ul>
	</div>

	<div class="forminfo">
		<form id="thisForm">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				class="tablelist">
				<tr>
					<td width="285" bgcolor="#6fb3e0" style="color:#FFF;">字段</td>
					<td width="125" bgcolor="#6fb3e0" style="color:#FFF;">类型</td>
					<td width="125" bgcolor="#6fb3e0" style="color:#FFF;">长度</td>
					<td width="285" bgcolor="#6fb3e0" style="color:#FFF;">默认值</td>
					<td width="35" bgcolor="#6fb3e0" style="color:#FFF;">可空</td>
					<td width="35" bgcolor="#6fb3e0" style="color:#FFF;">索引</td>
					<td width="35" bgcolor="#6fb3e0" style="color:#FFF;">全文</td>
					<td width="35" bgcolor="#6fb3e0" style="color:#FFF;">主键</td>
					<td width="25" bgcolor="#6fb3e0" style="color:#FFF;">键序</td>
					<td width="35" bgcolor="#6fb3e0" style="color:#FFF;"></td>
				</tr>

				<tbody id="myTable">
					<tr>
						<td><input style="border:0px;" name="colName" id="colName" ></input>
						</td>
						<td><select id="datatype" name="datatype" onchange="typechange()">
								<option value="BFILE">BFILE</option>
								<option value="VARCHAR">VARCHAR</option>
								<option value="BLOB">BLOB</option>
								<option value="BOOLEAN">BOOLEAN</option>
								<option value="CHAR">CHAR</option>
								<option value="DATE">DATE</option>
								<option value="DOUBLE">DOUBLE</option>
								<option value="FLOAT">FLOAT</option>
								<option value="INTEGER" selected="selected">INTEGER</option>
								<option value="LONG">LONG</option>
								<option value="NUMERIC">NUMERIC</option>
								<option value="TIMESTAMP">TIMESTAMP</option>
						</select></td>
						<td><input style="border:0px;" name="length" id="length" readonly="readonly" /></td>
						<td><input style="border:0px;" name="inital" id="inital"></input></td>
						<td><select id="isNull" name="isNull">
								<option value="1">yes</option>
								<option value="0" selected="selected">no</option>
						</select></td>
						<td><select id="isIndex" name="isIndex">
								<option value="1">yes</option>
								<option value="0" selected="selected">no</option>
						</select></td>
						<td><select id="iscover" name="iscover">
								<option value="1">yes</option>
								<option value="0" selected="selected">no</option>
						</select></td>
						<td><select id="isPrimary" name="isPrimary" onchange="primaryChange()">
								<option value="1">yes</option>
								<option value="0" selected="selected">no</option>
						</select></td>
						<td><input style="border:0px;width:25px;" name="keySquence"
							id="keySquence" readonly="readonly"  onchange="primaryChange()"></input></td>
						<td><input type="checkbox" /></td>
					</tr>
				</tbody>

			</table>
		</form>
	</div>
<div class="Errors" style="display:'none'"></div>
	<script language="javascript">
		function addSelect(tbodyID) {
			var bodyObj = document.getElementById("myTable");
			if (bodyObj == null) {
				alert("Body of Table not Exist!");
				return;
			}
			var rowCount = bodyObj.rows.length;
			var cellCount = bodyObj.rows[0].cells.length;
			var newRow = bodyObj.insertRow(rowCount++);
			for (var i = 0; i < cellCount; i++) {
				var cellHTML = bodyObj.rows[0].cells[i].innerHTML;

				if (cellHTML.indexOf("none") >= 0) {
					cellHTML = cellHTML.replace("none", "");
				}
				newRow.insertCell(i).innerHTML = cellHTML;
			}
			// bodyObj.rows[rowCount-1].cells[0].innerHTML = "选项"+rowCount+":"; 
			//  bodyObj.rows[rowCount-1].cells[1].innerHTML =  
			//   bodyObj.rows[rowCount-1].cells[1].innerHTML.replace("value1","selectNames"); 
		}
		function removeSelect() {
			var bodyObj = document.getElementById("myTable");
			var rowCount = bodyObj.rows.length;
			if (rowCount == 2)
				return;
			$(':checkbox').each(function() {
				if ($(this).attr('type') == 'checkbox') {
					var emp = $(this).prop('checked');
					if (emp) {
						$(this).parent().parent().remove();
					}
				}
				;
			});
			// if(inputobj==null) return;  
			// var parentTD = inputobj.parentNode;  
			// var parentTR = parentTD.parentNode;  
			//var parentTBODY = parentTR.parentNode;  
			//parentTBODY.removeChild(parentTR);  
		}
		function movedown() {
			var bodyObj = document.getElementById("myTable");
			var rowCount = bodyObj.rows.length;
			if (rowCount == 2)
				return;
			$(':checkbox').each(function() {
				if ($(this).attr('type') == 'checkbox') {
					var emp = $(this).prop('checked');
					if (emp) {
						var $tr = $(this).parent().parent();
						if ($tr.index() != rowCount - 1) {
							$tr.fadeOut().fadeIn();
							$tr.next().after($tr);
						}
					}
				}
				;
			});
		}
		function moveUp() {
			var bodyObj = document.getElementById("myTable");
			var rowCount = bodyObj.rows.length;
			if (rowCount == 2)
				return;
			$(':checkbox').each(function() {
				if ($(this).attr('type') == 'checkbox') {
					var emp = $(this).prop('checked');
					if (emp) {
						var $tr = $(this).parent().parent();
						if ($tr.index() != 0) {
							$tr.fadeOut().fadeIn();
							$tr.prev().before($tr);
						}
					}
				}
				;
			});
		}
		function insertTable() {
			var bodyObj = document.getElementById("myTable");
			var rowCount = bodyObj.rows.length;
			if (rowCount == 2)
				return;
			$(':checkbox').each(function() {
				if ($(this).attr('type') == 'checkbox') {
					var emp = $(this).prop('checked');
					if (emp) {
						$(this).attr('checked', false);
						var $tr = $(this).parent().parent();
						var copy = $tr.clone(true);
						$tr.after(copy);
					}
				}
				;
			});
		}

		function submitForm() {
			var table = $('#table').val();
			var schema=$("#schema").html();
			var data = $("#thisForm").serializeArray();
			var cols=[];
			var obj={};
			   $.each(data,function(i,param){
		       if(((i+1)%9)==0&&i!=0){
				    obj[param.name]=param.value;
				   cols.push(obj);
				   obj={};
				 
			   }else if((i+1)==data.length){
			       cols.push(obj);
			   }else{
			    obj[param.name]=param.value;
			   }
		   });
			var ajax_option={
		   type: 'post', // 提交方式 get/post
            url: '${path}/command/createTable.do',
            dataType:"json", // 需要提交的 url
            data: {
                'schema': schema,
                'table': table,
                 'cols':JSON.stringify(cols)
            },clearForm: true,
            error:function(data){
             alert(data);
            },
            success: function(data) { // data 保存提交后返回的数据，一般为 json 数据
                // 此处可对 data 作相关处理
                if(data=="success"){
               alert("表已经创建完成");
              $(this).reset();
              //  window.close();  
                }else{
                $(".Errors").html(data);
                $(".Errors").css('display','block'); 
                }
            }// 提交后重置表单
		
		}
			if (table == '') {
				alert("创建表格名不能为空");
			}
			$("input[name='colName']").each(function() {
				if ($.trim($(this).val()) == '')
					alert('第' + ($(this).index + 1) + '个文本框为空');
			});
				$("input[name='length']").each(function() {
			   
			　if(typeof($(this).attr("readonly"))=="undefined"){
	                     var value=$.trim($(this).val());
						 if(value==""){
						   alert("长度不能为空");
						 }
					}
			});
			$("#thisForm").ajaxSubmit(ajax_option);
		}
		
		    function typechange(){
	$("select[name='datatype']").each(function(){
	
		    
		   var selectValue=$(this).children('option:selected').val();
		  
		   if(selectValue=="VARCHAR" || selectValue=="NUMERIC" || selectValue=="CHAR" ){
		      $(this).parent().next().children().removeAttr("readonly");
		   }else{
		        $(this).parent().next().children().val("");
		          $(this).parent().next().children().attr("readonly","readonly");
		   }
		});
	}
	 function primaryChange(){
      $("select[name='isPrimary']").each(function(){

		   var selectValue=$(this).val();
		  
		   if(selectValue==1){
		         $(this).parent().next().children().removeAttr("readonly");
		   }else{
		      $(this).parent().next().children().val("");
		         $(this).parent().next().children().attr("readonly","readonly");
		   }
		});
		}
		function checknum(){
		 $("input[name='keySquence']").each(function(){
		    var test=$(this).val();
			if((!isNaN(test)) || test==""){
			 $(this).val("");
			 console.log($(this).val());
		      alert("请输入数字或者为空");
			}
		 });
		}
	</script>
</body>

</html>
