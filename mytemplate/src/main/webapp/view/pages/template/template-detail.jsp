<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set value="false" var = "logined"/>
<sec:authorize access="isAuthenticated()">
	<c:set value = "true" var = "logined"/>
</sec:authorize>
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
	    					<textarea class="form-control" id = "ta-comment" rows="2" <c:if test = "${!logined}">disabled</c:if> ></textarea>
	    					<c:choose>
	    						<c:when test = "${!logined}">
	    							<a href = "<c:url value = '/login'/>" class="btn btn-primary  mt-button btn-sm"><spring:message code = 'msg.login.label.signin'/></a>
	    						</c:when>
	    						<c:otherwise>
	    							<button class="btn btn-primary  mt-button btn-sm" id = "btn-comment" data-template-id = "${template.id}"><spring:message code = 'msg.template.detail.button.comment'/></button>
	    						</c:otherwise>
	    					</c:choose>
			            </div>
			            
			            <div class = "wrap-list-comment">
			            	<c:forEach items="${parentComments}" var="parentComment">
				            	<div class = "wrap-comment-parent">
				            		<div class = "comment-parent">
				            			<div class = "header-comment-parent">
				            				<span class = "header-comment-name"><c:out value = "${parentComment.commenter.firstName} ${parentComment.commenter.lastName}" escapeXml="true"/></span>
				            				<span class = "header-comment-time">${parentComment.dateToString}</span>
				            			</div><!-- end header-comment-parent -->
				            			<div class = "body-comment-parent">
				            				<pre class = "mt-pre"><c:out value="${parentComment.content}" escapeXml="true"/></pre>
				            			</div><!-- end body-comment-parent -->
				            			<div class = "footer-comment-parent">
				            				<c:if test = "${logined}">
				            					<a href = "#" class = "footer-comment-rely" data-id-parent-comment = "${parentComment.id}"><spring:message code = 'msg.template.detail.button.rely'/></a>
				            				</c:if>
				            			</div>
				            		</div><!-- end comment-parent -->
				            		<div class = "list-comment-child">
				            			<c:forEach items="${parentComment.childComments}" var="childComment">
					            			<div class = "comment-child">
						            			<div class = "header-comment-child">
						            				<span class = "header-comment-name">${childComment.commenter.firstName} ${childComment.commenter.lastName}</span>
				            						<span class = "header-comment-time">${childComment.dateToString}</span>
						            			</div><!-- end header-comment-child -->
						            			<div class = "body-comment-child">
						            				<pre class = "mt-pre"><c:out value = "${childComment.content}" escapeXml="false"/></pre>
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
			<!-- purchase safe -->
			<a href="${buysafe}">Buy now buy Bao kim</a>
		</div><!-- end left block -->
	</div>	
</div>
<!-- html for javascript -->
<input type = "hidden" value = "${template.id}" id = "txtTemplateId"/>
<input type = "hidden" value = "${template.owner.firstName} ${template.owner.lastName}" id = "txtNameCommenter"/>

<!-- form add new sub comment parent by js -->
<div id = "frmAddSubCommentAfterSaveSuccess" class = "mt-display-none">
	<div class = "comment-child">
		<div class = "header-comment-child">
			<span class = "header-comment-name"></span>
			<span class = "header-comment-time"><spring:message code = 'msg.template.detail.text.today'/></span>
		</div><!-- end header-comment-child -->
		<div class = "body-comment-child">
		</div><!-- end body-comment-child -->
	</div><!-- end comment-child -->
</div>

<!-- form add new comment parent by javascript  -->
<div id = "frmAddCommentAfterSaveSuccess" class = "mt-display-none">
	<div class = "wrap-comment-parent">
		<div class = "comment-parent">
			<div class = "header-comment-parent">
				<span class = "header-comment-name"></span>
				<span class = "header-comment-time"><spring:message code = 'msg.template.detail.text.today'/></span>
			</div><!-- end header-comment-parent -->
			<div class = "body-comment-parent">
			</div><!-- end body-comment-parent -->
			<div class = "footer-comment-parent">
				<a href = "#" class = "footer-comment-rely" data-id-parent-comment = ""><spring:message code = 'msg.template.detail.button.rely'/></a>
			</div>
		</div><!-- end comment-parent -->
		<div class="list-comment-child">
		</div>
	</div><!-- end wrap-comment-parent -->
</div><!-- end  frmAddCommentAfterSaveSuccess-->
<!-- form sub comment -->
<div id = "frmSubComment" class = "mt-display-none">
	<div class="form-group wrap-sub-comment-parent">
		<textarea class="form-control ta-sub-comment" rows="2"></textarea>
		<button class="btn btn-primary  mt-button btn-sm btn-sub-comment"><spring:message code = 'msg.template.detail.button.comment'/></button>
    </div>
</div>

