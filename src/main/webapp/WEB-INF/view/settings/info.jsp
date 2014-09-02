<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="base" value="${pageContext.request.contextPath}" />
<!-- Modal -->
<div class="modal-dialog">
	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">
				<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
			</button>
			<h4 class="modal-title" id="myModalLabel">Docker 信息</h4>
		</div>
		<div class="modal-body">
			<div>
				<label>镜像数量</label>
				<div>${info.images }</div>
			</div>
			<div>
				<label>容器数量</label>
				<div>${info.containers }</div>
			</div>
			<div>
				<label>Debug模式</label>
				<div>${info.debug }</div>
			</div>
			<div>
				<label>Driver</label>
				<div>${info.driver }</div>
			</div>
			<div>
				<label>ExecDriver</label>
				<div>${info.executionDriver }</div>
			</div>
			<div>
				<label>Index服务器</label>
				<div>${info.indexServerAddress }</div>
			</div>
			<div>
				<label>初始化路径</label>
				<div>${info.initPath }</div>
			</div>
			<div>
				<label>内核版本</label>
				<div>${info.kernelVersion }</div>
			</div>
			<div>
				<label>API版本</label>
				<div>${version.apiVersion }</div>
			</div>
			<div>
				<label>平台</label>
				<div>${version.arch }</div>
			</div>

			<div>
				<label>Git提交</label>
				<div>${version.gitCommit }</div>
			</div>
			<div>
				<label>GO版本</label>
				<div>${version.goVersion }</div>
			</div>
			<div>
				<label>操作系统</label>
				<div>${version.operatingSystem }</div>
			</div>
			<div>
				<label>Docker版本</label>
				<div>${version.version }</div>
			</div>
		</div>
		<div class="modal-footer">
			<button type="button" class="btn btn-primary" data-dismiss="modal">关闭</button>
		</div>
	</div>
</div>
