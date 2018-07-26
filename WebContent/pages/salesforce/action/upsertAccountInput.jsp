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
<title>Salesforce <s:text name="action.sf.account.upsert" /></title>
</head>
<body>
	<h3>
		Salesforce <s:text name="action.sf.account.upsert" />
	</h3>
	<s:actionerror />
	<s:actionmessage />
	<center>
		<s:form action="AccountUpsertAction" method="post" namespace="/">
			<s:checkbox name="fileDelete" value="true" key="fileDelete"/>
			<s:checkbox name="serial" value="false" key="serial"/>
			<sj:datepicker name="chgBegin" value="%{new java.util.Date()}" 
				displayFormat="yy-mm-dd" disabled="false" showOn="focus" key="action.sf.account.upsert.chgBegin"/>
			<sj:datepicker name="chgEnd" value="%{new java.util.Date()}" 
				displayFormat="yy-mm-dd" disabled="false" showOn="focus" key="action.sf.account.upsert.chgEnd"/>	
			<s:submit name="" key="action.sf.account.upsert" align="center" />
		</s:form>
	</center>
</body>
</html>


