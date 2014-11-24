<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="my" uri="/WEB-INF/my.tld"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<c:set var="myVar" value="maihuudanh" />

<h1>${my:doMyStuff(myVar)}</h1>


<span class="content"> <spring:message code="msg.client.id" />
</span>

-------------update load file------------------
<input type="file" name="uploadfile" id="upload" multiple />
<br />
<button id="btnUp">Upload</button>

<script>
	$(document).ready(function() {

		$(document).on("click", "#btnUp", function() {
			var oMyForm = new FormData();

			console.log($("#upload")[0].files[0]);

			oMyForm.append("name", "mhdanh.png");
			oMyForm.append("file", $("#upload")[0].files[0]);

			$.ajax({
				url : ctxPath + "/ajax/upload-file",
				data : oMyForm,
				dataType : 'text',
				processData : false,
				contentType : false,
				method : 'POST',
				success : function(data) {
					alert(data);
				}
			});

		});
	});
</script>
