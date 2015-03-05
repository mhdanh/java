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
	    				<c:if test = "${ownerTemplate}">
	    					<a href = "<c:url value = '/template/edit-description-page/${template.id}'/>" class = "fa fa-pencil-square-o template-detail-edit-link"></a>
	    				</c:if>
	    				
	    				${template.description}
	    			</div>
	    			<div class="tab-pane" id="template-log">
	    				<c:if test = "${ownerTemplate}">
		    				<a href = "<c:url value = '/template/edit-log-page/${template.id}'/>" class = "fa fa-pencil-square-o template-detail-edit-link"></a>
	    				</c:if>
	    				${template.log}
	    			</div>
	    			<div class="tab-pane" id="template-comment">
	    				<div class="form-group wrap-text-comment-parent">
	    					<textarea class="form-control" id = "ta-comment" rows="3"></textarea>
	    					<button class="btn btn-primary  mt-button" id = "btn-comment" data-template-id = "${template.id}"><spring:message code = 'msg.template.detail.button.comment'/></button>
			            </div>
			            
			            <div class = "wrap-list-comment">
			            	<c:forEach items="${parentComments}" var="parentComment">
				            	<div class = "wrap-comment-parent">
				            		<div class = "comment-parent">
				            			<div class = "header-comment-parent">
				            				<span>${parentComment.commenter.firstName} ${parentComment.commenter.lastName}</span>
				            				<span>${parentComment.dateCreated}</span> <strong>${parentComment.dateToString}</strong>
				            			</div><!-- end header-comment-parent -->
				            			<div class = "body-comment-parent">
				            				${parentComment.content}
				            			</div><!-- end body-comment-parent -->
				            		</div><!-- end comment-parent -->
				            		<div class = "list-comment-child">
				            			<c:forEach items="${parentComment.childComments}" var="childComment">
					            			<div class = "comment-child">
						            			<div class = "header-comment-child">
						            				<span>${childComment.commenter.firstName} ${childComment.commenter.lastName}</span>
				            						<span>${childComment.dateCreated}</span>
						            			</div><!-- end header-comment-child -->
						            			<div class = "body-comment-child">
						            				${childComment.content}
						            			</div><!-- end body-comment-child -->
						            		</div><!-- end comment-child -->
					            		</c:forEach>
				            		</div><!-- end list-comment-child -->
				            	</div><!-- end wrap-comment-parent -->
			            	</c:forEach>
			            </div><!-- end wrap-list-comment -->
	    			</div><!-- end template-comment -->
	  			</div><!-- tab-content -->
			</div><!-- end description -->
		</div><!-- end left block -->
		<div class = "col-md-4">
			<a href = "<c:url value ='/template/check/buy/direct/${template.id}'/>">Buy me direct</a>
		</div><!-- end left block -->
	</div>	
</div>