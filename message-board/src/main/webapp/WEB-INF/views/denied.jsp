<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html 
     PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
     "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>Access Denied</title>
</head>

<body>
<div id="main_wrapper">

<h1>Access Denied</h1>

<p>Sorry, Access Denied</p>
<p><a href="<%= request.getContextPath() %>/index.jsp">Return to Home Page</a> or</p>
<p><a href="../messages/j_spring_security_logout">Logout</a> (<security:authentication property="principal.username"/>)</p>

</div>
</body>

</html>
