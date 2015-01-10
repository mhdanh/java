$(document).ready(function(){
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	
	var msgCanNotBlank = getMsg("msg.validate.text.cannotbeblank");
	var msgWrongEmailFormat = getMsg("msg.validate.text.email.wrongformat");
	
	$("#frmForgotPassword").validate({
		submitHandler: function(form) {
			var valueEmail = $.trim($("#email").val());
			var checkEmailForRecoverPassword = new FormData();
			checkEmailForRecoverPassword.append("email",valueEmail);
			$.ajax({
				url : ctxPath + "/ajax-forgot-password-check-email",
				data : checkEmailForRecoverPassword,
				async : false,
				processData : false,
				contentType : false,
				method : 'POST',
				beforeSend:function(xhr){
					xhr.setRequestHeader(header, token);
				},
				success : function(data) {
					if(data.key == "notexist" || data.key == "blocked" || data.key == "notactive"){
						$("#error-error-email-recover-password").html("<label>"+data.msg+"<label>");
						$("#error-error-email-recover-password").show();
						return false;
					}else{
						//submit
						form.submit();
					}
				}
			});
		},
	  	rules: {
	  		email:{
	  			required: true,
	  			email:true
	  		}
		},
		messages:{
			email:{
				required: msgCanNotBlank,
				email:msgWrongEmailFormat
			},
		},
		focusInvalid: true,
		errorPlacement: function(error, element) {
			if(element.attr("name") == "email") {
				error.appendTo($("#error-email-recover-password"));
			}else {
		        error.insertAfter(element); // default error placement.
		    }
		}
	});
});