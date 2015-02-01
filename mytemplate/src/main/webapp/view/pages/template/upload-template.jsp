<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="row manage-file-index">
	<div class="col-sm-4">
		<h2 class="title-upload-template"><spring:message code = 'msg.upload-template-file-page.title'/></h2>
		<form id = "frm-upload-template">
			<!-- title template -->
            <div class="form-group">
                <label><spring:message code = 'msg.upload-template-file-page.label.titletemplate'/> *</label>
                <input type = "text" class = "form-control  mt-no-border-radius" name = "titleTemplate" id = "titleTemplate" autofocus="autofocus"/>
            	<div id = "error-title-template" class = "error-custom"></div>
            </div>
			<!-- category -->
			<div class="form-group">
                <label><spring:message code = 'msg.upload-template-file-page.label.choosecategory' /> *</label>
                <select class = 'form-control  mt-no-border-radius' id = "selCategory">
					<c:forEach items="${categories}" var = "category">
						<option value = "${category.id}"><spring:message code ="${category.name}"/></option>
					</c:forEach>
				</select>
            </div>
            <!-- upload file  -->
            <div class="form-group">
                <label><spring:message code = 'msg.upload-template-file-page.label.choosefile'/> *</label>
                <div class="input-group">
                	<input type="file" id="file-template-upload"
						class="mt-hidden-complete" accept="<spring:message code='msg.support.extension'/>" name = "fileTemplateUpload">
					<input type="text"
						class="form-control  mt-no-border-radius txt-name-file-upload" name="fileNameTemplate" id = "fileNameTemplate"  autocomplete="off">
						<label for = "file-template-upload" class="input-group-addon mt-no-border-radius btn btn-primary  btn-upload"><spring:message code = 'msg.upload-template-file-page.button.choosezipfile'/></label>
				</div>
				<div id = "info-file-overwrite-template" class = "info-custom mt-display-none">
					<label><spring:message code = 'msg.upload-template-file-page.text.overwriteyourtemplate'/></label>
				</div>
				<div id = "error-file-template" class = "error-custom"></div>
				<div id = "error-file-name-template" class = "error-custom"></div>
            </div>
            <!-- upload thumbnail -->
            <div class="form-group">
                <label><spring:message code = 'msg.upload-template-file-page.label.thumbnail' /> *</label>
                <div class="input-group">
                	<input type="file" id="file-thumbnail-upload"
						class="mt-hidden-complete" accept="image/*" name = "fileThumbnailUpload">
					<input type="text"
						class="form-control  mt-no-border-radius" name="fileNameThumbnail" id = "fileNameThumbnail" disabled>
						<label for = "file-thumbnail-upload" class="input-group-addon mt-no-border-radius btn btn-primary  btn-upload"><spring:message code = 'msg.upload-template-file-page.button.choosefile'/></label>
				</div>
				<div id = "error-file-thumbnail" class = "error-custom"></div>
				<div class = "thumbnail mt-display-none" id = "thumbnail">
					<img alt = "thumbnail template"/>
				</div>
            </div>
            <!-- cost -->
            <div class="form-group">
                <label><spring:message code = 'msg.upload-template-file-page.label.cost'/></label>
                <div class="input-group">
					<input type="text"
						class="form-control  mt-no-border-radius" name="costTemplate" id = "costTemplate" value="0">
						<span class="input-group-addon mt-no-border-radius">x 1000VND</span>
				</div>
				<p class="help-block"><spring:message code = 'msg.upload-template-file-page.text.freetemplate'/>.</p>
            	<div id = "error-cost-template" class = "error-custom"></div>
            </div>
            
            <div class="form-group">
				<button class="btn btn-primary  mt-button" id = "upload-file-button" type="submit"><spring:message code = 'msg.upload-template-file-page.button.upload'/></button>
			</div>
		</form>
	</div>
	
</div>