<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:message code="menu.users" /></title>
<script type="text/javascript">
	function changeTrBG(row) {
		row.style.backgroundColor = "lightgray";
	}

	function defaultTrBg(row) {
		row.style.background = "white";
	}

	function startSerach(pParam) {
		var searchWord = document.getElementById('searchString').value;
		var page = parseInt(document.getElementById('cp').value)
				+ parseInt(pParam);
		if (searchWord.length < 3) {
			document.getElementById("errorSearch").innerHTML = "<s:message code="error.searchString.toShort"/>";
			return false;
		} else {
			document.getElementById("errorSearch").innerHTML = "";
			var searchLink = '${pageContext.request.contextPath}/admin/adminUsers/search/'
					+ searchWord + '/' + page;
			window.location.href = searchLink;
		}
	}
</script>
<style type="text/css">
#leftA {
	align: left;
}
</style>
</head>
<body>
	<%@include file="/WEB-INF/incl/menu.app"%>
	<h2>
		<s:message code="menu.users" />
	</h2>
	<c:set var="count" value="${recordStartCounter}" />
	<div align="center">
		<div align="right" style="width: 1000px; padding: 2px;">
			<input type="hidden" name="cp" id="cp" value="${currentPage}" /> <input
				type="text" id="searchString" />&nbsp;&nbsp;<input type="button"
				value="<s:message code="button.search"/>" onclick="startSerach(0);" /><br />
			<span id="errorSearch" style="color: red;"></span>
		</div>
		<table width="1000" border="0" cellpadding="6" cellspacing="2">
			<tr bgcolor="#ffddcc">
				<td width="40" align="center"></td>
				<td width="75" align="center"><b><s:message
							code="admin.user.id" /></b></td>
				<td width="190" align="center"><b><s:message
							code="register.name" /></b></td>
				<td width="190" align="center"><b><s:message
							code="register.lastName" /></b></td>
				<td width="200" align="center"><b><s:message
							code="register.email" /></b></td>
				<td width="100" align="center"><b><s:message
							code="profil.czyAktywny" /></b></td>
				<td width="190" align="center"><b><s:message
							code="profil.rola" /></b></td>
				<td width="50"></td>
			</tr>

			<c:forEach var="u" items="${userList}">
				<c:set var="count" value="${count + 1}" />
				<tr onmouseover="changeTrBG(this)" onmouseout="defaultTrBg(this)">
					<td width="40" align="center"><c:out value="${count}"></c:out></td>
					<td width="75" align="center"><a href="edit/${u.id }"><c:out
								value="${u.id}" /></a></td>
					<td width="190" align="center"><a href="edit/${u.id }"><c:out
								value="${u.name}" /></a></td>
					<td width="190" align="center"><a href="edit/${u.id }"><c:out
								value="${u.lastName}" /></a></td>
					<td width="190" align="center"><a href="edit/${u.id }"><c:out
								value="${u.email}" /></a></td>
					<td width="190" align="center"><c:choose>
							<c:when test="${u.active == 1 }">
								<font color="green"><s:message code="word.tak"></s:message></font>
							</c:when>
							<c:otherwise>
								<font color="red"><s:message code="word.nie"></s:message></font>
							</c:otherwise>
						</c:choose></td>
					<td width="190" align="center"><c:choose>
							<c:when test="${u.nrRoli ==1}">
								<font color="gray"><b><s:message code="word.admin"></s:message></b></font>
							</c:when>
							<c:otherwise>
								<font><s:message code="word.user"></s:message></font>
							</c:otherwise>
						</c:choose></td>

					<td width="50" align="center"><c:choose>
							<c:when test="${u.nrRoli == 1}">
								<img alt="Inac" src="/resources/images/CantDelete18x16.png"
									width="18" height="16">
							</c:when>
							<c:otherwise>
								<a href="delete/${u.id}"> <img alt="Activ"
									src="/resources/images/Delete18x16.png"
									title="<s:message code = "delete.user"></s:message>" />
								</a>
							</c:otherwise>
						</c:choose></td>
				</tr>
			</c:forEach>
		</table>
		<table width="1000" border="0" cellpadding="6" cellspacing="2">
			<tr bgcolor="#ffddcc">
				<td width="115" align="left"><s:message
						code="admin.menuUsers.PageInfo" /> <c:out value="${currentPage}" />
					<s:message code="admin.menuUsers.PageFrom" /> <c:out
						value="${totalPages}" /></td>
				<td align="right"><c:if test="${currentPage > 1}">
						<input type="button" align="left"
							onclick="window.location.href='${pageContext.request.contextPath}/admin/adminUsers/${currentPage - 1}'"
							value="<s:message code = "button.previousPage"></s:message>" />
					</c:if> <c:if test="${currentPage < totalPages }">
						<input type="button" align="right"
							onclick="window.location.href='${pageContext.request.contextPath}/admin/adminUsers/${currentPage + 1}'"
							value="<s:message code = "button.nextPage"></s:message>" />
					</c:if></td>
			</tr>
		</table>
	</div>
</body>
</html>