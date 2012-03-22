<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html 
     PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
     "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>User Details</title>
	
	<style>
 		.error {
 			color: red;
 		}
	</style>
	
</head>

<body>
<div id="main_wrapper">

<h1>User Details</h1>

<form:form commandName="user" method="post">
	<table>
		<tr>
			<td><form:errors cssClass="error"/></td>
		</tr>
		<tr><td> </td></tr>
		<tr>
			<td>Username:</td>
			<security:authorize access="isAnonymous()">
				<td><form:input path="username" size="50"/></td>
				<td><form:errors path="username" cssClass="error"/></td>
			</security:authorize>
			<security:authorize access="isAuthenticated()">
				<td><form:label path="username"/></td>
			</security:authorize>
		</tr>
		<tr>
			<td>Password:</td>
			<td><form:password path="password" size="50"/></td>
			<td><form:errors path="password" cssClass="error"/></td>
		</tr>
		<tr>
			<td>Confirm password:</td>
			<td><form:password path="passwordConfirm" size="50"/></td>
		</tr>
		<security:authorize access="isAnonymous()">
			<tr>
				<td class="input-selitesarake">
					 <!-- Firefox caching issue workaround -->
					 <img src="<%= request.getContextPath() %>/captchaImg?uid=<%=Long.toString(new java.util.Date().getTime()) %>" />
				</td>
				<td><form:input path="captcha" size="50"/></td>
			</tr>
		</security:authorize>
		<tr>
			<td colspan="3"><input type="submit" value="Save"/>
				<security:authorize access="isAuthenticated()">
					<a href="<%= request.getContextPath() %>/board/users/userDetails?username=${user.username}"><input type="button" value="Cancel"/></a>
				</security:authorize>
			</td>
		</tr>
	</table>
</form:form>
<security:authorize access="hasRole('ROLE_ADMIN')">
	<a href="<%= request.getContextPath() %>/board/users/userSummary"><input type="button" value="User Summary"/></a>
</security:authorize>
<security:authorize access="isAuthenticated()">
	<a href="<%= request.getContextPath() %>/board/messages/messageSummary"><input type="button" value="Message Summary"/></a>
	<a href="<c:url value="/board/users/j_spring_security_logout"/>"><input type="button" value="Logout (<security:authentication property="principal.username"/>)"/></a>
</security:authorize>
<security:authorize access="isAnonymous()">
	<a href="<%= request.getContextPath() %>/board/messages/messageSummary"><input type="button" value="Cancel"/></a>
</security:authorize>
</div>
</body>

</html>
