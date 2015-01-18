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
	
	
	//click login
	$(document).on("click",".facebook-login-button",function(){
		FB.login(function(response){
			if(response.status === "connected"){
				location.reload();
			}
		});
	});
});

$(window).load(function(){
	checkLogin();
});

function checkLogin(){
	FB.getLoginStatus(function(response) {
		if(response.status === 'connected'){
			FB.api('/me', function(response) {
		      console.log('Successful login for: ' + response.name);
		    });
		}
	});
}