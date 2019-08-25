<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><s:message code="menu.mainPage"/></title>
</head>
<body>
	<%@include file="/WEB-INF/incl/menu.app" %>
	<h3 align="center"><c:out value="${message}"></c:out></h3> 
</body>
</html>