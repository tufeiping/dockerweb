<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="base" value="${pageContext.request.contextPath}" />
<c:set var="active" value="images" scope="request" />
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<c:import url="../common/mate-title.jsp" />
<script type="text/javascript">
  function datasizefmt(data) {
	var content = data + "";
	var len = content.length;
	if (len > 6) {
		var m = content.substr(0, len - 6);
		var k = content.substr(len - 6, 3);
		document.write(m + "." + k + " MB");
	} else {
		document.write(content + " B");
	}
  }
  
  function datefmt(tm) {
	  var dt = new Date(parseInt(tm) * 1000).toLocaleString();
	  dt = dt.substr(0, dt.length - 3) ;
	  document.write(dt);
  }
</script>
</head>
<body>
	<div class="container">
		<c:import url="../common/nav.jsp" />
		<div class="panel panel-default">
			<!-- Default panel contents -->
			<div class="panel-heading"></div>
			<table class="table table-striped">
				<thead>
					<tr>
						<th>镜像名</th>
						<th>镜像大小</th>
						<th class="hidden-xs">创建日期</th>
						<th><span class="glyphicon glyphicon-cog"></span>&nbsp;操作</th>
					</tr>
				</thead>
				<c:forEach items="${list}" var="image">
					<tr>
						<td><c:forEach var="tag" items="${image.repoTags}">
						  ${tag }&nbsp;
						</c:forEach></td>
						<td><script type="text/javascript">datasizefmt(${image.virtualSize });</script></td>
						<td class="hidden-xs"><script type="text/javascript">datefmt(${image.created });</script></td>
						<td>
							<button class="btn btn-info btn-xs image_info"
								data="${image.repoTags[0]}">INFO</button>
							<button class="btn btn-default btn-xs image_addc"
								data="${image.repoTags[0]}">容器运行</button>
							<button class="btn btn-danger btn-xs image_delete"
								data="${image.repoTags[0]}">删除</button>
							<button class="btn btn-info btn-xs image_tag" data="${image.id}">命名</button>
							<button class="btn btn-default btn-xs image-save" role="button"
								data="${image.id }">保存</button>
						</td>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>

	</div>

	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true"></div>
	<!-- javascript -->
	<c:import url="../common/mate-footer.jsp" />
	<script type="text/javascript">
		$(function() {
			$(".image_delete").balert({
				url : function(obj){return "${base}/images/" + obj.attr("data") + "/delete";},
				title: "镜像删除后不能恢复，您确定需要删除该镜像吗？"
			});
			
			$('.image_info').click(function() {
				$.post("${base}/images/name/json", {
					name : $(this).attr('data')
				}, function(data) {
					$('#myModal').html(data).modal({
						backdrop : true,
						show : true
					});
				});
			});
			
			$('.image_addc').click(function() {
				$.post("${base}/images/addc", {
					name : $(this).attr('data'),
					rurl : 'containers/list'
				}, function(data) {
					$('#myModal').html(data).modal({
						backdrop : true,
						show : true
					});
				});
			});
			
			$(".image_tag").click(function(){
				$.post("${base}/images/imgtag", {
					id : $(this).attr('data')
				}, function(data){
					$('#myModal').html(data).modal({
						backdrop : true,
						show : true
					});
				});
			});
			$(".image-save").balert({
				url: function(obj) {return "${base}/images/" + obj.attr("data") + "/save"},
				title: '保存镜像资源会占用比较多磁盘空间，是否需要保存镜像？'
			});
		});
	</script>
</body>
</html>
