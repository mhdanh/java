<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="my" uri="/WEB-INF/my.tld"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>

<div class = "wrapper-content-template">
	<c:forEach items="${templates}" var="template" varStatus="status">
		<c:choose>
			<c:when test="${status.index % 2== 0}">
			<div class = "row row-content-template">
				<div class = "col-md-6">
					<div class = "content-template">
						<div class = "wrap-title-img">
							<h4 class = "title-template">${template.title}</h4>
							<div class = "wrap-imag-viewdetail">
								<img src="<c:url value='/viewimg/${template.thumbnail}'/>" class="img-thumbnail" alt="Cinque Terre" width="420" height="236">
								<div class = "overlay-view-detail-button">
									<button class = 'view-detail-button'><spring:message code = 'msg.upload-template-file-page.label.viewdetail'/></button>
								</div>
							</div><!-- end wrap img and delete -->
							<div class = "detail-item">
								<strong class = "cost-item">
									${template.cost}
								</strong>
								<strong class = "buy-item">
									<span class = "glyphicon glyphicon-shopping-cart icon"></span>
									<span class = "buy-item-text">${template.buy}</span>
								</strong>
							</div><!-- end detail item -->
							<div class = "wrap-livedemo-buy">
								<a href="${template.link}" target="_blank" class = "btn btn-primary mt-button"><spring:message code = 'msg.upload-template-file-page.label.livedemo' /></a>
								<a href="${template.link}" target="_blank" class = "btn btn-primary mt-button"><spring:message code = 'msg.upload-template-file-page.label.buyme' /></a>
							</div><!-- end purchase button and live demo -->
						</div>
					</div>
					<div class = "mt-clear-both"></div>
				</div>
			</c:when>
			<c:otherwise>
				<div class = "col-md-6">
					<div class = "content-template">
						<div class = "wrap-title-img">
							<h4 class = "title-template">${template.title}</h4>
							<div class = "wrap-imag-viewdetail">
								<img src="<c:url value='/viewimg/${template.thumbnail}'/>" class="img-thumbnail" alt="Cinque Terre" width="420" height="236">
								<div class = "overlay-view-detail-button">
									<button class = 'view-detail-button'><spring:message code = 'msg.upload-template-file-page.label.viewdetail'/></button>
								</div>
							</div><!-- end wrap img and delete -->
							<div class = "detail-item">
								<strong class = "cost-item">
									${template.cost}
								</strong>
								<strong class = "buy-item">
									<span class = "glyphicon glyphicon-shopping-cart icon"></span>
									<span class = "buy-item-text">${template.buy}</span>
								</strong>
							</div><!-- end detail item -->
							<div class = "wrap-livedemo-buy">
								<a href="${template.link}" target="_blank" class = "btn btn-primary mt-button"><spring:message code = 'msg.upload-template-file-page.label.livedemo' /></a>
								<a href="${template.link}" target="_blank" class = "btn btn-primary mt-button"><spring:message code = 'msg.upload-template-file-page.label.buyme' /></a>
							</div><!-- end purchase button and live demo -->
						</div>
					</div>
					<div class = "mt-clear-both"></div>
				</div>
			</div>
			</c:otherwise>
		</c:choose>
	</c:forEach>
	<c:if test="${templates.size() % 2 == 1}">
		<!-- enclose div if odd size -->
		</div>
	</c:if>
</div>