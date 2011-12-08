<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html
     PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
     "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<title>Welcome to message board</title>
</head>

<body>
<div id="main_wrapper">

<h1>Welcome to message board</h1>

<security:authorize access="isAuthenticated()">
	<p><a href="board/messages/messageSummary">View Message Summary</a></p>
	<p><a href="<c:url value="/board/messages/j_spring_security_logout"/>">Logout</a> (<security:authentication property="principal.username"/>)</p>
</security:authorize>
<security:authorize access="isAnonymous()">
	<p><a href="board/login.htm">Login</a></p>
</security:authorize>

</div>
</body>

</html>