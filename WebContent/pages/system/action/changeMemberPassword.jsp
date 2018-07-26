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
<title><s:text name="function.changePassword" /></title>
</head>
<body>
	<h3>
		<s:text name="function.changePassword" />
	</h3>
	<s:actionerror />
	<s:actionmessage />
	<s:form action="MemberPasswordChangeAction" method="post" namespace="/">
		<s:password key="orgPassword" />
		<s:password key="newPassword" />
		<s:password key="cfmPassword" />
		<s:submit name="" key="submit.changePassword" align="center" />
	</s:form>
</body>
</html>


