<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="row recharge-card">
	<div class="col-sm-4">
		<h2 class="page-title"><spring:message code = 'msg.recharge.card.title'/></h2>
		<c:choose>
			<c:when test="${responseRecharge.responseCode eq '200'}">
				Chuc mung ban da nap thanh cong ${responseRecharge.amount} vao tai khoan
			</c:when>
			<c:otherwise>
				Ban da nap khong thanh cong
				error: ${responseRecharge.errorMessage}
			</c:otherwise>
		</c:choose>
	</div>
</div>