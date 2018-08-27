<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ResourceBundle"%>
<%@ page import="com.ldchotels.system.model.Member"%>
<%@ page import="com.ldchotels.system.model.Role"%>
<%@ page import="com.ldchotels.util.Definition"%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
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
<title><s:text name="memu.system.function.panel" /></title>
</head>
<body>
	<!-- 
	<s:actionerror />
	<s:actionmessage />
	-->
	<sj:accordion id="accordion" heightStyle="content" animate="true" collapsible="true">
		<security:authorize access="hasAnyRole('ROLE_ADMIN')">
			<s:set name="menu.edm.system"><s:text name="menu.edm.system" /></s:set>
			<sj:accordionItem title="%{menu.edm.system}">
				<sj:div><a href="EdmSendAction.input.action"><s:text name="action.edm.send" /></a></sj:div>	
			</sj:accordionItem>
			<s:set name="menu.protel.system"><s:text name="menu.protel.system" /></s:set>
			<sj:accordionItem title="%{menu.protel.system}">
				<sj:div><a href="KundenQueryAction.input.action"><s:text name="action.protel.query.kunden" /></a></sj:div>	
			</sj:accordionItem>
			<s:set name="menu.sf.system"><s:text name="menu.sf.system" /></s:set>
			<sj:accordionItem title="%{menu.sf.system}">
				<sj:div><a href="AccountUpsertAction.input.action"><s:text name="action.sf.account.upsert" /></a></sj:div>
				<!-- <sj:div><a href="ReservationUpsertAction.input.action"><s:text name="action.sf.reservation.upsert" /></a></sj:div> -->
				<sj:div><a href="ReservationUpsertAction.input.action"><s:text name="action.sf.reservation.upsert" /></a></sj:div>	
				<sj:div><a href="ReservationCOUpsertAction.input.action"><s:text name="action.sf.reservationCO.upsert" /></a></sj:div>	
				<sj:div><a href="TransactionUpsertAction.input.action"><s:text name="action.sf.transaction.upsert" /></a></sj:div>
				<sj:div><a href="ReservationUpsertWithAccountAction.input.action"><s:text name="action.sf.reservation.with.account.upsert" /></a></sj:div>	
				<sj:div><a href="TransactionUpsertWithAccountAction.input.action"><s:text name="action.sf.transaction.with.account.upsert" /></a></sj:div>
				<sj:div><a href="AccountDeleteAction.input.action"><s:text name="action.sf.account.delete" /></a></sj:div>	
				<sj:div><a href="ReservationDeleteAction.input.action"><s:text name="action.sf.reservation.delete" /></a></sj:div>	
				<sj:div><a href="TransactionDeleteAction.input.action"><s:text name="action.sf.transaction.delete" /></a></sj:div>	
			</sj:accordionItem>
		</security:authorize>

		<security:authorize access="hasAnyRole('ROLE_ADMIN')">
			<s:set name="menu.system.config"><s:text name="menu.system.config" /></s:set>
			<sj:accordionItem title="%{menu.system.config}">
				<security:authorize access="hasAnyRole('ROLE_ADMIN')">
					<sj:div><a href="CompanyAction.input.action"><s:text name="company.new" /></a></sj:div>
				</security:authorize>	
					<sj:div><a href="CompanyAction.list.action"><s:text name="company.list.all" /></a></sj:div>
				<security:authorize access="hasAnyRole('ROLE_ADMIN')">
					<sj:div><a href="RoleAction.input.action"><s:text name="role.new" /></a></sj:div>
				</security:authorize>	
					<sj:div><a href="RoleAction.list.action"><s:text name="role.list.all" /></a></sj:div>
				<security:authorize access="hasAnyRole('ROLE_ADMIN')">	
					<sj:div><a href="MemberAction.input.action"><s:text name="member.new" /></a></sj:div>
				</security:authorize>
					<sj:div><a href="MemberAction.list.action"><s:text name="member.list.all" /></a></sj:div>
			</sj:accordionItem>
		</security:authorize>
		
		<security:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_NORMAL')">
			<s:set name="menu.system.management"><s:text name="menu.system.management" /></s:set>
			<sj:accordionItem title="%{menu.system.management}">
				<security:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_NORMAL')">
					<sj:div><a href="MemberPasswordChangeAction.input.action"><s:text name="submit.changePassword" /></a></sj:div>
					<sj:div><a href="MemberLogoutAction.action"><s:text name="submit.logout" /></a></sj:div>
				</security:authorize>
			</sj:accordionItem>
		</security:authorize>
	</sj:accordion>	
</body>
</html>


