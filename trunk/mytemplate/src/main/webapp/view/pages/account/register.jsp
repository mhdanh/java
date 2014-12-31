<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class = "row register-page">
	<h2 class = "register-page-title"><spring:message code = "msg.register.label.title" /></h2>
	<!-- login with register account -->
	<div class = "col-md-7">
		<form name='loginForm' action="<c:url value='j_spring_security_check' />" method='POST'>
			<!-- email -->
		    <div class="form-group">
		        <label><spring:message code = 'msg.register.label.input.email'/> *</label>
		        <input type = "text" class = "form-control  mt-no-border-radius" name = "username" id = "username" autofocus="autofocus"/>
		    	<div id = "error-title-template" class = "error-custom"></div>
		    </div>
		    <div class="form-group">
				<button class="btn btn-primary  mt-button" id = "upload-file-button" type="submit"><spring:message code = 'msg.login.label.signin'/></button>
			</div>
		</form>
	</div>
</div>

