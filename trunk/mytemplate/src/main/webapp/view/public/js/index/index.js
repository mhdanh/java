$(document).ready(function(){
	
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	
	//event loadmore button
	$(document).on("click","#load-more-button",function(){
		var currentPage = $("#current-page").val();
		var currentStep = $("#current-step").val();
		var currentCategoryId = $("#current-category-id").val();
		var currentValueFilter = $("#current-value-filter").val();
		console.log(currentPage + "|" + currentStep + "|" + currentCategoryId + "|" + currentValueFilter);
		//call ajax load more
		
		var valueFilter = new FormData();
		
		valueFilter.append("idCategory",currentCategoryId);
		valueFilter.append("valueFilter",currentValueFilter);
		valueFilter.append("page",currentPage);
		valueFilter.append("step",currentStep);
		
		$.ajax({
			url : ctxPath + "/ajax/index-load-more",
			data : valueFilter,
			processData : false,
			contentType : false,
			method : 'POST',
			beforeSend:function(xhr){
				xhr.setRequestHeader(header, token);
				$("#upload-file-button").attr("disabled","disabled");
			},
			success : function(data) {
				//renew save data info filter
				$(".div-wrap-save-info-filter").remove();
				//append data
				$(".index-wrap-content-load-template").append(data);
			}
		});//end ajax
		
	});//end function load more
	
});//end ready