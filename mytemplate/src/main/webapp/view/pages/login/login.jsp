<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<form name='loginForm'
	action="<c:url value='j_spring_security_check' />" method='POST'>
	<table>
		<tr>
			<td>User:</td>
			<td><input type='text' name='username' value='admin' autofocus="true"></td>
		</tr>
		<tr>
			<td>Password:</td>
			<td><input type='password' name='password' value='123'/></td>
		</tr>
		<tr>
			<td colspan='2'><input name="submit" type="submit"
				value="submit" /></td>
		</tr>
		<tr>
			<td colspan='2'><input type="checkbox" name="remember-me" /> remember me</td>
		</tr>
	</table>
	<input type="hidden" name="${_csrf.parameterName}"
		value="${_csrf.token}" />
</form>