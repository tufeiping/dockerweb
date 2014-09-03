<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="base" value="${pageContext.request.contextPath}" />
<c:set var="active" value="containers" scope="request" />
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<c:import url="../common/mate-title.jsp" />
<script type="text/javascript">
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
						<th>容器名</th>
						<th>镜像</th>
						<th class="hidden-xs">创建日期</th>
						<th>状态</th>
						<th><span class="glyphicon glyphicon-cog"></span>&nbsp;操作&nbsp;&nbsp;
							<!-- button class="btn btn-info btn-xs container-import" >导入镜像</button--></th>
					</tr>
				</thead>
				<c:forEach items="${list}" var="container">
					<tr>
						<td><c:forEach var="name" items="${container.names}">
						  ${name }&nbsp;
						</c:forEach></td>
						<td>${container.image }</td>
						<td><script type="text/javascript">datefmt(${container.created });</script></td>
						<td>${container.status }</td>
						<td><button class="btn btn-info btn-xs container-info"
								role="button" data="${container.id}">INFO</button>
							<button class="btn btn-danger btn-xs container-stop"
								role="button" data="${container.id}">停止</button>
							<button class="btn btn-default btn-xs container-restart"
								role="button" data="${container.id}">重启</button>
							<button class="btn btn-danger btn-xs container-kill"
								role="button" data="${container.id}">杀死</button>
							<button class="btn btn-warning btn-xs container-pause"
								role="button" data="${container.id}">暂停</button>
							<button class="btn btn-info btn-xs container-resume"
								role="button" data="${container.id}">恢复</button>
							<button class="btn btn-danger btn-xs container-delete"
								role="button" data="${container.id}">删除</button>
							<button class="btn btn-default btn-xs container-commit"
								role="button" data="${container.id }">提交</button>
							<button class="btn btn-default btn-xs container-log"
								role="button" data="${container.id }">LOG</button>
							<!-- button class="btn btn-default btn-xs container-export"
								role="button" data="${container.id }">导出</button--></td>
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
			$(".container-delete").balert({
				url: function (obj) {return "${base}/containers/" + obj.attr("data") + "/delete";},
				title: '容器删除后不能恢复，您可以尝试停止容器，而非删除，您真的要删除该容器吗？'
			});
	  		
			$(".container-stop").balert({
				url: function(obj){return "${base}/containers/" + obj.attr("data") + "/stop"},
				title: '容器停止后，可以点击“重启”按钮再次启动容器，您真的要停止容器运行吗？'
			});
			
			$(".container-restart").balert({
				url: function(obj){return "${base}/containers/" + obj.attr("data") + "/restart"},
				title: '您要重新启动容器吗？'
			});
			
			$(".container-kill").balert({
				url: function(obj){return "${base}/containers/" + obj.attr("data") + "/kill";},
				title: '您真的杀死选择的容器吗？'
			});
			
			$(".container-pause").balert({
				url: function(obj){return "${base}/containers/" + obj.attr("data") + "/pause"},
				title: '暂停容器后，还可以使用“恢复”功能来恢复容器运行，您真的要暂停容器吗？'
			});
			
			$(".container-resume").balert({
				url: function(obj){return "${base}/containers/" + obj.attr("data") + "/unpause"},
				title: '您真的要恢复容器的运行吗？'
			});
			
			$(".container-commit").balert({
				url: function(obj) {return "${base}/containers/" + obj.attr("data") + "/commit"},
				title: '容器提交后会成为镜像，是否需要提交容器？'
			});
			
			$(".container-export").balert({
				url: function(obj) {return "${base}/containers/" + obj.attr("data") + "/export"},
				title: '保存容器导出资源会占用比较多磁盘空间，是否需要导出容器？'
			});
			
			$(".container-import").click(function(){
				$.get("${base}/containers/import", function(data) {
					$("#myModal").html(data).modal({
						backdrop: true,
						show: true
					});
				});
			});
			
			$(".container-info").click(function() {
				var containerId = $(this).attr("data");
				$.get("${base}/containers/" + containerId + "/info", function(data) {
					$("#myModal").html(data).modal({
						backdrop : true,
						show : true
					});
				});
			});
			
			$(".container-log").click(function(){
				var containerId = $(this).attr("data");
				$.get("${base}/containers/" + containerId + "/log", function(data){
					$("#myModal").html(data).modal({
						backdrop: true,
						show: true
					});
				});
			});
		});
	</script>
</body>
</html>