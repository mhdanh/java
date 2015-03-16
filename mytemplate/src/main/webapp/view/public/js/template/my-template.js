var idTemplateDelete = 0;
$(document).ready(function(){
	//delete template action
	$(document).on("click",".action-delete-template",function(){
		var idTemplate = $(this).attr("data-id-template");
		idTemplateDelete = idTemplate;
		createModalConfirmYesNo("Confirm","Would you like to delete template #" + idTemplate, deleteTemplate);
		return false;
	});//end action delete template action
});//end ready

function deleteTemplate(){
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	
	var deleteTemplateForm = new FormData();
	deleteTemplateForm.append("idTemplate",idTemplateDelete);
	
	$.ajax({
		url : ctxPath + "/template/delete-template",
		data : deleteTemplateForm,
		processData : false,
		contentType : false,
		method : 'POST',
		beforeSend:function(xhr){
			xhr.setRequestHeader(header, token);
		},
		success : function(data) {
			if(data.state == "true"){
				//remove tr
				$("tr[data-id-template='"+idTemplateDelete+"']").remove();
			} else {
				createModalInformError("Error",data.msg);
			}
		}
		
	});
}