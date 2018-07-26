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
<sj:head jqueryui="true" jquerytheme="cupertino"/>
<title><s:text name="role.detail" /></title>
</head>
<body>
	<h3><s:text name="role.detail" /></h3>
	<s:actionerror />
	<s:actionmessage />
	<s:form action="RoleAction.update" method="post" namespace="/">
		<s:textfield key="role.id" value="%{role.id}" readonly="true" />
		<s:textfield key="role.role_code" value="%{role.role_code}" readonly="true" />
		<s:textfield key="role.role_description" value="%{role.role_description}" />
		<s:textfield key="role.creation_date" value="%{role.creation_date}" readonly="true" />
		<s:textfield key="role.remark" value="%{role.remark}" />
		<s:textfield key="role.timestamp" value="%{role.timestamp}" readonly="true" />
		<s:submit name="" key="submit.update" align="center" />
	</s:form>
</body>
</html>