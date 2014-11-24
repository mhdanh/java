<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="my" uri="/WEB-INF/my.tld" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<c:set var = "myVar" value = "maihuudanh"  />

<h1>${my:doMyStuff(myVar)}</h1>


<span class = "content">
	<spring:message code="msg.client.id" />
</span>
