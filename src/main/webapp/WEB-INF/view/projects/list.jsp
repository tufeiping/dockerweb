<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="base" value="${pageContext.request.contextPath}" />
<c:set var="active" value="projects" scope="request" />
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<c:import url="../common/mate-title.jsp" />
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
						<th>项目名</th>
						<th>运行环境</th>
						<th class="hidden-xs">日期</th>
						<th><span class="glyphicon glyphicon-cog"></span>&nbsp;操作&nbsp;&nbsp;&nbsp;&nbsp;
							<button class="btn btn-default btn-xs project-add">
								<span class="glyphicon glyphicon-plus"></span>&nbsp;添加
							</button></th>
					</tr>
				</thead>
				<c:forEach items="${list}" var="project">
					<tr>
						<td>${project.name}</td>
						<td>${project.image}</td>
						<td>${project.date_time }</td>
						<td>
							<button class="btn btn-default btn-xs project_edit"
								data="${project.id}">编辑</button>
							<button class="btn btn-danger btn-xs project-delete"
								data="${project.id}">删除</button>
							<button class="btn btn-default btn-xs project_build"
								data="${project.id}">生成镜像</button>
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
	<script src="${base}/js/jquery.ui.widget.js"></script>
	<script src="${base}/js/jquery.fileupload.js"></script>
	<script type="text/javascript">
		$(function() {
			$(".project-delete").balert({
				url : function(obj) {
					return "${base}/projects/delete/" + obj.attr("data");
				},
				title : '删除项目信息不会影响已经创建的资源，您确定需要删除该项目信息吗？'
			});
			$('.project-add').click(function() {
				$.get("${base}/projects/input", function(data) {
					$('#myModal').html(data).modal({
						backdrop : true,
						show : true
					});
				});
			});
			$('.project_edit').click(
					function() {
						$.get("${base}/projects/input/" + $(this).attr("data"),
								function(data) {
									$('#myModal').html(data).modal({
										backdrop : true,
										show : true
									});
								});
					});
			$(".project_build")
					.balert(
							{
								cb : function(obj) {
									var imgname = $("#imgname").val();
									if (imgname == '')
										return $.balert({
											type : 'alert',
											title : '没有输入镜像名，请重新操作。'
										});
									$.post("${base}/projects/build", {
										id : obj.attr("data"),
										imgtag : imgname
									}, function(data) {
										location.href = "${base}/images/list";
									});
								},
								title : '请输入镜像名<input id="imgname" placeholder="请输入小写英文标题"/>&nbsp;然后点击“确定”创建'
							});
		});
	</script>
</body>
</html>
