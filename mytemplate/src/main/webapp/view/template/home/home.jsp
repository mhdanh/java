<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8"/>
		<title><tiles:insertAttribute name = "title" /></title>
		<!-- stylesheets global-->
		<link rel="stylesheet" type="text/css" href="<c:url value='/public/css/lib/bootstrap.css'/>"/>
		<link rel="stylesheet" type="text/css" href="<c:url value='/public/css/lib/bootstrap-theme.css'/>"/>
		<!-- stylesheets -->
		<tiles:importAttribute name="stylesheets"/>
	    <c:forEach var="css" items="${stylesheets}">
	        <link rel="stylesheet" type="text/css" href="<c:url value="${css}"/>">
	    </c:forEach>
		<script>
			var ctxPath = "<%=request.getContextPath() %>";
		</script>
	</head>
	<body>
		
		<!-- content  -->
		<tiles:insertAttribute name = "content"/>
		
		
		
		<!-- javascript global -->
		<script src="<c:url value="/public/js/lib/jquery.js"/>"></script>
		<script src="<c:url value="/public/js/lib/bootstrap.js"/>"></script>
		<!-- javascript -->
		<tiles:importAttribute name="javascripts"/>
		<c:forEach var="script" items="${javascripts}">
	        <script src="<c:url value="${script}"/>"></script>
	    </c:forEach>
	</body>
</html>
