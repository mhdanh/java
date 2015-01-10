<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class = "row set-new-password-page">
	<h2 class = "page-title"><spring:message code = "msg.set.net.password.title" /></h2>
	<!-- login with register account -->
	<div class = "col-md-12">
		<form name='newpassword' action="<c:url value='/update-new-password/${keyRecoverPassword}'/>" method='POST' id="frmSetNewPassword">
			<!-- email -->
		    <div class="form-group">
		        <label><spring:message code = 'msg.set.net.password.newpassword'/> *</label>
		        <input type = "password" class = "form-control  mt-no-border-radius" name = "password" id = "password" autofocus="autofocus" autocomplete="off"/>
		    	<div id = "error-password" class = "error-custom"></div>
		    </div>
		    <div class="form-group">
				<button class="btn btn-primary  mt-button" id = "upload-file-button" type="submit"><spring:message code = 'msg.set.net.password.update.button'/></button>
			</div>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		</form>
	</div>
</div>

