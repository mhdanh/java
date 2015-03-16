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
    
	<title><spring:message code = "msg.error.pagenotfound"/></title>
	<!-- stylesheets global-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/view/public/css/lib/font-awesome.css'/>"/>
	<link rel="stylesheet" type="text/css" href="<c:url value='/view/public/css/lib/bootstrap.css'/>"/>
	<link rel="stylesheet" type="text/css" href="<c:url value='/view/public/css/lib/bootstrap-theme.css'/>"/>
	<link rel="stylesheet" type="text/css" href="<c:url value='/view/public/css/global/global.css'/>"/>
	<link rel="stylesheet" type="text/css" href="<c:url value='/view/template/home/home.css'/>"/>
	<link rel="stylesheet" type="text/css" href="<c:url value='/view/public/css/error/404.css'/>"/>
</head>
<body>
					
	<jsp:include page = "/view/template/home/header.jsp"/>
	<div class = "main-content">
		<!-- page 404 -->
		<div class = "error-404">
			<h2><spring:message code = "msg.error.pagenotfound.title" /></h2>
			<h4><spring:message code = "msg.error.pagenotfound.title.reason" /></h4>
			<div>
				<img src = "<c:url value='/view/public/image/404.png'/>" />
			</div>
			<div>
				<a href = "#" onclick = "history.back();return false;">Previous page</a> | 
				<a href = "<c:url value ='/'/>">Home page</a>
			</div>
		</div>
		<!-- end page 404 -->
	</div>
	<jsp:include page = "/view/template/home/footer.jsp"/>
	
	<!-- javascript global -->
	<script src="<c:url value="/view/public/js/lib/jquery.js"/>"></script>
	<script src="<c:url value="/view/public/js/lib/bootstrap.js"/>"></script>
	<script src="<c:url value="/view/public/js/global/global.js"/>"></script>
</body>
</html>