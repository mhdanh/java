$(document).ready(function() {
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	
	//like button
	$(document).on("click",".like-button",function(e) {
		e.preventDefault();
		var feedBackId = $(this).attr("data-feedback-id");
		$.ajax({
			url: ctxPath + "/feedback/ajax/like",
			method: 'POST',
			data:{"idFeedback":feedBackId},
			beforeSend:function(xhr){
				xhr.setRequestHeader(header, token);
			},
			success:function(data) {
				if(data.state == "true") {
					location.reload();
				}
			}
		});
	});
	
	//dislike button
	$(document).on("click",".dislike-button",function(e) {
		e.preventDefault();
		var feedBackId = $(this).attr("data-feedback-id");
		$.ajax({
			url: ctxPath + "/feedback/ajax/dislike",
			method: 'POST',
			data:{"idFeedback":feedBackId},
			beforeSend:function(xhr){
				xhr.setRequestHeader(header, token);
			},
			success:function(data) {
				if(data.state == "true") {
					location.reload();
				}
			}
		});
	});
	
	//rely feedback
	$(document).on("click",".rely-feedback-button",function(e){
		e.preventDefault();
		var formRely = $(this).parent().find(".frm-rely-feedback");
		if($(formRely).hasClass("mt-display-none")) {
			//hide all form rely feedback first
			$(".frm-rely-feedback").addClass("mt-display-none");
			$(formRely).removeClass("mt-display-none");
		} else {
			$(formRely).addClass("mt-display-none");
		}
	});
});