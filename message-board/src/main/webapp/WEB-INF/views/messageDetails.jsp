<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html 
     PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
     "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>Message Details</title>
</head>

<body>
<div id="main_wrapper">

<h1>Message Details</h1>

<table>
	<tr><td>
		<table>
			<tr>
				<td>Message:</td>
				<td>${message.title}</td>
			</tr>
			<tr>
				<td>Date:</td>
				<td><fmt:formatDate value="${message.timestamp}" type="both"/></td>
			</tr>
			<tr>
				<td>Content:</td>
				<td>${message.content}</td>
			</tr>
			<tr>
				<td>Sender:</td>
				<td>${message.principal}</td>
			</tr>
			<tr>
				<td>URL:</td>
				<td>${message.url}</td>
			</tr>
		</table>
	</td></tr>
</table>

<p>
<security:authentication var="principal" property="principal.username"/>
<c:choose>
	<c:when test="${message.principal == principal}">
		<a href="<%= request.getContextPath() %>/board/messages/editMessage?entityId=${message.entityId}"><input type="button" value="Edit"/></a>
		<a href="<%= request.getContextPath() %>/board/messages/removeMessage?entityId=${message.entityId}"><input type="button" value="Delete"/></a>
	</c:when>
	<c:otherwise>
		<security:authorize access="hasRole('ROLE_ADMIN')">
			<a href="<%= request.getContextPath() %>/board/messages/editMessage?entityId=${message.entityId}"><input type="button" value="Edit"/></a>
			<a href="<%= request.getContextPath() %>/board/messages/removeMessage?entityId=${message.entityId}"><input type="button" value="Delete"/></a>
		</security:authorize>
	</c:otherwise>
</c:choose>
<a href="<%= request.getContextPath() %>/board/messages/messageSummary"><input type="button" value="Message Summary"/></a>
<a href="<c:url value="/board/messages/j_spring_security_logout"/>"><input type="button" value="Logout (<security:authentication property="principal.username"/>)"/></a>
</p>

</div>
</body>

</html>