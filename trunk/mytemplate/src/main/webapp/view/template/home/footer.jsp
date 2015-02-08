<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!-- <footer class="footer"> -->
<!-- 	<div class = "row"> -->
<!-- 		<div class = "col-md-4"> -->
<!-- 			<ul> -->
<!-- 				<li><a href = "" class = "fa fa-globe"> Find Jobs</a></li> -->
<!-- 				<li><a href = "" class = "fa fa-slideshare"> Blog</a></li> -->
<!-- 				<li><a href="https://plus.google.com/110814290957816282578" rel="publisher">Google+</a></li> -->
<!-- 			</ul> -->
<!-- 		</div> -->
<!-- 	</div> -->
<!-- </footer> -->
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
