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
<title><s:text name="member.add" /></title>
</head>
<body>
	<h3><s:text name="member.add" /></h3>
	<s:actionerror />
	<s:actionmessage />
	<s:form action="MemberAction.add" method="post" namespace="/">
		<s:checkbox key="member.active" value="true"/>
		<s:textfield key="member.account" />
		<s:password key="member.password" />
		<s:select key="member.company.cmp_no" list="companyList" listKey="cmp_no" listValue="cmp_description" />
		<s:select key="member.role.role_code" list="roleList" listKey="role_code" listValue="role_description" />
		<s:textfield key="member.remark" />
		<sj:datepicker key="member.creation_date" value="%{new java.util.Date()}" 
			displayFormat="yy-mm-dd" disabled="true" showOn="focus"
			timepicker="true" timepickerShowSecond="true" timepickerFormat="HH:mm:ss" />
		<s:submit value="%{getText('submit.add')}" align="center" />
	</s:form>
	<center><br><a href="MemberAction.list"><s:text name="member.list.all" /></a></center>	
</body>
</html>


