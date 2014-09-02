<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="base" value="${pageContext.request.contextPath}" />
<c:set var="active" value="settings" scope="request" />
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<jsp:include page="../common/mate-title.jsp" />
</head>
<body>
	<div class="container">
		<c:import url="../common/nav.jsp" />
		<div class="panel panel-default">
			<!-- Default panel contents -->
			<div class="panel-heading"></div>
			<div class="stub">
				<form action="" class="myform" method="post" id="form">
					<table class="table table-hover">
						<thead>
							<tr>
								<th>Docker服务IP</th>
								<th>Docker服务端口</th>
								<th class="hidden-xs">操作</th>
							</tr>
						</thead>
						<c:choose>
							<c:when test="${hostport == null}">
								<tr>
									<td><input name="host" value="" /></td>
									<td><input name="port" value="" /></td>
									<td><a class="btn btn-info btn-xs docker_info">INFO</a>&nbsp;<a
										class="btn btn-default btn-xs docker_edit">更新</a></td>
								</tr>
							</c:when>
							<c:otherwise>
								<tr>
									<td><input name="host" value="${hostport.hostIp}" /></td>
									<td><input name="port" value="${hostport.hostPort}" /></td>
									<td><a class="btn btn-info btn-xs docker_info">INFO</a>&nbsp;<a
										class="btn btn-default btn-xs docker_edit">更新</a></td>
								</tr>
							</c:otherwise>
						</c:choose>
					</table>
				</form>
			</div>
		</div>
	</div>
	<div class="modal fade" id="myModal"></div>
	<!-- javascript -->
	<c:import url="../common/mate-footer.jsp" />
	<script type="text/javascript">
		$(function() {
			var url = location.href;
			if (url.indexOf("?") != -1) {
				$.balert({
					url : '#',
					title : '您需要重新设置Docker服务地址和端口，才能正常提供服务。',
					type : 'alert'
				});
			}
			$(".docker_edit").balert({
				title : '更新地址设置后，Docker管理系统会重新连接Docker，是否要更新设置？',
				cb : function(obj) {
					$("#form").submit();
				},
				div : '.stub'
			});

			$(".docker_info").click(function() {
				$.get("${base}/settings/info", function(data) {
					$("#myModal").html(data).modal({
						backdrop : true,
						show : true
					});
				});
			});
		});
	</script>
</body>
</html>
