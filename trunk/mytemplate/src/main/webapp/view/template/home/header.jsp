<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="my" uri="/WEB-INF/my.tld"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<nav class="navbar navbar-default mt-no-margin-bottom" role="navigation">
	<div class="container-fluid">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#bs-example-navbar-collapse-1">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="<c:url value='/'/>">Home</a>
		</div>

		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li class="dropdown">
					<a href="#" class="dropdown-toggle multi-level" data-toggle="dropdown"><spring:message code = "msg.recharge.card.title"/></a>
					<ul class="dropdown-menu dropdown-menu-with-arrow" role="menu">
						<li><a href="<c:url value='/recharge/card-page'/>"><spring:message code='msg.layout.recharge.by.mobile.card'/></a></li>
					</ul>
				</li>
				<li><a href="<c:url value = '/upload-template-file-page'/>">Upload template</a></li>
			</ul>
			
			<ul class="nav navbar-nav navbar-right">
				<sec:authorize access="isAuthenticated()">
				<li><a href = "#" onclick = "return false;"><spring:message code = "msg.layout.total.money"/>: ${empty my:getUserLogined().totalMoney?'0':my:getUserLogined().totalMoney}VND</a></li>
				<li class="dropdown">
				<a href="#" class="dropdown-toggle" data-toggle="dropdown">${my:getUserLogined().username} <span class="caret"></span></a>
					<ul class="dropdown-menu" role="menu">
						<li><a href="<c:url value='/template/my-template'/> ">My Template</a></li>
						<li><a href="#">Account info</a></li>
						<li><a href="<c:url value='/recharge/card-page'/>"><spring:message code = "msg.recharge.card.title"/></a></li>
						<li class="divider"></li>
						<li><a href="#">
							<form name='logoutForm' action="<c:url value='/j_spring_security_logout' />" method='POST'>
								<input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />
								<button type="submit" class = "link-button-sign-out">Sign out</button>
							</form>
							</a>
						</li>
					</ul>
				</li>
				</sec:authorize>
				
				<sec:authorize access="isAnonymous()">
					<li><a href="<c:url value='/login'/>">Sign in</a></li>
					<li><a href="<c:url value='/register'/>">Sign up</a></li>
				</sec:authorize>
			</ul>
		</div>
		<!-- /.navbar-collapse -->
	</div>
	<!-- /.container-fluid -->
</nav>