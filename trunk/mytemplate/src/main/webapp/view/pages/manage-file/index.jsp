<div class="row manage-file-index">
	<div class="col-sm-6">
		<h2 class="title-upload-template">Upload template</h2>
		<form class="form-horizontal" id = "frm-upload-template">
			<div class="form-group wrap-input-and-button-choose-file">
				<div class="col-xs-9">
					<div class="input-group">
						<input type="text"
							class="form-control input-sm mt-no-border-radius txt-name-file-upload" name="fileNameTemplate" autocomplete="off"> <span
							class="input-group-addon mt-no-border-radius">*.rar</span>
					</div>
					<div id = "error-file-template" class = "error-custom"></div>
					<div id = "error-file-name-template" class = "error-custom"></div>
				</div>
				<div class="col-sm-3 mt-no-padding-left">
					<input type="file" id="file-template-upload"
						class="mt-hidden-complete" accept=".rar" name = "fileTemplateUpload">
					<label for = "file-template-upload" class="btn btn-primary btn-sm mt-button">Choose File</label>
				</div>
			</div>
			<!-- end form group -->
			<div class="form-group">
				<div class="col-xs-12">
					<div class="checkbox">
						<label> <input type="checkbox"> Overide template
							exist
						</label>
					</div>
				</div>
			</div>
			<!-- end form group -->
			<div class="form-group">
				<div class="col-xs-12">
					<button class="btn btn-primary btn-sm mt-button" id = "upload-file-button" type="submit">Upload</button>
				</div>
			</div>
			<!-- end form group -->
		</form>
	</div>
	
	
</div>