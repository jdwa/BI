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
<title><s:text name="member.detail" /></title>
</head>
<body>
	<h3><s:text name="member.detail" /></h3>
	<s:actionerror />
	<s:actionmessage />
	<s:form action="MemberAction.update" method="post" namespace="/">
		<s:checkbox key="member.active" readonly="false" />
		<s:textfield key="member.id" value="%{member.id}" readonly="true" />
		<s:textfield key="member.account" value="%{member.account}" readonly="true" />
		<s:textfield key="member.password" value="%{member.password}" />
		<s:select key="member.company.cmp_no" list="companyList" listKey="cmp_no" listValue="cmp_description" />
		<s:select key="member.role.role_code" list="roleList" listKey="role_code" listValue="role_description" />
		<s:textfield key="member.creation_date" value="%{member.creation_date}" readonly="true" />
		<s:textfield key="member.remark" value="%{member.remark}" />
		<s:textfield key="member.timestamp" value="%{member.timestamp}" readonly="true" />
		<s:submit name="" key="submit.update" align="center" />
	</s:form>
</body>
</html>