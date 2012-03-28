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

<h1>Message Details</h1>

Message was successfully published to a queue!
<br/>
<br/>

<a href="<%= request.getContextPath() %>"><input type="button" value="Home page"/></a>
<security:authorize access="isAuthenticated()">
	<a href="<c:url value="/board/users/j_spring_security_logout"/>"><input type="button" value="Logout (<security:authentication property="principal.username"/>)"/></a>
</security:authorize>

</div>
</body>

</html>
