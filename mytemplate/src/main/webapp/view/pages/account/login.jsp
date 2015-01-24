<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class = "row login-page">
	<h2 class = "page-title"><spring:message code = "msg.login.label.title.logintomyui" /></h2>
	<!-- login with register account -->
	<div class = "col-md-7">
		<form name='loginForm' action="<c:url value='j_spring_security_check' />" method='POST' id = "loginForm">
			<!-- username -->
		    <div class="form-group">
		        <label><spring:message code = 'msg.login.label.email'/> *</label>
		        <input type = "text" class = "form-control  mt-no-border-radius" name = "username" id = "username" autofocus="autofocus"/>
		    	<div id = "error-username-login" class = "error-custom"></div>
		    </div>
		    <!-- password -->
		    <div class="form-group">
		        <label><spring:message code = 'msg.login.label.password'/> *</label>
		        <input type = "password" class = "form-control  mt-no-border-radius" name = "password" id = "password"/>
		    	<div id = "error-password-login" class = "error-custom"></div>
		    </div>
		    <div class="form-group">
				<button class="btn btn-primary  mt-button" id = "upload-file-button" type="submit"><spring:message code = 'msg.login.label.signin'/></button>
				<a href = "<c:url value='/forgot-password'/>" class = "login-page-form-login-link-forgot-password"><spring:message code="msg.login.label.forgotpassword"/></a>
			</div>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		</form>
	</div>
	<!-- login with social account -->
	<div class = "col-md-5">
		<!-- login  -->
	    <div class="form-group">
	        <label><spring:message code = 'msg.login.label.loginbyfacebook'/></label>
	        <a href = "<c:url value='/auth/facebook'/>" class = "form-control btn btn-primary mt-button"><i class = "fa fa-facebook-square"></i> Facebook</a>
	    </div>
	    <div class="form-group">
	        <label><spring:message code = 'msg.login.label.loginbygoogleplus'/></label>
	        <button class = "form-control btn btn-primary mt-button"><i class="fa fa-google-plus-square"></i> Google Plus</button>
	    </div>
	</div><!-- end login wrap -->
	<div class = "mt-clear-both"></div>
</div>

