<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="row template-edit-description">
	<h2 class = "page-title">edit description</h2>
	<div class = "row">
		<form name = "frmEditLog" action="<c:url value = '/template/edit-log/${template.id}'/>" method="post" id = "frmEditLog" >
			<!-- email -->
		    <div class="form-group">
		    	<textarea name="log" class = "editorLog">${template.log}</textarea>
		    </div>
		    <div class="form-group">
				<button class="btn btn-primary  mt-button" type="submit"><spring:message code = 'msg.button.update'/></button>
			</div>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		</form>
	</div>	
</div>