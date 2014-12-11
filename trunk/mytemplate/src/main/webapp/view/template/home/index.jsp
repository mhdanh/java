<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="my" uri="/WEB-INF/my.tld"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8"/>
	<meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    
	<title><tiles:insertAttribute name = "title" /></title>
	<!-- stylesheets global-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/view/public/css/lib/bootstrap.css'/>"/>
	<link rel="stylesheet" type="text/css" href="<c:url value='/view/public/css/lib/bootstrap-theme.css'/>"/>
	<link rel="stylesheet" type="text/css" href="<c:url value='/view/public/css/global/global.css'/>"/>
	<link rel="stylesheet" type="text/css" href="<c:url value='/view/template/home/home.css'/>"/>
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
					
	<tiles:insertAttribute name = "header" />
	<tiles:insertAttribute name = "content" />
	<tiles:insertAttribute name = "footer" />
	
	<!-- javascript global -->
	<script src="<c:url value="/view/public/js/lib/jquery.js"/>"></script>
	<script src="<c:url value="/view/public/js/lib/bootstrap.js"/>"></script>
	<script src="<c:url value="/view/public/js/global/global.js"/>"></script>
	<!-- javascript -->
	<tiles:importAttribute name="javascripts"/>
	<c:forEach var="script" items="${javascripts}">
		<script src="<c:url value="${script}"/>"></script>
	</c:forEach>
</body>
</html>