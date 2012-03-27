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
	<title>Message Summary</title>
</head>

<body>
<div id="main_wrapper">

<h1>Message Summary</h1>

<p>

<a href="<%= request.getContextPath() %>"><input type="button" value="Home page"/></a>

<security:authorize access="hasRole('ROLE_MEMBER')">
	<a href="<%= request.getContextPath() %>/board/messages/createMessage"><input type="button" value="Create Message"/></a>
	<%-- <a href="<%= request.getContextPath() %>/board/users/userDetails?username=<security:authentication property="principal.username"/>"><input type="button" value="User Details"/></a> --%>
</security:authorize>

<%-- <security:authorize access="hasRole('ROLE_ADMIN')">
	<a href="<%= request.getContextPath() %>/board/users/userSummary"><input type="button" value="User Summary"/></a>
</security:authorize> --%>

<a href="<c:url value="/board/messages/j_spring_security_logout"/>"><input type="button" value="Logout (<security:authentication property="principal.username"/>)"/></a>
</p>

<ul>
	<c:forEach items="${messages}" var="message">
		<li><a href="messageDetails?entityId=${message.entityId}">${message.title} (<fmt:formatDate value="${message.timestamp}" type="both"/>) --- ${message.principal}</a></li>
	</c:forEach>
</ul>

</div>
</body>

</html>