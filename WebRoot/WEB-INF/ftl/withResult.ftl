<#assign path="${request.getContextPath()}">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${runningsql}</title>
<link rel="stylesheet" type="text/css"
	href="${path}/page/css/frameStyle.css" />
	<link rel="stylesheet" type="text/css" href="${path}/page/css/jquery.tablescroll.css"/>
<script src="${path}/page/js/jquery-1.10.2.min.js"
	type="text/javascript"></script>
<script src="${path}/page/js/view/common.js" type="text/javascript"></script>
<script src="${path}/page/js/SimpleTree.js" type="text/javascript"></script>
<script src="../page/js/jquery.tablescroll.js" type="text/javascript"></script>
<script type="text/javascript">
	var recordCount = ${recordCount};
	var sql = "${runningsql}";
	$(document).ready(function() {
		$(".pagin").show();
		if (recordCount > 20) {
			$(".paginList").show();
		} else {
			$(".paginList").hide();
		}
	var pageHight=$(window).width();
	$('.tablelist').tableScroll({height:1024,width:pageHight});
	});
	$(window).resize(function() {
	var pageHight=$(window).width();
	$('.tablelist').tableScroll({height:1024,width:pageHight});
	});
	$(window).unload(function(){ 
$.get("${path}/command/cancle.do", {"id":id},
    function(req) {
        //成功时的回调方法
    });
}); 
</script>
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
				<img src="../page/images/loading.gif" /><br /> <span id="tis">数据加载中……</span>
			</div>
		</div>
	</div>
	<Div class="tabson">
		<table cellSpacing=0 cellPadding=0 width="100%" align=center border=0>
			<tr>
				<td>
				<div class="forminfo">
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="tablelist">
							<#assign x=0 /> <#list result as su>
							<tr>
								<#list su as col> <#if x==0>
								<th height="40">${col}</th> <#else>
								<td height="40">${col}</td> </#if> </#list>
							</tr>
							<#assign x=x+1 /> </#list>
						</table>
					</div></td>
			</tr>
			<tr>
				<td><div class="pagin">
						<div class="message">
							共<i class="blue">${recordCount}</i>条记录，当前显示第&nbsp;<i class="blue"
								id="currentPage">${current}&nbsp;</i>页
						</div>
						<ul class="paginList">
							<li class="paginItem"><a onclick="pagebackward()"><img
									src="../page/images/pre.png" width="28" height="30" id="pre" /></a></li>
							<li class="paginItem"><a onclick="pageForward()"><img
									src="../page/images/next.png" width="28" height="30" id="next" /></a></li>
						</ul>
					</div></td>
			</tr>
		</table>
	</Div>
	<script type="text/javascript">
		var sql = "${runningsql}";
       var index=${commandId};
		var totalpage = recordCount / 20;
		var current = ${current};
		function pageForward() {
			var next = current + 1;
			if (next > totalpage) {
				alter("这是最后一页");
				return;
			}
			urlpath = "${path}/command/withQueryPage.do";
			datapath = {
			     index : index,
				pageNum : next
			};
			updateTable(urlpath, datapath);
			$("#currentPage").html(next);
		}

		function pagebackward() {
			var next = current - 1;
			if (next < 1) {
				alter("这是第一页");
				return;
			}
			urlpath = "${path}/command/withQueryPage.do";
			datapath = {
				index : index,
				pageNum : next
			};
			updateTable(urlpath, datapath);
			$("#currentPage").html(next);
		}
		function updateTable(url, urldata) {
			loadingShow(".tabson");
			$
					.ajax({
						type : "get",
						dataType : "json",
						data : urldata,
						url : url,
						complete : function() {
						},
						error : function(data) {
							console.error(data);
						},
						success : function(msg) {
							if (msg.info != null) {
								var resobj = JSON.parse(msg.info);
								if (msg.current != null) {
									current = parseInt(msg.current);

									if (current == totalpage) {
										$("#pre").attr('src',
												'../page/images/pre1.png');
										$("#next").attr('src',
												'../page/images/next1.png');
									} else if (current == 1) {
										$("#pre").attr('src',
												'../page/images/pre.png');
										$("#next").attr('src',
												'../page/images/next.png');
									} else {
										$("#pre").attr('src',
												'../page/images/pre1.png');
										$("#next").attr('src',
												'../page/images/next.png');
									}
								}
								$(".tablelist").empty();
								var value = "";
								for (var i = 0; i < resobj.length; i++) {
									value += "<tr>";
									for (var j = 0; j < resobj[i].length; j++) {
										if (i == 0) {
											value += '<th height="40">'
													+ resobj[i][j] + '</th>'
										} else {
											value += '<td height="40">'
													+ resobj[i][j] + '</td>'
										}
									}
									value += "</tr>"
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