<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="my" uri="/WEB-INF/my.tld"%>

<div class="row contact-us-page">
	<h2 class="page-title"><spring:message code = 'msg.about.contactus'/></h2>
	<div class="col-md-12 wrap-frm-contact-us">
		<form id = "frm-contact-us" method="post" name = "contactForm" action = "<c:url value ='/contact/add?${_csrf.parameterName}=${_csrf.token}'/>" enctype="multipart/form-data">
			<!-- name -->
			<div class="form-group">
                <label for = "txtYourNameContact"><spring:message code ='msg.about.yourname'/> *</label>
                <input type = 'text' class = "form-control  mt-no-border-radius" name = "yourNameContact" id = "txtYourNameContact"/>
                <div id = "error-your-name" class = "error-custom"></div>
            </div>
            
            <!-- email -->
			<div class="form-group">
                <label for = "txtYourEmailContact"><spring:message code = 'msg.about.youremail'/> *</label>
                <input type = "email" class = "form-control mt-no-border-radius" name = "yourEmailContact" id = "txtYourEmailContact"/>
                <div id = "error-your-email" class = "error-custom"></div>
            </div>
            
            <!-- subject -->
            <div class="form-group">
                <label for = "txtSubjectContact"><spring:message code = 'msg.about.subject'/> *</label>
                <input type = "text" class = 'form-control mt-no-border-radius' name = "subjectContact" id = "txtSubjectContact"/>
                <div id = "error-subject" class = "error-custom"></div>
            </div>
            
            <!-- content -->
            <div class="form-group">
                <label for = "txtContentContact"><spring:message code = 'msg.about.content'/> *</label>
                <textarea class = "form-control mt-no-border-radius" name = "contentContact" id = "txtContentContact"></textarea>
                <div id = "error-content" class = "error-custom"></div>
            </div>
            
            <!-- attach mentfile -->
			<div class="form-group">
                <label for = "txtAttachmentContact"><spring:message code = 'msg.about.attachment'/> *</label>
                <div class="input-group">
                	<input type="file" id="txtAttachmentContact" class="form-control" name = "fileContact">
				</div>
				<div id = "error-file-attachment" class = "error-custom"></div>
            </div>
            <div class="form-group">
				<button class="btn btn-primary  mt-button" id = "upload-file-button" type="submit"><spring:message code = 'msg.about.sendcontact'/></button>
			</div>
		</form>
	</div>
</div>