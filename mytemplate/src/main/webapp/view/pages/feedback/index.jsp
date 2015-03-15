<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="my" uri="/WEB-INF/my.tld"%>

<div class="row feedback-page">
	<h2 class="page-title"><spring:message code = 'msg.feedback.title'/></h2>
	<div class="wrap-frm-feedback">
		<form id = "frm-feedback" method="post" name = "feedBackForm" action = "<c:url value ='/feedback/add?${_csrf.parameterName}=${_csrf.token}'/>" enctype="multipart/form-data">
			<!-- type card -->
            <div class="form-group">
                <label for = "selFeedbackType"><spring:message code = 'msg.feedback.type'/> *</label>
                <select class = 'form-control  mt-no-border-radius' id = "selFeedbackType" name = "typeFeedback">
					<c:forEach items="${feedBackTypes}" var = "feedBackType">
						<option value = "${feedBackType}">${feedBackType}</option>
					</c:forEach>
				</select>
            </div>
			<!-- subject form -->
			<div class="form-group">
                <label for = "txtFeedbackSubject"><spring:message code = 'msg.feedback.subject'/> *</label>
                <input type = 'text' class = "form-control  mt-no-border-radius" name = "subjectFeedback" id = "txtFeedbackSubject"/>
                <div id = "error-seri-number" class = "error-custom"></div>
            </div>
            
            <!-- content -->
			<div class="form-group">
                <label for = "txtFeedbackContent"><spring:message code = 'msg.feedback.content'/> *</label>
                <textarea class = "form-control  mt-no-border-radius" name = "contentFeedback" id = "txtFeedbackContent"></textarea>
                <div id = "error-seri-number" class = "error-custom"></div>
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
            <input type = "hidden" name = "idParentFeedback" value = "0"/>
            <div class="form-group">
				<button class="btn btn-primary  mt-button" id = "upload-file-button" type="submit"><spring:message code = 'msg.feedback.addfeedback'/></button>
			</div>
		</form>
	</div>
	<div class = "wrap-feedbacklist">
		<c:forEach items="${feedbacks}" var = "feedback">
			<div class = "parent-feedback">
				<div class = "content-parent-feedback">
					<a href = "">
						<i class="fa fa-user avatar-feedbacker"></i>
					</a>
					<div class = "inside-content-parent">
						<a href = "" class = "name-feedbacker">${feedback.feedbacker.firstName} ${feedback.feedbacker.lastName}</a>
						<span class = "date-feedback">${my:getDateToString(feedback.dateCreated)}</span> <span>${feedback.type}</span>
						<div class = "subject-feedback">
							${feedback.subject}
						</div><!-- end subject -->
						<div>
							${feedback.content}
						</div><!-- end content -->
						<div>
							<c:if test = "${not empty feedback.attachment}">
								<a href = "" class = "attachment-feedback"><i class="fa fa-paperclip"></i>${feedback.attachment.fileName}</a>
							</c:if>
						</div>
						<div>
							<a href = "#" class = "rely-feedback-button"><spring:message code = 'msg.feedback.rely'/></a>
							<a><i class="fa fa-thumbs-up"></i></a>
							<div class = "content-childs-feedback frm-rely-feedback mt-display-none">
									<a href = "">
										<i class="fa fa-user avatar-feedbacker"></i>
									</a>
									<div class = "inside-content-parent">
										<form method="post" name = "frmAddChildFeedback" enctype="multipart/form-data" action = "<c:url value ='/feedback/add?${_csrf.parameterName}=${_csrf.token}'/>">
											<!-- type card -->
								            <div class="form-group">
								                <textarea class = "form-control mt-no-border-radius" name = "contentFeedback"></textarea>
								            </div>
								            <input type = "hidden" value = "${feedback.id}" name = "idParentFeedback"/>
								            <div class="form-group group-file-submit">
								            	<input type = "file" name = "fileFeedback" class = "file-child-feedback"/>
												<button class="btn btn-primary  mt-button" id = "upload-file-button" type="submit">Add Feedback</button>
											</div>
										</form>									
									</div><!-- end wrap content -->
							</div><!-- end content parent feedback -->
							<!-- load child feedback  -->
							<c:forEach items="${feedback.childsFeedback}" var = "childFeedback">
								<div class = "childs-feedback">
									<div class = "content-childs-feedback">
										<a href = "">
											<i class="fa fa-user avatar-feedbacker"></i>
										</a>
										<div class = "inside-content-parent">
											<a href = "" class = "name-feedbacker">${childFeedback.feedbacker.firstName} ${feedback.feedbacker.lastName}</a>
											<span class = "date-feedback">${my:getDateToString(childFeedback.dateCreated)}</span>
											<div>
												${childFeedback.content}
											</div><!-- end content -->
											<div>
												<c:if test = "${not empty childFeedback.attachment}">
													<a href = "" class = "attachment-feedback"><i class="fa fa-paperclip"></i>${childFeedback.attachment.fileName}</a>
												</c:if>
											</div>
											<div>
												<a><i class="fa fa-thumbs-up"></i></a>
											</div>
										</div><!-- end wrap content -->
									</div><!-- end content parent feedback -->
								</div><!-- childs feedback -->
							</c:forEach>
						</div><!-- end wrap rely -->
					</div><!-- end wrap content -->
				</div><!-- end content parent feedback -->
			</div>			
		</c:forEach>
	</div><!-- add feedback -->
</div>