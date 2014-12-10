<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="row manage-file-index">
	<div class="col-sm-4">
		<h2 class="title-upload-template"><spring:message code = 'msg.upload-template-file-page.title'/></h2>
		<form id = "frm-upload-template">
			<div class="form-group">
                <label><spring:message code = 'msg.upload-template-file-page.label.choosecategory' /></label>
                <select class = 'form-control input-sm mt-no-border-radius' id = "selCategory">
					<c:forEach items="${categories}" var = "category">
						<option value = "${category.id}"><spring:message code ="${category.name}"/></option>
					</c:forEach>
				</select>
            </div>
            
            <div class="form-group">
                <label><spring:message code = 'msg.upload-template-file-page.label.choosefile'/></label>
                <div class="input-group">
                	<input type="file" id="file-template-upload"
						class="mt-hidden-complete" accept="<spring:message code='msg.support.extension'/>" name = "fileTemplateUpload">
					<input type="text"
						class="form-control input-sm mt-no-border-radius txt-name-file-upload" name="fileNameTemplate" autocomplete="off">
						<label for = "file-template-upload" class="input-group-addon mt-no-border-radius btn btn-primary btn-sm"><spring:message code = 'msg.upload-template-file-page.button.choosezipfile'/></label>
				</div>
				<div id = "error-file-template" class = "error-custom"></div>
				<div id = "error-file-name-template" class = "error-custom"></div>
            </div>
            
            <div class="form-group">
				<div class="checkbox">
					<label> <input type="checkbox"> <spring:message code = 'msg.upload-template-file-page.label.overwrite'/></label>
				</div>
			</div>
			
            <div class="form-group">
				<button class="btn btn-primary btn-sm mt-button" id = "upload-file-button" type="submit"><spring:message code = 'msg.upload-template-file-page.button.upload'/></button>
			</div>
			
		</form>

	</div>
	
</div>