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
	<title>User Summary</title>
</head>

<body>
<div id="main_wrapper">

<h1>User Summary</h1>

<ul>
	<c:forEach items="${users}" var="user">
		<li><a href="userDetails?username=${user.username}">${user.username} (<fmt:formatDate value="${user.timestamp}" type="both"/>)</a></li>
	</c:forEach>
</ul>

<a href="<%= request.getContextPath() %>/board/messages/messageSummary"><input type="button" value="Message Summary"/></a>
<a href="<c:url value="/board/users/j_spring_security_logout"/>"><input type="button" value="Logout (<security:authentication property="principal.username"/>)"/></a>

</div>
</body>

</html>