$(document).ready(function(){
	function ajaxUploadTemplate(){
		var formUpload = new FormData();
		var file = $("#file-template-upload")[0].files[0];
		var fileName = $(".txt-name-file-upload").val() + ".zip";
		var categoryId = $("#selCategory").val();
		
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		
		formUpload.append("name",fileName);
		formUpload.append("categoryId",categoryId);
		formUpload.append("file",file);
		
		var labelButtonSumit = $("#upload-file-button").text().trim();
		
		$.ajax({
			url : ctxPath + "/ajax/upload-template-file-page",
			data : formUpload,
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
				console.log(data);
			}
		});
	}
	
	$(document).on("change","#file-template-upload",function(){
		var file = $("#file-template-upload")[0].files[0];
		var fileName = file.name;
		console.log(file);
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
	
	$("#frm-upload-template").validate({
		submitHandler: function(form) {
			ajaxUploadTemplate();
			return false;
		},
	  	rules: {
	  		fileTemplateUpload:{
	  			required:true
	  		},
	  		fileNameTemplate:{
	  			required:true
	  		}
		},
		messages:{
			fileTemplateUpload:{
				required:"You need choose file"
			},
			fileNameTemplate:{
		    	required: "You need input name for new template"
			}
		},
		focusInvalid: true,
		errorPlacement: function(error, element) {
			if(element.attr("name") == "fileNameTemplate"){
				error.appendTo($("#error-file-name-template"));
			}else if(element.attr("name") == "fileTemplateUpload"){
				error.appendTo($("#error-file-template"));
			}else {
		        error.insertAfter(element); // default error placement.
		    }
		}
	});
	
});
