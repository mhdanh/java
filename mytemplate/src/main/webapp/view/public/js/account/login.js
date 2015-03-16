$(document).ready(function(){
	var msgCanNotBlank = getMsg("msg.validate.text.cannotbeblank");
	
	$("#loginForm").validate({
		submitHandler: function(form) {
			form.submit();
		},
	  	rules: {
	  		username:{
	  			required: true
	  		},
	  		password:{
	  			required:true
	  		}
		},
		messages:{
			username:{
				required: msgCanNotBlank
			},
			password:{
				required: msgCanNotBlank
			}
		},
		focusInvalid: true,
		errorPlacement: function(error, element) {
			if(element.attr("name") == "username") {
				error.appendTo($("#error-username-login"));
			}else if(element.attr("name") == "password") {
				error.appendTo($("#error-password-login"));
			}else {
		        error.insertAfter(element); // default error placement.
		    }
		}
	});
});
