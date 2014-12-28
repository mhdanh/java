$(document).ready(function(){
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	
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
		templateUpload.append("fileNameTemplate",fileNameTemplate);
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
				$("#upload-file-button").removeAttr("disabled");
				$("#upload-file-button").text(labelButtonSumit);
			}
		});
	}
	
	function checkTemplateUploadState(){
		var templateUpload = new FormData();
		var fileNameTemplate = $(".txt-name-file-upload").val() + ".zip";
		var categoryTemplateId = $("#selCategory").val();
		
		templateUpload.append("fileNameTemplate",fileNameTemplate);
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
				if(data.state == "canuse" || data.state == "overwriteyourtemplate"){
					ajaxUploadTemplate();
				}else{
					$("#info-file-overwrite-template").addClass("md-display-none");
					$("#error-file-isused-template").removeClass("mt-display-none");
				}
			}
		});
	}
	
	$(document).on("blur","#fileNameTemplate",function(){
		var templateUpload = new FormData();
		var fileNameTemplate = $(".txt-name-file-upload").val() + ".zip";
		var categoryTemplateId = $("#selCategory").val();
		
		templateUpload.append("fileNameTemplate",fileNameTemplate);
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
				if(data.state == "overwriteyourtemplate"){
					$("#info-file-overwrite-template").removeClass("mt-display-none");
				}else{
					$("#info-file-overwrite-template").addClass("md-display-none");
					$("#error-file-isused-template").removeClass("mt-display-none");
				}
			}
		});
	});
	
	$(document).on("focusin","#fileNameTemplate",function(){
		var errorFileIsUsedTemplate = $("#error-file-isused-template");
		if(!errorFileIsUsedTemplate.hasClass("mt-display-none")){
			errorFileIsUsedTemplate.addClass("mt-display-none");
		}
	});
	
	$(document).on("change","#file-template-upload",function(){
		var file = $("#file-template-upload")[0].files[0];
		var fileName = file.name;
		var allowExtension = ".zip";
		var existExtension = fileName.indexOf(allowExtension);
		if(existExtension == -1){
			alert("extension not support");
		}else{
			//var extensionFile = file.
			var nameWithoutExtension = removeFileExtension(fileName);
			nameWithoutExtension = nameWithoutExtension.trim().replace(/ /g,'-');
			$(".txt-name-file-upload").val(nameWithoutExtension);
		}
	});
	
	$(document).on("change","#file-thumbnail-upload",function(){
		var file = $("#file-thumbnail-upload")[0].files[0];
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
			};
		}else{
			alert("not support");
		}
	});
	
	$("#frm-upload-template").validate({
		submitHandler: function(form) {
			checkTemplateUploadState();
			//ajaxUploadTemplate();
			return false;
		},
	  	rules: {
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
			titleTemplate:{
				required:"You need input title"
			},
			fileTemplateUpload:{
				required:"You need choose file"
			},
			fileNameTemplate:{
		    	required: "You need input name for new template"
			},
			fileThumbnailUpload:{
				required:"You need choose thumbnail"
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
			}else{
		        error.insertAfter(element); // default error placement.
		    }
		}
	});
	
});
