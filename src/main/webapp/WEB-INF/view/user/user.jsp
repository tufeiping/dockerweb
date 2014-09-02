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
		<div class="stub"></div>
		<div class="col-lg-4 col-lg-offset-4">
			<div class="form-group">
				<label>用户名</label> <input class="form-control" id="name"
					value="docker" disabled />
			</div>
			<div class="form-group">
				<label>原始密码</label> <input class="form-control" id="oldpasswd"
					type="password" value="" />
			</div>
			<div class="form-group">
				<label>新密码</label> <input class="form-control" id="passwd"
					type="password" value="" />
			</div>
			<a class="form-control btn btn-primary update">更新用户密码</a>
		</div>
	</div>
	<!-- javascript -->
	<c:import url="../common/mate-footer.jsp" />
	<script type="text/javascript">
		$(function() {
			$(".update").click(function() {
				$.post("${base}/user", {
					name : $("#name").val(),
					oldpasswd : $("#oldpasswd").val(),
					passwd : $("#passwd").val()
				}, function(data) {
					if (data == "ok") {
						$.balert({
							url : '#',
							title : '密码修改成功，下次登录请使用新密码，请牢记！',
							type : 'alert',
							div : '.stub'
						});
					} else {
						$.balert({
							url : '#',
							title : '密码修改失败，请确认原始密码是否正确！',
							type : 'alert',
							div : '.stub'
						});
					}
				});
			});
		});
	</script>
</body>
</html>
