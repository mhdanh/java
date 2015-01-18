// Load the SDK asynchronously
(function(d, s, id) {
	var js, fjs = d.getElementsByTagName(s)[0];
	if (d.getElementById(id))
		return;
	js = d.createElement(s);
	js.id = id;
	js.src = "//connect.facebook.net/en_US/sdk.js";
	fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));

window.fbAsyncInit = function() {
	FB.init({
		appId : '771082552972505',
		cookie : true, // enable cookies to allow the server to access
		// the session
		xfbml : true, // parse social plugins on this page
		version : 'v2.1' // use version 2.1
	});
};

function createModalInformError(title, body) {
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