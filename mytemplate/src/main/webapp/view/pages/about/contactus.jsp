<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="my" uri="/WEB-INF/my.tld"%>

<div class="row contact-us-page">
	<h2 class="page-title"><spring:message code = 'msg.about.contactus'/></h2>
	<div class="col-md-12 wrap-frm-feedback">
		<form id = "frm-feedback" method="post" name = "feedBackForm" action = "<c:url value ='/contact/add?${_csrf.parameterName}=${_csrf.token}'/>" enctype="multipart/form-data">
			<!-- name -->
			<div class="form-group">
                <label for = "txtFeedbackSubject">Your Name *</label>
                <input type = 'text' class = "form-control  mt-no-border-radius" name = "subjectFeedback" id = "txtYourName"/>
                <div id = "error-seri-number" class = "error-custom"></div>
            </div>
            
            <!-- email -->
			<div class="form-group">
                <label for = "txtFeedbackContent">Your Email *</label>
                <textarea class = "form-control  mt-no-border-radius" name = "contentFeedback" id = "txtFeedbackContent"></textarea>
                <div id = "error-seri-number" class = "error-custom"></div>
            </div>
            
            <!-- subject -->
            <div class="form-group">
                <label for = "txtSubject">Subject *</label>
                <input type = "text" class = 'form-control mt-no-border-radius' name = "" id = "txtSubject"/>
            </div>
            
            <!-- content -->
            <div class="form-group">
                <label for = "txtSubject">Content *</label>
                <input type = "text" class = 'form-control mt-no-border-radius' name = "" id = "txtSubject"/>
            </div>
            
            <!-- attach mentfile -->
			<div class="form-group">
                <label for = "file-feedback-upload">Attachfile *</label>
                <div class="input-group">
                	<input type="file" id="file-feedback-upload"
						class="form-control" name = "fileFeedback">
				</div>
				<div id = "error-file-thumbnail" class = "error-custom"></div>
            </div>
            <div class="form-group">
				<button class="btn btn-primary  mt-button" id = "upload-file-button" type="submit"><spring:message code = 'msg.feedback.addfeedback'/></button>
			</div>
		</form>
	</div>
	
</div>