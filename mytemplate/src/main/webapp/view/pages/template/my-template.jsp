<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class = "my-template">
	<table class="table table-striped tb-my-template">
		<thead>
			<tr>
				<th class = "tb-my-template-col-id"><spring:message code = "msg.template.my-temlate.text.id"/></th>
				<th class = "tb-my-template-col-title"><spring:message code = "msg.template.my-temlate.text.title"/></th>
				<th class = "tb-my-template-col-cost"><spring:message code = "msg.template.my-temlate.text.cost"/></th>
				<th class = "tb-my-template-col-status"><spring:message code = "msg.template.my-temlate.text.status"/></th>
				<th class = "tb-my-template-col-date-modified"><spring:message code = "msg.template.my-temlate.text.datemodified"/></th>
				<th class = "tb-my-template-col-owner"><spring:message code = "msg.template.my-temlate.text.owner"/></th>
				<th class = "tb-my-template-col-action"></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="myTemplate" items="${myTemplates}">
				<tr data-id-template = "${myTemplate.id}">
					<td class = "tb-my-template-col-id">${myTemplate.id}</td>
					<td class = "tb-my-template-col-text"><a href="<c:url value = '/${myTemplate.link}'/>" target="_blank" title = "<spring:message code = 'msg.template.my-temlate.text.live.demo'/>">${myTemplate.title}</a></td>
					<td class = "tb-my-template-col-cost">${myTemplate.sellOff}k</td>
					<td class = "tb-my-template-col-status">${myTemplate.status}</td>
					<td class = "tb-my-template-col-date-modified"><fmt:formatDate value="${myTemplate.dateModified}" pattern="dd/MM/yyyy hh:mm"/></td>
					<td class = "tb-my-template-col-owner">${myTemplate.owner.username}</td>
					<td class = "tb-my-template-col-action">
						<a href = "<c:url value = '/template-detail/${myTemplate.id}'/>" target="_blank" class = "glyphicon glyphicon-th-list" title = "<spring:message code = 'msg.template.my-temlate.text.view.detail'/>"></a>
						<a href = "<c:url value = '/${myTemplate.link}'/>" target="_blank" class = "glyphicon glyphicon-eye-open" title = "<spring:message code = 'msg.template.my-temlate.text.live.demo'/>"></a>
						<a href = "#" class = "glyphicon glyphicon-trash action-delete-template" title = "<spring:message code = 'msg.template.my-temlate.text.delete'/>" data-id-template="${myTemplate.id}"></a>
						<a href = "#" class = "glyphicon glyphicon-edit" title = "<spring:message code = 'msg.template.my-temlate.text.edit'/>"></a>
					</td>
				</tr>
			</c:forEach>
			<c:if test = "${empty myTemplates}">
				<tr>
					<td colspan="7">
						<spring:message code = "msg.template.my-temlate.text.notemplate"/>
					</td>
				</tr>
			</c:if>
		</tbody>
	</table>
</div>