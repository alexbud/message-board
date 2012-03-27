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
	<title>Message Details</title>
	
	<style>
 		.error {
 			color: red;
 		}
	</style>
	
</head>

<body>
<div id="main_wrapper">

<h1>Message Details</h1>

<form:form commandName="message" method="post">
	<table>
		<tr>
			<td>Title:</td>
			<td><form:input path="title" size="50"/></td>
			<td><form:errors path="title" cssClass="error"/></td>
		</tr>
		<tr>
			<td>Content:</td>
			<td><form:textarea path="content" cols="38" rows="4"/></td>
			<td><form:errors path="content" cssClass="error"/></td>
		</tr>
		<tr>
			<td>URL:</td>
			<td><form:input path="url" size="50"/></td>
			<td><form:errors path="url" cssClass="error"/></td>
		</tr>
		<form:hidden path="principal" />
		<tr>
			<td colspan="3"><input type="submit" value="Save"/>
				<a href="<%= request.getContextPath() %>/board/messages/messageSummary"><input type="button" value="Cancel"/></a>
			</td>
		</tr>
	</table>
</form:form>
<a href="<c:url value="/board/messages/j_spring_security_logout"/>"><input type="button" value="Logout (<security:authentication property="principal.username"/>)"/></a>
</div>
</body>

</html>
