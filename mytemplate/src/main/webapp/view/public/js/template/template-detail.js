$(document).ready(function(){
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	var idCommentParent = "";
	var msgCanNotBlank = getMsg("msg.validate.text.cannotbeblank");
	
	$(document).on("click","#btn-comment",function(){
		var contentComment = $("#ta-comment").val();
		var idTemplate = $(this).attr("data-template-id");
		
		var commentTemplate = new FormData();
		commentTemplate.append("contentComment",contentComment);
		commentTemplate.append("idTemplate",idTemplate);
		commentTemplate.append("idCommentParent",0);
		
		$.ajax({
			url : ctxPath + "/ajax/template/comment-template",
			data : commentTemplate,
			processData : false,
			contentType : false,
			method : 'POST',
			beforeSend:function(xhr){
				xhr.setRequestHeader(header, token);
			},
			success : function(data) {
				if(data.state == "true") {
					//add data for form frmAddCommentAfterSaveSuccess
					var commenterName = $("#txtNameCommenter").val();
					$("#frmAddCommentAfterSaveSuccess").find(".header-comment-name").text(commenterName);
					$("#frmAddCommentAfterSaveSuccess").find(".body-comment-parent").text(contentComment);
					$("#frmAddCommentAfterSaveSuccess").find(".footer-comment-rely").attr("data-id-parent-comment",data.idCommentParent);
					//add comment parent
					$(".wrap-list-comment").prepend($("#frmAddCommentAfterSaveSuccess").html());
					//clear data text comment on editor
					$("#ta-comment").val("");
				}
			}
		});
	});//end click btn comment
	
	$(document).on("click", ".footer-comment-rely", function(e){
		var htmlFormSubComment = $("#frmSubComment").html();
		//set idcommentParent
		idCommentParent = $(this).attr("data-id-parent-comment");
		//remove all form before add new
		$(".footer-comment-rely").next().remove();
		$(this).after(htmlFormSubComment);
		e.preventDefault();
	});//end click rely function
	
	//add sub comment
	$(document).on("click", ".btn-sub-comment", function(e){
		var contentComment = $(this).parent().find(".ta-sub-comment").val();
		var idTemplate = $("#txtTemplateId").val();
		
		var commentTemplate = new FormData();
		commentTemplate.append("contentComment",contentComment);
		commentTemplate.append("idTemplate",idTemplate);
		commentTemplate.append("idCommentParent",idCommentParent);
		
		$.ajax({
			url : ctxPath + "/ajax/template/comment-template",
			data : commentTemplate,
			processData : false,
			contentType : false,
			method : 'POST',
			beforeSend:function(xhr){
				xhr.setRequestHeader(header, token);
			},
			success : function(data) {
				if(data.state == "true") {
					//add data for form frmAddCommentAfterSaveSuccess
					var commenterName = $("#txtNameCommenter").val();
					$("#frmAddSubCommentAfterSaveSuccess").find(".header-comment-name").text(commenterName);
					$("#frmAddSubCommentAfterSaveSuccess").find(".body-comment-child").text(contentComment);
					//add comment parent
					$(".wrap-list-comment").prepend($("#frmAddSubCommentAfterSaveSuccess").html());
					//clear data text comment on editor
					$(".ta-sub-comment").val("");
				}
			}
		});
	});//end click rely function
	
});//end ready