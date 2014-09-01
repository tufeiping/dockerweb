<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="base" value="${pageContext.request.contextPath}" />

<!-- Static navbar -->
<div class="navbar navbar-default" role="navigation">
	<div class="navbar-header">
		<a class="navbar-brand" href="javascript:void(0);">Docker 管理</a>
	</div>
	<div class="navbar-collapse collapse">
		<ul class="nav navbar-nav">
			<c:choose>
				<c:when test="${active=='projects'}">
					<li class="active"><a href="${base}/projects/list">项目管理</a></li>
				</c:when>
				<c:otherwise>
					<li class="upfile"><a href="${base}/projects/list">项目管理</a></li>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${active=='images'}">
					<li class="active"><a href="${base}/images/list">镜像管理</a></li>
				</c:when>
				<c:otherwise>
					<li class="statusnav"><a href="${base}/images/list">镜像管理</a></li>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${active=='containers'}">
					<li class="active"><a href="${base}/containers/list">容器管理</a></li>
				</c:when>
				<c:otherwise>
					<li class="gatewaynav"><a href="${base}/containers/list">容器管理</a></li>
				</c:otherwise>
			</c:choose>
		</ul>
		<ul class="nav navbar-nav navbar-right">
			<c:choose>
				<c:when test="${active=='settings'}">
					<li class="active dropdown">
				</c:when>
				<c:otherwise>
					<li class="dropdown">
				</c:otherwise>
			</c:choose>
			<a class="dropdown-toggle" href="#" data-toggle="dropdown">设置<span
				class="caret"></span></a>
			<ul class="dropdown-menu" role="menu">
				<li><a href="${base}/settings">Docker设置</a></li>
				<li><a href="${base}/user">用户设置</a></li>
			</ul>
			</li>
			<li><a href="${base}/user/logout">退出</a></li>
		</ul>
	</div>
	<!--/.nav-collapse -->
</div>


