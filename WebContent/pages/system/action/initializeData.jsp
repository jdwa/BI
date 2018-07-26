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
<title><s:text name="function.initializeData" /></title>
</head>
<body>
	<h3>
		<s:text name="function.initializeData" />
	</h3>
	<s:actionerror />
	<s:actionmessage />
	<s:form action="DataInitializeAction" method="post" namespace="/">
		<s:textfield key="account" />
		<s:password key="password" />
		<s:submit name="" key="submit.initialze" align="center" />
	</s:form>
</body>
</html>