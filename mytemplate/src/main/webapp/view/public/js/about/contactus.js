$(document).ready(function() {
	var msgCanNotBlank = getMsg("msg.validate.text.cannotbeblank");
	var msgWrongEmailFormat = getMsg("msg.validate.text.email.wrongformat");
	
	$("#frm-contact-us").validate({
	  	rules: {
	  		yourNameContact:{
	  			required: true
	  		},
	  		yourEmailContact:{
	  			required: true,
	  			email:true
	  		},
	  		subjectContact:{
	  			required: true
	  		},
	  		contentContact:{
	  			required: true
	  		},
		},
		messages:{
			yourNameContact:{
				required: msgCanNotBlank
			},
			subjectContact:{
				required: msgCanNotBlank
			},
			contentContact:{
				required: msgCanNotBlank
			},
			yourEmailContact:{
				required: msgCanNotBlank,
				email:msgWrongEmailFormat
			},
		},
		focusInvalid: true,
		errorPlacement: function(error, element) {
			if(element.attr("name") == "yourNameContact") {
				error.appendTo($("#error-your-name"));
			} else if(element.attr("name") == "yourEmailContact") {
				error.appendTo($("#error-your-email"));
			} else if(element.attr("name") == "subjectContact") {
				error.appendTo($("#error-subject"));
			} else if(element.attr("name") == "contentContact") {
				error.appendTo($("#error-content"));
			} else {
		        error.insertAfter(element); // default error placement.
		    }
		}
	});
});

