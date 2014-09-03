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
			<h4 class="modal-title" id="myModalLabel">${title}</h4>
		</div>
		<div class="modal-body">
			<c:choose>
				<c:when test="${title=='添加项目文件'}">
					<form id="projectsform" class="form-horizontal" role="form"
						action="${base}/projects/save" method="post">
				</c:when>
				<c:otherwise>
					<form id="projectsform" class="form-horizontal" role="form"
						action="${base}/projects/update" method="post">
						<input type="hidden" name="id" value="${info.id }" />
				</c:otherwise>
			</c:choose>
			<div class="form-group">
				<label for="inputPassword" class="col-sm-2 control-label">项目名称</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="name" name="name"
						value="${info.name }">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">网络镜像</label>
				<div class="col-sm-10">
					<input class="form-control" name="image" value="${info.image }"
						placeholder="填写docker-hub中的image:tag，优先于Dockefile使用" />
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">构建文件(Dockerfile)</label>
				<div class="col-sm-10">
					<textarea class="form-control" rows="10" id="note" name="note">${info.note }</textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">其他文件</label>
				<div class="col-sm-10">
					<input class="form-control" id="path" name="path"
						value="${info.path }" readonly />
				</div>
			</div>
			</form>
			<input class="btn" id="fileupload" type="file" name="files[]"
				data-url="${base}/projects/upload" multiple> <br>
			<div class="progress">
				<div class="progress-bar" role="progressbar" aria-valuenow="60"
					aria-valuemin="0" aria-valuemax="100" style="width: 0%;"></div>
			</div>
		</div>
		<div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			<button type="button" class="btn btn-primary" id="btnsave">保存</button>
		</div>
	</div>
</div>

<script type="text/javascript">
	$(function() {
		$('#btnsave').click(function() {
			$("#projectsform").submit();
		});
		$('#fileupload').fileupload({
			dataType : 'json',
			done : function(e, data) {
				var name = data.result.names;
				var path = $('#path').val();
				$('#path').val((path == "" ? "" : path + ";") + name);
			},
			progressall : function(e, data) {
				var progress = parseInt(data.loaded / data.total * 100, 10);
				$('.progress .progress-bar').css('width', progress + '%');
			}
		});
	});
</script>
