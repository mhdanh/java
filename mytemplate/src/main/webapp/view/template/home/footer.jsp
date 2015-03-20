<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<footer class="footer">
	<div class = "wrap-footer-row">
		<div class = "footer-top">
			<ul class = "footer-top-ul-left">
				<li><a href="<c:url value ='/about'/>"><spring:message code = "msg.layout.about"/></a></li>
				<li><a href="<c:url value ='/feedback'/>"><spring:message code = "msg.layout.feedback"/></a></li>
				<li><a href="<c:url value = '/contact'/>"><spring:message code ='msg.layout.contact'/></a></li>
			</ul>
			<ul class = "footer-top-ul-right">
				<li><a href="<c:url value = '?lang=en'/>">English</a></li>
				<li><a href="">|</a></li>
				<li><a href="<c:url value = '?lang=vi'/>"><spring:message code = "msg.layout.vietnamese"/></a></li>
			</ul>
			<div class = "mt-clear-both"></div>
		</div><!-- end footer top -->
		<div class = "footer-bottom">
			<ul class = "footer-bottom-ul-left">
				<li><a href="">myui.info</a> &copy; 2015 | Rights Reserved.</li>
				<li><spring:message code = "msg.layout.otherproduct"/>: <a href="http://dainguu.blogspot.com/" target="_blank" title="<spring:message code = "msg.layout.kienthucit"/>"><spring:message code = "msg.layout.kienthucit"/></a> - <a href ="http://vieccantho.com/jobs/" target = "_blank" title = "<spring:message code = "msg.layout.vieccantho"/>"><spring:message code = "msg.layout.vieccantho"/></a></li>
			</ul>
			<ul class = "footer-bottom-ul-right-icon-social">
				<li><a href=""><i class="fa fa-facebook-square"></i></a></li>
				<li><a href=""><i class="fa fa-google-plus-square"></i></a></li>
			</ul>
			<div class = "mt-clear-both"></div>
		</div><!-- end footer bottom -->
	</div>
</footer>
<!-- modal confirm yes no-->
<div class="modal" id = "modal-confirm-yes-no">
	<div class="modal-dialog modal-vertical-centered">
		<div class="modal-content mt-no-border-radius modal-confirm-yes-no-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id = "modal-confirm-yes-no-title"></h4>
			</div>
			<div class="modal-body" id = "modal-confirm-yes-no-body">
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary mt-button modal-confirm-yes-button"><spring:message code = "msg.button.yes"/></button>
				<button type="button" class="btn btn-default mt-button" data-dismiss="modal"><spring:message code = "msg.button.close"/></button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<!-- modal inform error -->
<div class="modal" id = "modal-inform-error">
	<div class="modal-dialog modal-vertical-centered">
		<div class="modal-content mt-no-border-radius modal-content-error">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id = "modal-inform-error-title"></h4>
			</div>
			<div class="modal-body" id = "modal-inform-error-body">
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-danger mt-button" data-dismiss="modal"><spring:message code = "msg.button.close"/></button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
