$(document).ready(function(){
	
	var msgCanNotBlank = getMsg("msg.validate.text.cannotbeblank");
	var msgInputNumber = getMsg("msg.upload-template-file-page.text.inputnumber");
	
	$("#frm-recharge").validate({
	  	rules: {
	  		seriNumber: {
	  			required: true,
	  			number: true
	  		},
	  		pinCode: {
	  			required: true,
	  			number: true
	  		}
		},
		messages:{
			txtSeriNumber:{
				required: msgCanNotBlank,
				number: msgInputNumber
			},
			txtPinCode: {
				required: msgCanNotBlank,
				number: msgInputNumber
			},
		},
		focusInvalid: true,
		errorPlacement: function(error, element) {
			if(element.attr("name") == "seriNumber") {
				error.appendTo($("#error-seri-number"));
			} else if(element.attr("name") == "pinCode") {
				error.appendTo($("#error-pin-code"));
			} else {
		        error.insertAfter(element); // default error placement.
		    }
		}
	});
});