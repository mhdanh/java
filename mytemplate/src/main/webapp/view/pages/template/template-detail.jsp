<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="row template-detail">
	<h2 class = "page-title">${template.title}</h2>
	<div class = "row">
		<div class = "col-md-8">
			<div class = "thumbnail" id = "thumbnail">
				<img src="<c:url value='/viewimg/${template.thumbnail}'/>" class="img-thumbnail" alt="Detail template">
			</div>
			<div>

		  		<!-- Nav tabs -->
				<ul class="nav nav-tabs template-tab">
	  				<li class="active"><a href="#template-description" data-toggle="tab"><spring:message code = "msg.template.detail.text.description"/></a></li>
	  				<li><a href="#template-log"  data-toggle="tab"><spring:message code = "msg.template.detail.text.log"/></a></li>
	  				<li><a href="#template-comment" data-toggle="tab"><spring:message code= "msg.template.detail.text.comment"/></a></li>
				</ul>
	
				<!-- Tab panes -->
	  			<div class="tab-content">
	    			<div class="tab-pane active" id="template-description">
	    				<a class = "fa fa-pencil-square-o template-detail-edit-link"></a>
	    				${template.description}
	    				not description
	    			</div>
	    			<div class="tab-pane" id="template-log">
	    				<a class = "fa fa-pencil-square-o template-detail-edit-link"></a>
	    				${template.log}
	    				not log
	    			</div>
	    			<div class="tab-pane" id="template-comment">messages</div>
	  			</div>
			</div><!-- end description -->
		</div><!-- end left block -->
		<div class = "col-md-4">
			right block
		</div><!-- end left block -->
	</div>	
</div>