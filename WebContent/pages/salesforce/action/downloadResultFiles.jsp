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
<title><s:text name="action.sf.result.files.download" /></title>
</head>
<body>
	<h3>
		<s:text name="action.sf.result.files.download" />
	</h3>
	<s:actionerror />
	<s:actionmessage />
	<center>
		<a href="ResultAccountsDownloadAction.action"><s:text name="action.sf.result.accounts.download" /></a>
		<br>
		<a href="ResultReservationsDownloadAction.action"><s:text name="action.sf.result.reservations.download" /></a>
		<br>
		<a href="ResultReservationCOsDownloadAction.action"><s:text name="action.sf.result.reservationCOs.download" /></a>
		<br>
		<a href="ResultTransactionsDownloadAction.action"><s:text name="action.sf.result.transactions.download" /></a>
	</center>
</body>
</html>