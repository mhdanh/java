<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class = "row register-page">
	<h2 class = "page-title"><spring:message code = "msg.register.label.title" /></h2>
	<!-- login with register account -->
	<div class = "col-md-12">
		<form name='register' action="<c:url value='/register'/>" method='POST'>
			<!-- email -->
		    <div class="form-group">
		        <label><spring:message code = 'msg.register.label.input.email'/> *</label>
		        <input type = "text" class = "form-control  mt-no-border-radius" name = "email" id = "email" autofocus="autofocus"/>
		    	<div id = "error-title-template" class = "error-custom"></div>
		    </div>
		    <div class="form-group">
				<button class="btn btn-primary  mt-button" id = "upload-file-button" type="submit"><spring:message code = 'msg.login.label.signin'/></button>
			</div>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		</form>
	</div>
</div>

