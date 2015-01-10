<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class = "row register-success-page">
	<h2 class = "page-title"><spring:message code = "msg.register.email.register.success.subject" /></h2>
	<!-- login with register account -->
	<div class = "col-md-12">
		<span>${message}</span>		
	</div>
</div>

