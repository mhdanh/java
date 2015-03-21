<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="my" uri="/WEB-INF/my.tld"%>

<div class="row about-me-page">
	<h1><spring:message code = 'msg.aboutme.titleabout'/></h1>
	<h4><spring:message code = 'msg.aboutme.description'/></h4>
	<hr/>
	<h3><spring:message code = 'msg.aboutme.request.template.or.website'/></h3>
	<div>
		<spring:message code = 'msg.aboutme.description.request.template.or.website'/>
	</div>
	<hr/>
	<div class = "wrap-skill">
		<spring:message code = 'msg.aboutme.ourskills'/>
		<div class = "wrap-progress">
			<div class = "inside-progress progress-java">JAVA 70%</div>
		</div>
		
		<div class = "wrap-progress">
			<div class = "inside-progress progress-php">PHP 65%</div>
		</div>
		
		<div class = "wrap-progress">
			<div class = "inside-progress progress-js-css">JS & CSS 60%</div>
		</div>
	</div>	
</div>