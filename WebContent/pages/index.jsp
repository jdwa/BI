<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%
 String path = request.getContextPath();
 String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="<%=basePath%>">
<s:head />
<sj:head jqueryui="true" jquerytheme="cupertino" />
<link href="<s:url value ="/pages/template/css/style.css"/>"
	rel="stylesheet" type="text/css" />
<title><s:text name="system.name" /></title>
</head>
<body>
	<table border="0" cellpadding="0" cellspacing="0" align="center">
		<tr align="center">
			<td height="60" valign="middle" align="center">
				<h2>
					<s:text name="system.name" />
				</h2>
			</td>
		</tr>
		<tr align="center">
			<td height="60" width="400" valign="middle" align="center">
				<h3>
					<a href="MemberLoginAction.input.action"><s:text name="member.login" /></a>
					<!--
					<a href="pages/system/action/loginMember.jsp"><s:text name="member.login" /></a>
					-->
				</h3>
			</td>
		</tr>
		<tr align="center">
			<td height="30" align="center">Copyright &copy; L'Hotel de Chine Group</td>
		</tr>
	</table>
</body>
</html>