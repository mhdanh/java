$(document).ready(function(){
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	
	//unpublish template
	$(document).on("click",".action-unpublish",function(){
		var idTemplate = $(this).attr("data-id");
		var btnUnPublish = $(this);
		var templatePublish = new FormData();
		templatePublish.append("idTemplate",idTemplate);
		$.ajax({
			url : ctxPath + "/admin/ajax/unpublish-template",
			data : templatePublish,
			processData : false,
			contentType : false,
			method : 'POST',
			beforeSend:function(xhr){
				xhr.setRequestHeader(header, token);
			},
			success : function(data) {
				if(data.status == "true"){
					//renew title
					$(btnUnPublish).attr("title",data.msg);
					//remove class unchecked
					$(btnUnPublish).removeClass("glyphicon-check").addClass("glyphicon-unchecked");
					//remove class action
					$(btnUnPublish).removeClass("action-unpublish").addClass("action-publish");
					//update status
					$(btnUnPublish).parents("tr").find(".tb-manage-template-col-status").text(data.statusTemplate);
				}else{
					createModalInformError("Error",data.msg);
				}
			}
		});//end ajax
	});//end unpublish template
	
	//publish template
	$(document).on("click",".action-publish",function(){
		var idTemplate = $(this).attr("data-id");
		var templatePublish = new FormData();
		var btnPublish = $(this);
		templatePublish.append("idTemplate",idTemplate);
		$.ajax({
			url : ctxPath + "/admin/ajax/publish-template",
			data : templatePublish,
			processData : false,
			contentType : false,
			method : 'POST',
			beforeSend:function(xhr){
				xhr.setRequestHeader(header, token);
			},
			success : function(data) {
				if(data.status == "true"){
					//renew title
					$(btnPublish).attr("title",data.msg);
					//remove class unchecked
					$(btnPublish).removeClass("glyphicon-unchecked").addClass("glyphicon-check");
					//remove class action
					$(btnPublish).removeClass("action-publish").addClass("action-unpublish");
					//update status
					$(btnPublish).parents("tr").find(".tb-manage-template-col-status").text(data.statusTemplate);
				}else{
					createModalInformError("Error",data.msg);
				}
			}
		});//end ajax
	});//end publish template
	
});//end ready