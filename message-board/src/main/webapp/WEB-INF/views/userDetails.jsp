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
	<title>User Details</title>
</head>

<body>
<div id="main_wrapper">

<h1>User Details</h1>

<table>
	<tr><td>
		<table>
			<tr>
				<td>Username:</td>
				<td>${user.username}</td>
			</tr>
			<tr>
				<td>Date:</td>
				<td><fmt:formatDate value="${user.timestamp}" type="both"/></td>
			</tr>
		</table>
	</td></tr>
</table>

<p>

<a href="<%= request.getContextPath() %>"><input type="button" value="Home page"/></a>

<security:authentication var="username" property="principal.username"/>
<security:authorize access="isAuthenticated()">
	<c:choose>
		<c:when test="${user.username == username}">
			<security:authorize access="hasRole('ROLE_MEMBER')">
				<a href="<%= request.getContextPath() %>/board/users/editUser?name=${user.username}"><input type="button" value="Edit"/></a>
			</security:authorize>
			<security:authorize access="hasRole('ROLE_ADMIN')">
				<a href="<%= request.getContextPath() %>/board/users/removeUser?username=${user.username}"><input type="button" value="Delete"/></a>
				<a href="<%= request.getContextPath() %>/board/users/userSummary"><input type="button" value="User Summary"/></a>
			</security:authorize>
		</c:when>
		<c:otherwise>
			<security:authorize access="hasRole('ROLE_ADMIN')">
				<a href="<%= request.getContextPath() %>/board/users/editUser?name=${user.username}"><input type="button" value="Edit"/></a>
				<a href="<%= request.getContextPath() %>/board/users/removeUser?username=${user.username}"><input type="button" value="Delete"/></a>
				<a href="<%= request.getContextPath() %>/board/users/userSummary"><input type="button" value="User Summary"/></a>
			</security:authorize>
		</c:otherwise>
	</c:choose>
</security:authorize>
<%-- <a href="<%= request.getContextPath() %>/board/messages/messageSummary"><input type="button" value="Message Summary"/></a> --%>
<a href="<c:url value="/board/messages/j_spring_security_logout"/>"><input type="button" value="Logout (<security:authentication property="principal.username"/>)"/></a>
</p>

</div>
</body>

</html>