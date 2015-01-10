<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class = "row init-account-page">
	<h2 class = "page-title"><spring:message code = "msg.register.label.title" /></h2>
	<!-- login with register account -->
	<div class = "col-md-12">
		<form name='initaccount' action="<c:url value='/init-account-update/${paramToken}'/>" method='POST' id="frmInitAccount">
			<!-- name -->
		    <div class="form-group">
		    	<div class = "col-md-6 col-last-name">
		    		<label><spring:message code = 'msg.initaccount.label.lastname'/> *</label>
		        	<input type = "text" class = "form-control  mt-no-border-radius" name = "lastName" id = "lastName" autofocus="autofocus"/>
		    		<div id = "error-last-name" class = "error-custom"></div>
		    		<p class="help-block"><spring:message code = 'msg.initaccount.text.example'/>. Nguyen</p>
		    	</div>
		    	<div class = "col-md-6 col-first-name">
		    		<label><spring:message code = 'msg.initaccount.label.firstname'/> *</label>
		        	<input type = "text" class = "form-control  mt-no-border-radius" name = "firstName" id = "firstName"/>
		    		<div id = "error-first-name" class = "error-custom"></div>
		    		<p class="help-block"><spring:message code = 'msg.initaccount.text.example'/>. Van A</p>
		    	</div>
		    	<div class = "mt-clear-both"></div>
		    </div>
		    <!-- user name -->
		    <div class="form-group">
	    		<label><spring:message code = 'msg.initaccount.label.usernameoremail'/> *</label>
	        	<input type = "text" class = "form-control  mt-no-border-radius" name = "username" id = "username"/>
	    		<div id = "error-username" class = "error-custom"></div>
		    </div>
		    
		    <!-- user name -->
		    <div class="form-group">
	    		<label><spring:message code = 'msg.initaccount.label.password'/> *</label>
	        	<input type = "password" class = "form-control  mt-no-border-radius" name = "password" id = "password"/>
	    		<div id = "error-password" class = "error-custom"></div>
		    </div>
		    
		    <div class="form-group">
				<button class="btn btn-primary  mt-button" id = "init-account-save-button" type="submit"><spring:message code = 'msg.button.save'/></button>
				<button class="btn btn-default  mt-button" type="reset"><spring:message code = 'msg.button.clear'/></button>
			</div>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		</form>
	</div>
</div>

