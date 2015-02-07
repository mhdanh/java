<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="my" uri="/WEB-INF/my.tld"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>

<c:forEach items="${templates}" var="template" varStatus="status">
	<c:choose>
		<c:when test="${status.index % 2== 0}">
			<div class="row row-content-template">
				<div class="col-md-6">
					<div class="content-template">
						<div class="wrap-title-img">
							<h4 class="title-template">
								<c:out value="${template.title}" escapeXml="true" />
							</h4>
							<div class="wrap-imag-viewdetail">
								<img src="<c:url value='/viewimg/${template.thumbnail}'/>"
									class="img-thumbnail" alt="Cinque Terre" width="420"
									height="236">
								<div class="overlay-view-detail-button">
									<a href="<c:url value = '/template-detail/${template.id}'/>"
										class='view-detail-button' target="_blank"><spring:message
											code='msg.upload-template-file-page.label.viewdetail' /></a>
								</div>
							</div>
							<!-- end wrap img and delete -->
							<div class="detail-item">
								<strong class="cost-item"> <c:choose>
										<c:when test="${template.sellOff eq 0}">
											<spring:message
												code='msg.upload-template-file-page.text.free' />
										</c:when>
										<c:otherwise>
												${template.sellOff}	
											</c:otherwise>
									</c:choose>
								</strong> <strong class="buy-item"> <span
									class="glyphicon glyphicon-shopping-cart icon"></span> <span
									class="buy-item-text">${template.buy}</span>
								</strong>
							</div>
							<!-- end detail item -->
							<div class="wrap-livedemo-buy">
								<a href="${template.link}" target="_blank"
									class="btn btn-primary mt-button"><spring:message
										code='msg.upload-template-file-page.label.livedemo' /></a>
								<c:choose>
									<c:when test="${template.sellOff eq 0}">
										<a
											href="<c:url value='/template/download-template/${template.id}'/>"
											class="btn btn-primary mt-button"><spring:message
												code='msg.upload-template-file-page.label.downloadme' /></a>
									</c:when>
									<c:otherwise>
										<a href="${template.link}" target="_blank"
											class="btn btn-primary mt-button"><spring:message
												code='msg.upload-template-file-page.label.buyme' /></a>
									</c:otherwise>
								</c:choose>
							</div>
							<!-- end purchase button and live demo -->
						</div>
					</div>
					<div class="mt-clear-both"></div>
				</div>
		</c:when>
		<c:otherwise>
			<div class="col-md-6">
				<div class="content-template">
					<div class="wrap-title-img">
						<h4 class="title-template">
							<c:out value="${template.title}" escapeXml="true" />
						</h4>
						<div class="wrap-imag-viewdetail">
							<img src="<c:url value='/viewimg/${template.thumbnail}'/>"
								class="img-thumbnail" alt="Cinque Terre" width="420"
								height="236">
							<div class="overlay-view-detail-button">
								<a href="<c:url value = '/template-detail/${template.id}'/>"
										class='view-detail-button' target="_blank"><spring:message
											code='msg.upload-template-file-page.label.viewdetail' /></a>
							</div>
						</div>
						<!-- end wrap img and delete -->
						<div class="detail-item">
							<strong class="cost-item"> <c:choose>
									<c:when test="${template.sellOff eq 0}">
										<spring:message code='msg.upload-template-file-page.text.free' />
									</c:when>
									<c:otherwise>
												${template.sellOff}	
											</c:otherwise>
								</c:choose>
							</strong> <strong class="buy-item"> <span
								class="glyphicon glyphicon-shopping-cart icon"></span> <span
								class="buy-item-text">${template.buy}</span>
							</strong>
						</div>
						<!-- end detail item -->
						<div class="wrap-livedemo-buy">
							<a href="${template.link}" target="_blank"
								class="btn btn-primary mt-button"><spring:message
									code='msg.upload-template-file-page.label.livedemo' /></a>
							<c:choose>
								<c:when test="${template.sellOff eq 0}">
									<a
										href="<c:url value='/template/download-template/${template.id}'/>"
										class="btn btn-primary mt-button"><spring:message
											code='msg.upload-template-file-page.label.downloadme' /></a>
								</c:when>
								<c:otherwise>
									<a href="${template.link}" target="_blank"
										class="btn btn-primary mt-button"><spring:message
											code='msg.upload-template-file-page.label.buyme' /></a>
								</c:otherwise>
							</c:choose>
						</div>
						<!-- end purchase button and live demo -->
					</div>
				</div>
				<div class="mt-clear-both"></div>
			</div>
			</div>
		</c:otherwise>
	</c:choose>
</c:forEach>
<c:if test="${templates.size() % 2 == 1}">
	<!-- enclose div if odd size -->
	</div>
</c:if>

<div class = "div-wrap-save-info-filter mt-display-none">
	<!-- param load more page -->
	<input type = "hidden" id = "current-page" value = "${lazyLoading.page}"/>
	<input type = "hidden" id = "current-step" value = "${lazyLoading.step}"/>
	<input type = "hidden" id = "current-category-id" value = "${lazyLoading.idCategory}"/>
	<input type = "hidden" id = "current-value-filter" value = "${lazyLoading.valueFilter}"/>
	<input type = "hidden" id = "total-template-published" value = "${totalTemplatePublished}" />
	<input type = "hidden" id = "index-step-load" value = "${step}" />
</div>