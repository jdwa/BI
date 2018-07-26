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
<title>Salesforce <s:text name="action.sf.transaction.delete" /></title>
</head>
<body>
	<h3>
		Salesforce <s:text name="action.sf.transaction.delete" />
	</h3>
	<s:actionerror />
	<s:actionmessage />
	<center>
		<s:form action="TransactionDeleteAction" method="post" namespace="/">
			<s:checkbox name="fileDelete" value="true" key="fileDelete"/>
			<s:checkbox name="serial" value="false" key="serial"/>
			<sj:datepicker name="delDepBegin" value="%{new java.util.Date()}" 
				displayFormat="yy-mm-dd" disabled="false" showOn="focus" key="action.sf.transaction.delete.depBegin"/>
			<sj:datepicker name="delDepEnd" value="%{new java.util.Date()}" 
				displayFormat="yy-mm-dd" disabled="false" showOn="focus" key="action.sf.transaction.delete.depEnd"/>	
			<s:submit name="" key="action.sf.transaction.delete" align="center" />
		</s:form>
	</center>
</body>
</html>


