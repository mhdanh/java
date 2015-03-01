<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="row template-edit-description">
	<h2 class = "page-title">edit description</h2>
	<div class = "row">
		<form name = "frmEditDescription" action="<c:url value = '/template/edit-description/${template.id}'/>" method="post" id = "frmEditDescription" >
			<!-- email -->
		    <div class="form-group">
		    	<textarea name="description" class = "editorDescription">${template.description}</textarea>
		    </div>
		    <div class="form-group">
				<button class="btn btn-primary  mt-button" type="submit"><spring:message code = 'msg.button.update'/></button>
			</div>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		</form>
	</div>	
</div>