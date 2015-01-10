$(document).ready(function(){
	var msgCanNotBlank = getMsg("msg.validate.text.cannotbeblank");
	
	$("#frmInitAccount").validate({
		submitHandler: function(form) {
			form.submit();
		},
	  	rules: {
	  		lastName:{
	  			required: true
	  		},
	  		firstName:{
	  			required:true
	  		},
	  		username:{
	  			required:true
	  		},
	  		password:{
	  			required:true
	  		}
		},
		messages:{
			lastName:{
				required: msgCanNotBlank
			},
			firstName:{
				required: msgCanNotBlank
			},
			username:{
				required: msgCanNotBlank
			},
			password:{
				required: msgCanNotBlank
			}
		},
		focusInvalid: true,
		errorPlacement: function(error, element) {
			if(element.attr("name") == "lastName") {
				error.appendTo($("#error-last-name"));
			}else if(element.attr("name") == "firstName") {
				error.appendTo($("#error-first-name"));
			}else if(element.attr("name") == "username") {
				error.appendTo($("#error-username"));
			}else if(element.attr("name") == "password") {
				error.appendTo($("#error-password"));
			}else {
		        error.insertAfter(element); // default error placement.
		    }
		}
	});
});