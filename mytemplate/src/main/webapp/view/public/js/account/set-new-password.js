$(document).ready(function(){
	
	var msgCanNotBlank = getMsg("msg.validate.text.cannotbeblank");
	
	$("#frmSetNewPassword").validate({
		submitHandler: function(form) {
			form.submit();
		},
	  	rules: {
	  		password:{
	  			required: true
	  		}
		},
		messages:{
			password:{
				required: msgCanNotBlank
			},
		},
		focusInvalid: true,
		errorPlacement: function(error, element) {
			if(element.attr("name") == "password") {
				error.appendTo($("#error-password"));
			}else {
		        error.insertAfter(element); // default error placement.
		    }
		}
	});
});