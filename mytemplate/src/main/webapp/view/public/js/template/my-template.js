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
	console.log("template da delete" + idTemplateDelete);
}