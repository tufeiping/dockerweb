<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="base" value="${pageContext.request.contextPath}" />
<c:set var="active" value="settings" scope="request" />
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<c:import url="./mate-title.jsp" />
</head>
<body>
	<div class="container">
		<c:import url="./nav.jsp" />
		<!-- Default panel contents -->
		<div class="stub">
			<div class="pull-left col-lg-3"></div>
			<div class="pull-left">
				<h3 style="color: red">Sorry!! 我也不知道为什么会进入这个页面......</h3>
			</div>
		</div>
	</div>
	<!-- javascript -->
	<c:import url="./mate-footer.jsp" />
	<script type="text/javascript">
		$(function() {
			$
					.balert({
						url : '#',
						title : '哎呀！！ 出现错误了，也许是Docker服务不正常，您可以尝试进入&nbsp;<a href="${base}/settings">Docker设置</a>&nbsp;页面。',
						type : 'alert',
						div : '.stub'
					});
		});
	</script>
</body>
</html>
