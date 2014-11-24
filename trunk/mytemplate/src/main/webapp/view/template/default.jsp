<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<head>
		<script src="http://code.jquery.com/jquery-1.9.0.js"></script>
		<script>
			var ctxPath = "<%=request.getContextPath() %>";
		</script>
	</head>
	<body>
		<tiles:insertAttribute name = "title" />
		<hr/>
		<tiles:insertAttribute name = "content"/>
	</body>
</html>
