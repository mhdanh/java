$(document).ready(function(){
	
	//scroll to load more template
	$(window).scroll(function(){
		var heightDocument = $(document).height() - $(window).height();
		if(heightDocument == $(this).scrollTop() && checkRemainData()){
			loadMore();
		}
	});
	
	//event loadmore button
	$(document).on("click","#load-more-button",function(){
		loadMore();
	});//end function load more
	
});//end ready
function loadMore(){
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	
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
			//hide button load more
			hideShowButtonLoadMore();
		}
	});//end ajax
}
function hideShowButtonLoadMore(){
	if(!checkRemainData()){
		$("#load-more-button").hide();
	}else{
		$("#load-more-button").show();
	}
}

function checkRemainData(){
	var totalPage = parseInt($("#total-template-published").val());
	var currentPage = parseInt($("#current-page").val()) + 1;
	var step = parseInt($("#current-step").val());
	var totalRowCurrent = currentPage * step;
	if(totalPage <= totalRowCurrent){
		return false;
	}else{
		return true;
	}
}