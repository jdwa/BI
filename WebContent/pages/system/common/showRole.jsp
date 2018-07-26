<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
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
	<table id="role">
		<tr>
			<th><s:text name="role.id" /></th>
			<th><s:text name="role.role_code" /></th>
			<th><s:text name="role.role_description" /></th>
			<th><s:text name="role.creation_date" /></th>
			<th><s:text name="role.remark" /></th>
			<th><s:text name="role.timestamp" /></th>
			<security:authorize access="hasAnyRole('ROLE_ADMIN')">
				<th><s:text name="submit.delete" /></th>
			</security:authorize>	
			<th><s:text name="submit.detail" /></th>
		</tr>

		<s:iterator value="roleList">
			<tr>
				<td><s:property value="id" /></td>
				<td><s:property value="role_code" /></td>
				<td><s:property value="role_description" /></td>
				<td><s:date name="creation_date" format="yyyy-MM-dd HH:mm:ss" /></td>
				<td><s:property value="remark" /></td>
				<td><s:property value="timestamp" /></td>
				<security:authorize access="hasAnyRole('ROLE_ADMIN')">
					<td><a href="RoleAction.delete.action?id=<s:property value="id" />"><s:text name="submit.delete" /></a></td>
				</security:authorize>	
				<td><a href="RoleAction.detail.action?id=<s:property value="id" />"><s:text name="submit.detail" /></a></td>
			</tr>
		</s:iterator>
	</table>
</body>
</html>