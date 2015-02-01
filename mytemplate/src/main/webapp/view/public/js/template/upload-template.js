$(document).ready(function(){
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	
	var msgCanNotBlank = getMsg("msg.validate.text.cannotbeblank");
	var msgChooseFile = getMsgWithParam("msg.validate.text.chooseoption",["file"]);
	var msgChooseThumbnail = getMsgWithParam("msg.validate.text.chooseoption",["thumbnail"]);
	var msgTitleError = getMsg("msg.modal.title.error");
	var msgNotSupport = getMsg("msg.upload-template-file-page.text.filenotsupport");
	var msgInputNumber = getMsg("msg.upload-template-file-page.text.inputnumber");
	
	function ajaxUploadTemplate(){
		var templateUpload = new FormData();
		var titleTemplate = $("#titleTemplate").val().trim();
		var fileTemplate = $("#file-template-upload")[0].files[0];
		var fileThumbnail = $("#file-thumbnail-upload")[0].files[0];
		var fileNameTemplate = $(".txt-name-file-upload").val() + ".zip";
		var fileNameThumbnail = $("#fileNameThumbnail").val();
		var categoryTemplateId = $("#selCategory").val();
		var costTemplate = $("#costTemplate").val();
		
		templateUpload.append("titleTemplate",titleTemplate);
		templateUpload.append("fileNameTemplate",fileNameTemplate.replace(/ /g,'-'));
		templateUpload.append("categoryTemplateId",categoryTemplateId);
		templateUpload.append("fileTemplate",fileTemplate);
		templateUpload.append("fileThumbnail",fileThumbnail);
		templateUpload.append("fileNameThumbnail",fileNameThumbnail);
		templateUpload.append("costTemplate",costTemplate);
		
		
		var labelButtonSumit = $("#upload-file-button").text().trim();
		
		$.ajax({
			url : ctxPath + "/ajax/upload-template-file-page",
			data : templateUpload,
			processData : false,
			contentType : false,
			method : 'POST',
			beforeSend:function(xhr){
				xhr.setRequestHeader(header, token);
				
				$("#upload-file-button").attr("disabled","disabled");
				$("#upload-file-button").text("Uploading...");
			},
			success : function(data) {
				console.log("debug: response from server:");
				console.log(data);
				//update load success
				if(data.state == "uploadsuccess"){
					window.location.href = data.message;
				}else if(data.state == "wrongformat"){
					var msg = data.message;
					var htmlMsg = "<ul class ='eg-template-structure'>" +
										"<li>" +
											"<i class='fa fa-file-archive-o'></i> my-template.zip" +
											"<ul>" +
												"<li><i class ='fa fa-folder-open-o'></i> css</li>" +
												"<li><i class ='fa fa-folder-open-o'></i> js</li>" +
												"<li><i class ='fa fa-folder-open-o'></i> pages</li>" +
												"<li><i class ='fa fa-file-code-o'></i> index.html</li>" +
											"</ul>" +
										"</li>" + 
									"<ul>";
					createModalInformError("Error",msg + htmlMsg);
				}else if(data.state == "notchoosefile"){
					createModalInformError("Error","you haven't choose file yet?");
				}
				$("#upload-file-button").removeAttr("disabled");
				$("#upload-file-button").text(labelButtonSumit);
			}
		});
	}
	
	function messageTemplateUpload(){
		var templateUpload = new FormData();
		var fileTemplate = $("#file-template-upload")[0].files[0];
		var fileNameTemplate = $(".txt-name-file-upload").val() + ".zip";
		var categoryTemplateId = $("#selCategory").val();
		
		templateUpload.append("fileTemplate",fileTemplate);
		templateUpload.append("fileNameTemplate",fileNameTemplate.replace(/ /g,'-'));
		templateUpload.append("categoryTemplateId",categoryTemplateId);
		
		$.ajax({
			url : ctxPath + "/ajax/check-template-upload-state",
			data : templateUpload,
			processData : false,
			contentType : false,
			method : 'POST',
			beforeSend:function(xhr){
				xhr.setRequestHeader(header, token);
			},
			success : function(data) {
				if(data.state == "wrongformat"){
					var msg = data.msg;
					var htmlMsg = "<ul class ='eg-template-structure'>" +
										"<li>" +
											"<i class='fa fa-file-archive-o'></i> my-template.zip" +
											"<ul>" +
												"<li><i class ='fa fa-folder-open-o'></i> css</li>" +
												"<li><i class ='fa fa-folder-open-o'></i> js</li>" +
												"<li><i class ='fa fa-folder-open-o'></i> pages</li>" +
												"<li><i class ='fa fa-file-code-o'></i> index.html</li>" +
											"</ul>" +
										"</li>" + 
									"<ul>";
					createModalInformError("Error",msg + htmlMsg);
				}else{
					if(data.state == "overwriteyourtemplate"){
						$("#info-file-overwrite-template").removeClass("mt-display-none");
					}else{
						$("#info-file-overwrite-template").addClass("mt-display-none");
						$("#error-file-isused-template").addClass("mt-display-none");
					}
				}
				
			}
		});
	}
	
	$(document).on("blur","#fileNameTemplate",function(){
		messageTemplateUpload();
	});
	
	$(document).on("focusin","#fileNameTemplate",function(){
		var errorFileIsUsedTemplate = $("#error-file-isused-template");
		if(!errorFileIsUsedTemplate.hasClass("mt-display-none")){
			errorFileIsUsedTemplate.addClass("mt-display-none");
		}
	});
	
	$(document).on("change","#file-template-upload",function(){
		var file = $("#file-template-upload")[0].files[0];
		if(typeof(file) == "undefined"){
			//clear file name
			$("#fileNameTemplate").val("");
			//hide info
			$("#info-file-overwrite-template").addClass("mt-display-none");
			$("#error-file-isused-template").addClass("mt-display-none");
		}else{
			//hide validate msg
			$("#error-file-template label").hide();
			$("#error-file-template label").hide();
			$("#error-file-name-template label").hide();
			
			var fileName = file.name;
			var allowExtension = ".zip";
			var existExtension = fileName.indexOf(allowExtension);
			if(existExtension == -1){
				createModalInformError(msgTitleError,msgNotSupport);
			}else{
				//var extensionFile = file.
				var nameWithoutExtension = removeFileExtension(fileName);
				nameWithoutExtension = nameWithoutExtension.trim().replace(/ /g,'-');
				$(".txt-name-file-upload").val(nameWithoutExtension);
			}
			messageTemplateUpload();
		}
	});
	
	$(document).on("change","#file-thumbnail-upload",function(){
		var file = $("#file-thumbnail-upload")[0].files[0];
		if(typeof(file) == "undefined"){
			//remove image
			$("#thumbnail").addClass("mt-display-none").children('img').attr("src",'');
			$("#fileNameThumbnail").val("");
		}else{
			var fileName = file.name;
			var allowExtension = "image/*";
			if(file.type.match(allowExtension)){
				var reader = new FileReader();
				reader.readAsDataURL(file);
				reader.onload = function(e){
					//show img
					var containImg = $("#thumbnail");
					$(containImg).removeClass("mt-display-none").children('img').attr("src",e.target.result);
					$("#fileNameThumbnail").val(fileName);
					//hide message
					$("#error-file-thumbnail label").hide();
				};
			}else{
				createModalInformError(msgTitleError,msgNotSupport);
			}
		}
		
	});
	
	$("#frm-upload-template").validate({
		submitHandler: function(form) {
			ajaxUploadTemplate();
			//submitTemplateUpload();
			return false;
		},
	  	rules: {
	  		costTemplate:{
	  			number: true
	  		},
	  		titleTemplate:{
	  			required:true
	  		},
	  		fileTemplateUpload:{
	  			required:true
	  		},
	  		fileNameTemplate:{
	  			required:true
	  		},
	  		fileThumbnailUpload:{
	  			required:true
	  		}
		},
		messages:{
			costTemplate:{
				number: msgInputNumber
			},
			titleTemplate:{
				required: msgCanNotBlank
			},
			fileTemplateUpload:{
				required: msgChooseFile
			},
			fileNameTemplate:{
		    	required: msgCanNotBlank
			},
			fileThumbnailUpload:{
				required: msgChooseThumbnail
			}
		},
		focusInvalid: true,
		errorPlacement: function(error, element) {
			
			if(element.attr("name") == "fileNameTemplate"){
				error.appendTo($("#error-file-name-template"));
			}else if(element.attr("name") == "fileTemplateUpload"){
				error.appendTo($("#error-file-template"));
			}else if(element.attr("name") == "titleTemplate"){
				error.appendTo($("#error-title-template"));
			}else if(element.attr("name") == "fileThumbnailUpload"){
				error.appendTo($("#error-file-thumbnail"));
			}else if(element.attr("name") == "costTemplate"){
				error.appendTo($("#error-cost-template"));
			}else{
		        error.insertAfter(element); // default error placement.
		    }
		}
	});
	
});
