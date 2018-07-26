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
<title>Protel <s:text name="action.protel.query.kunden" /></title>
</head>
<body>
	<h3>
		Protel <s:text name="action.protel.query.kunden" />
	</h3>
	<s:actionerror />
	<s:actionmessage />
	<center>
		<a href="KundenQueryAction.action"><s:text name="action.protel.query.kunden" /></a>
	</center>
</body>
</html>


