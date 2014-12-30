function createModalInformError(title,body){
	$("#modal-inform-error-title").html(title);
	$("#modal-inform-error-body").html(body);
	$("#modal-inform-error").modal("show");
}


function removeFileExtension(fileName) {
	var lastDot = fileName.lastIndexOf(".");
	if (lastDot == -1) {
		return fileName;
	} else {
		console.log(fileName.substring(0, lastDot));
		return fileName.substring(0, lastDot);
	}
}

function getMsg(key) {
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	var msgForm = new FormData();
	var result = "";
	msgForm.append("key", key);
	$.ajax({
		url : ctxPath + "/ajax/get-msg",
		data : msgForm,
		async : false,
		processData : false,
		contentType : false,
		method : 'POST',
		beforeSend : function(xhr) {
			xhr.setRequestHeader(header, token);
		},
		success : function(data) {
			result = data;
		}
	});
	return result;
}

function getMsgWithParam(key, param) {
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	var msgForm = new FormData();
	var result = "";
	msgForm.append("key", key);
	msgForm.append("param", param);
	$.ajax({
		url : ctxPath + "/ajax/get-msg-with-param",
		data : msgForm,
		async : false,
		processData : false,
		contentType : false,
		method : 'POST',
		beforeSend : function(xhr) {
			xhr.setRequestHeader(header, token);
		},
		success : function(data) {
			result = data;
		}
	});
	return result;
}