<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class = "manage-template">
	<table class="table table-striped tb-manage-template">
		<thead>
			<tr>
				<th class = "tb-manage-template-col-id"><spring:message code = "msg.admin.manage-temlate.text.id"/></th>
				<th class = "tb-manage-template-col-title"><spring:message code = "msg.admin.manage-temlate.text.title"/></th>
				<th class = "tb-manage-template-col-cost"><spring:message code = "msg.admin.manage-temlate.text.cost"/></th>
				<th class = "tb-manage-template-col-status"><spring:message code = "msg.admin.manage-temlate.text.status"/></th>
				<th class = "tb-manage-template-col-date-modified"><spring:message code = "msg.admin.manage-temlate.text.datemodified"/></th>
				<th class = "tb-manage-template-col-owner"><spring:message code = "msg.admin.manage-temlate.text.owner"/></th>
				<th class = "tb-manage-template-col-action"></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="newestTemplate" items="${newestTemplates}">
				<tr>
					<td class = "tb-manage-template-col-id">${newestTemplate.id}</td>
					<td class = "tb-manage-template-col-text"><a href = "<c:url value='/admin/view-template/${newestTemplate.id}'/>" target="_blank" title = "<spring:message code ='msg.admin.manage-temlate.text.live.demo'/>">${newestTemplate.title}</a></td>
					<td class = "tb-manage-template-col-cost">${newestTemplate.sellOff}k</td>
					<td class = "tb-manage-template-col-status">${newestTemplate.status}</td>
					<td class = "tb-manage-template-col-date-modified"><fmt:formatDate value="${newestTemplate.dateModified}" pattern="dd/MM/yyyy hh:mm"/></td>
					<td class = "tb-manage-template-col-owner">${newestTemplate.owner.username}</td>
					<td class = "tb-manage-template-col-action">
						<c:choose>
							<c:when test="${newestTemplate.status eq 'PUBLISHED'}">
								<a href = "#" class = "glyphicon glyphicon-check action-unpublish" data-id = "${newestTemplate.id}" title = "<spring:message code = 'msg.admin.manage-temlate.text.unpublish'/>"></a>
							</c:when>
							<c:otherwise>
								<a href = "#" class = "glyphicon glyphicon-unchecked action-publish" data-id = "${newestTemplate.id}" title = "<spring:message code = 'msg.admin.manage-temlate.text.publish'/>"></a>
							</c:otherwise>
						</c:choose>
						<a href = "<c:url value = '/template-detail/${newestTemplate.id}'/>" target="_blank" class = "glyphicon glyphicon-th-list" title = "<spring:message code = 'msg.admin.manage-temlate.text.view.detail'/>"></a>
						<a href = "<c:url value='/admin/view-template/${newestTemplate.id}'/>" target="_blank" class = "glyphicon glyphicon-eye-open" title = "<spring:message code = 'msg.admin.manage-temlate.text.view'/>"></a>
						<a href = "#" class = "glyphicon glyphicon-trash" title = "<spring:message code = 'msg.admin.manage-temlate.text.delete'/>"></a>
						<a href = "#" class = "glyphicon glyphicon-edit" title = "<spring:message code = 'msg.admin.manage-temlate.text.edit'/>"></a>
					</td>
				</tr>
			</c:forEach>
			<c:if test="${empty newestTemplates}">
				<tr>
					<td colspan="7"><spring:message code = "msg.template.my-temlate.text.notemplate"/></td>
				</tr>
			</c:if>
		</tbody>
	</table>
</div>