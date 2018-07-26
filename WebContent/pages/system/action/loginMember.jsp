<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%
 String path = request.getContextPath();
 String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="<%=basePath%>">
<s:head />
<sj:head jqueryui="true" jquerytheme="cupertino" />
<link href="<s:url value ="/pages/template/css/style.css"/>"
	rel="stylesheet" type="text/css" />
<link href="<s:url value ="/pages/template/css/style-table.css"/>"
	rel="stylesheet" type="text/css" />
<title><s:text name="system.name" /></title>
</head>
<body>
	<s:actionerror />
	<s:actionmessage />
	<table>
		<tr>
			<td align="center"><s:form action="MemberLoginAction" method="post" namespace="/">
					<s:textfield key="account" />
					<s:password key="password" />
					<s:submit name="" key="submit.login" align="center" />
				</s:form>
			</td>
		</tr>
	</table>
</body>
</html>