<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<!-- Modal -->
<div class="modal-dialog" max-width="300px">
	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">
				<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
			</button>
			<h4 class="modal-title" id="myModalLabel">${title }</h4>
		</div>
		<div class="modal-body">
			<dl class="dl-horizontal">
				<dt>体系结构</dt>
				<dd><input class="form-control" value="${info.arch }" /></dd>
				<dt>作者</dt>
				<dd><input class="form-control" value="${info.author }" /></dd>
				<dt>内存设置</dt>
				<dd><input class="form-control" value="${info.config.memoryLimit}" /></dd>
				<dt>用户</dt>
				<dd><input class="form-control" value="${info.config.user}" /></dd>
				<dt>容器</dt>
				<dd>
					<input class="form-control" value="${info.container }" />
				</dd>
				<dt>备注</dt>
				<dd><input class="form-control" value="${info.comment }" /></dd>
				<dt>ID</dt>
				<dd>
					<input class="form-control" value="${info.id }" />
				</dd>
				<dt>操作系统</dt>
				<dd><input class="form-control" value="${info.os }" /></dd>
				<dt>父镜像</dt>
				<dd>
					<input class="form-control" value="${info.parent }" />
				</dd>
				<dt>镜像大小</dt>
				<dd><input class="form-control" value="${info.size }" /></dd>
				<dt>CMD</dt>
				<dd>
					<input class="form-control" value="<c:forEach var='cmd' items='${info.config.cmd}'>${cmd} &nbsp;</c:forEach>"/>
				</dd>
				<dt>EntryPoint</dt>
				<dd>
					<input class="form-control" value="<c:forEach var='enp' items='${info.config.entrypoint }'>${enp }&nbsp;</c:forEach>"/>
				</dd>
				<dt>工作目录</dt>
				<dd><input class="form-control" value="${info.config.workingDir }" /></dd>
				<dt>开放端口</dt>
				<dd>
					<input class="form-control" value="<c:forEach var='port' items='${info.config.exposedPorts}'>${port} &nbsp;</c:forEach>"/>
				</dd>
			</dl>
		</div>
		<div class="modal-footer">
			<button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
		</div>
	</div>
</div>
