<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
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
<title><s:text name="protel.kunden.detail" /></title>
</head>
<body>
	<h3>
		<s:text name="protel.kunden.detail" />
	</h3>
	<s:actionerror />
	<s:actionmessage />
	<table id="kunden">
		<tr>
			<th><s:text name="protel.kunden.kdnr" /></th>
			<th><s:text name="protel.kunden.name1" /></th>
			<th><s:text name="protel.kunden.name2" /></th>
		</tr>

		<s:iterator value="kundenList">
			<tr>
				<td><s:property value="kdnr" /></td>
				<td><s:property value="LastName" /></td>
				<td><s:property value="vorname" /></td>
			</tr>
		</s:iterator>
	</table>
</body>
</html>