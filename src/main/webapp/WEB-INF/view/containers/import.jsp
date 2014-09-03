<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="base" value="${pageContext.request.contextPath }" />
<!-- Modal -->
<div class="modal-dialog" max-width="300px">
	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">
				<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
			</button>
			<h4 class="modal-title" id="myModalLabel">导入镜像</h4>
		</div>
		<div class="modal-body">
			<div class="alert-success form-control">
				镜像文件都比较大，占用服务器资源较多，因为服务器导入后tar镜像后<br>
				不会删除上传的文件，docker服务器会访问本服务器tar资源，然后导入。
			</div>
			<p>&nbsp;</p>
			<form id="myform" action="${base}/containers/import"
				enctype="multipart/form-data" method="post">
				<dl class="dl-horizontal">
					<dt>镜像名称(小写英文字符)</dt>
					<dd>
						<input class="form-control" name="name" />
					</dd>
					<dt>镜像文件(tar)</dt>
					<dd>
						<input type="file" class="form-control" name="file" />
					</dd>
				</dl>
			</form>
		</div>
		<div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			<button type="button" class="btn btn-primary tart_build"
				data-dismiss="modal">创建</button>
		</div>
	</div>
</div>
<script type="text/javascript">
	$(function() {
		$(".tart_build").click(function() {
			$("#myform").submit();
		});
	});
</script>
