<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="my" uri="/WEB-INF/my.tld" %>

<c:set var = "myVar" value = "maihuudanh"  />

<h1>${my:doMyStuff(myVar)}</h1>
