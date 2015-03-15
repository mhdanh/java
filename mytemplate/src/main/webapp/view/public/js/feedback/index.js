$(document).ready(function() {
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