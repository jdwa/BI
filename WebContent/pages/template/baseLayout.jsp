<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%
 String path = request.getContextPath();
 String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

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
<title><tiles:insertAttribute name="title" ignore="true" /></title>
</head>
<body>
	<table>
		<tr>
			<td colspan="2" align="center"><tiles:insertAttribute
					name="header" /></td>
		</tr>
		<tr>
			<td width="240" valign="top" align="center"><tiles:insertAttribute
					name="menu" /></td>
			<td width="800" valign="top" align="center"><tiles:insertAttribute
					name="body" /></td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<h6>
					<tiles:insertAttribute name="footer" />
				</h6>
			</td>
		</tr>
	</table>
</body>
</html>