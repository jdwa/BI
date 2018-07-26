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
<title><s:text name="company.detail" /></title>
</head>
<body>
	<h3><s:text name="company.detail" /></h3>
	<s:actionerror />
	<s:actionmessage />
	<s:form action="CompanyAction.update" method="post" namespace="/">
		<s:textfield key="company.id" value="%{company.id}" readonly="true" />
		<s:textfield key="company.cmp_no" value="%{company.cmp_no}" readonly="false" />
		<s:textfield key="company.cmp_description" value="%{company.cmp_description}" />
		<s:textfield key="company.creation_date" value="%{company.creation_date}" readonly="true" />
		<s:textfield key="company.remark" value="%{company.remark}" />
		<s:textfield key="company.timestamp" value="%{company.timestamp}" readonly="true" />
		<s:submit name="" key="submit.update" align="center" />
	</s:form>
</body>
</html>