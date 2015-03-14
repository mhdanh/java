<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!-- page error -->
<div class = "error-page">
	<h2><spring:message code = "msg.error.system.title" /></h2>
	<h4><spring:message code = "msg.error.system.help" /></h4>
	<div>
		<img src = "<c:url value='/view/public/image/error.png'/>" />
	</div>
	<div>
		<a href = "#" onclick = "history.back();return false;">Previous page</a> | 
		<a href = "<c:url value ='/'/>">Home page</a>
	</div>
</div>