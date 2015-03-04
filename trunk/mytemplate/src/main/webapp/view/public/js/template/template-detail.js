$(document).ready(function(){
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	
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
			}
		});
	});
});//end ready