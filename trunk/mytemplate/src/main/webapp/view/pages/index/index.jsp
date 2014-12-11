<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="my" uri="/WEB-INF/my.tld"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>


-------------update load file------------------
<input type="file" name="uploadfile" id="upload" multiple />
<br />
<button id="btnUp">Upload</button>
<hr />
<button class="btn btn-default glyphicon glyphicon-asterisk">ok
	man</button>
<!--  -->
<form name='logoutForm'
	action="<c:url value='j_spring_security_logout' />" method='POST'>

	<input type="hidden" name="${_csrf.parameterName}"
		value="${_csrf.token}" />
	<button type="submit">logout</button>
</form>

<c:forEach var="category" items="${my:getCategories()}">
	<li><a href="<c:url value="${category.name}"/>"><spring:message code='${category.name}'/></a></li>
</c:forEach>

<sec:authorize access="hasRole('ADMIN')">
	hi admin
</sec:authorize>