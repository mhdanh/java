$(document).ready(function(){
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	
	var msgCanNotBlank = getMsg("msg.validate.text.cannotbeblank");
	var msgWrongEmailFormat = getMsg("msg.validate.text.email.wrongformat");
	
	$(document).on("keyup","#email",function(){
		var valueEmail = $.trim($(this).val());
		if(valueEmail != ""){
			var registerEmail = new FormData();
			registerEmail.append("email",valueEmail);
			$.ajax({
				url : ctxPath + "/ajax-register-check-email-exist",
				data : registerEmail,
				processData : false,
				contentType : false,
				method : 'POST',
				beforeSend:function(xhr){
					xhr.setRequestHeader(header, token);
				},
				success : function(data) {
					console.log(data);
					if(data.key == "true"){
						$("#error-exist-email-register").html("<label>"+data.msg+"<label>");
						$("#error-exist-email-register").show();
					}else{
						$("#error-exist-email-register").hide();
					}
				}
			});
		}
	});
	
	
	$("#frmRegister").validate({
		submitHandler: function(form) {
			var valueEmail = $.trim($("#email").val());
			var registerEmail = new FormData();
			registerEmail.append("email",valueEmail);
			$.ajax({
				url : ctxPath + "/ajax-register-check-email-exist",
				data : registerEmail,
				async : false,
				processData : false,
				contentType : false,
				method : 'POST',
				beforeSend:function(xhr){
					xhr.setRequestHeader(header, token);
				},
				success : function(data) {
					if(data.key == "true"){
						$("#error-exist-email-register").html("<label>"+data.msg+"<label>");
						$("#error-exist-email-register").show();
						return false;
					}else{
						$("#error-exist-email-register").hide();
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
				error.appendTo($("#error-email-register"));
			}else {
		        error.insertAfter(element); // default error placement.
		    }
		}
	});
});