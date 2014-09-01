<%@tag pageEncoding="UTF-8"%>
<%@ attribute name="page" type="com.love320.zpro.code.bean.Page" required="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="elf" uri="/WEB-INF/tlds/elfunc.tld"%>

		  <div><ul class="pagination">
		    <c:if test="${page.index != elf:pageStart(page,5)+1}">
		    <li><a a href="javascript:void(0);" onclick="GoFormIndex(${page.index-1});"><</a></li>
		    </c:if>
		    <c:forEach var="xx" begin="${elf:pageStart(page,5) == 0 ? 1:elf:pageStart(page,5)}" end="${elf:pageEnd(page,5)+1}" step="1">
		    <li <c:if test="${xx == page.index}" > class="active"</c:if>><a href="javascript:void(0);" onclick="GoFormIndex(${xx});">${xx}</a></li>
			</c:forEach>
		    <c:if test="${elf:pageEnd(page,5) > page.index}">
		    <li><a a href="javascript:void(0);" onclick="GoFormIndex(${page.index +1 });">></a></li>
		    </c:if>
		  </ul></div>
		<script type="text/javascript" >
		function GoFormIndex(pi){
			document.getElementById("formindex").value = pi;
		    document.formsearch.submit();
		}
		</script>

