<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="my" uri="/WEB-INF/my.tld"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="refresh" content="900"/>
	<meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    
	<title><spring:message code = "msg.error.system.error"/></title>
	<!-- stylesheets global-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/view/public/css/lib/font-awesome.css'/>"/>
	<link rel="stylesheet" type="text/css" href="<c:url value='/view/public/css/lib/bootstrap.css'/>"/>
	<link rel="stylesheet" type="text/css" href="<c:url value='/view/public/css/lib/bootstrap-theme.css'/>"/>
	<link rel="stylesheet" type="text/css" href="<c:url value='/view/public/css/global/global.css'/>"/>
	<link rel="stylesheet" type="text/css" href="<c:url value='/view/template/home/home.css'/>"/>
	<link rel="stylesheet" type="text/css" href="<c:url value='/view/public/css/error/error.css'/>"/>
</head>
<body>
					
	<jsp:include page = "/view/template/home/header.jsp"/>
	<div class = "main-content">
		<!-- page error -->
			<jsp:include page = "/view/pages/error/error.jsp"/>
		<!-- end page error -->
	</div>
	<jsp:include page = "/view/template/home/footer.jsp"/>
	
	<!-- javascript global -->
	<script src="<c:url value="/view/public/js/lib/jquery.js"/>"></script>
	<script src="<c:url value="/view/public/js/lib/bootstrap.js"/>"></script>
	<script src="<c:url value="/view/public/js/global/global.js"/>"></script>
</body>
</html>