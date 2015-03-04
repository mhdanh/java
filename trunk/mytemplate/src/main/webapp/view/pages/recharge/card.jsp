<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="row recharge-card">
	<div class="col-sm-4">
		<h2 class="page-title"><spring:message code = 'msg.recharge.card.title'/></h2>
		<form id = "frm-recharge" method="post" name = "frmRecharge" action = "<c:url value ='/recharge/card-add'/>">
			<!-- type card -->
            <div class="form-group">
                <label for = "selTypeCard"><spring:message code = 'msg.recharge.card.typecard'/> *</label>
                <select class = 'form-control  mt-no-border-radius' id = "selTypeCard" name = "typeCard">
					<c:forEach items="${cardTypes}" var = "cardType">
						<option value = "${cardType}">${cardType}</option>
					</c:forEach>
				</select>
            </div>
			<!-- seri number -->
			<div class="form-group">
                <label for = "txtSeriNumber"><spring:message code = 'msg.recharge.card.serinumber' /> *</label>
                <input type = "text" class = "form-control  mt-no-border-radius" id = "txtSeriNumber" name = "seriNumber"/>
                <div id = "error-seri-number" class = "error-custom"></div>
            </div>
            
            <!-- pin code -->
			<div class="form-group">
                <label for = "txtPinCode"><spring:message code = 'msg.recharge.card.pincode' /> *</label>
                <input type = "text" class = "form-control  mt-no-border-radius" id = "txtPinCode" name = "pinCode"/>
                <div id = "error-pin-code" class = "error-custom"></div>
            </div>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <div class="form-group">
				<button class="btn btn-primary  mt-button" id = "upload-file-button" type="submit"><spring:message code = 'msg.recharge.card.button.recharge'/></button>
			</div>
		</form>
	</div>
</div>