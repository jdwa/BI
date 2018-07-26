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
<meta http-equiv="refresh" content="5;url=<s:url includeParams="all"/>"/>
<%--
<meta http-equiv="refresh" content="5"/>"/>
Above refresh meta will also work as long as browser supports cookie, 
by including params above we are making sure that it will work even when cookies are disabled
--%>
<base href="<%=basePath%>">
<s:head />
<sj:head jqueryui="true" jquerytheme="cupertino" />
<title><s:text name="action.system.waiting" /></title>
</head>
<body>
	<h3>
		<s:text name="action.system.waiting" />
	</h3>
	<s:actionerror />
	<s:actionmessage />
	<center>
		<s:text name="action.system.waiting" />
	</center>
</body>
</html>