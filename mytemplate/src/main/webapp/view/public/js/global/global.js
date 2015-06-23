function createModalConfirmYesNo(title, body, action) {
	$("#modal-confirm-yes-no-title").html(title);
	$("#modal-confirm-yes-no-body").html(body);
	$("#modal-confirm-yes-no").modal("show");
	$(".modal-confirm-yes-button").on("click",function(){
		console.log("click");
		//action
		action();
		//hide confirm modal
		$("#modal-confirm-yes-no").modal("hide");
		$(".modal-confirm-yes-button").unbind("click");
	});
}

var delayTimeFunction = (function() {
    var timer = 0;
    return function(callback, ms) {
        clearTimeout(timer);
        timer = setTimeout(callback, ms);
    };
})();

//function start and complete ajax
var varIntervalCounter = {};
function startAjaxLoader() {
	$(".ajax-loader").removeClass("mt-display-none");
	var i = 1;
	varIntervalCounter = setInterval(function(){
		$(".ajax-counter-time").text(i++);
	}, 1000);
}

function completeAjaxLoader() {
	$(".ajax-loader").addClass("mt-display-none");
	clearInterval(varIntervalCounter);
}

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