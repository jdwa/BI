<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<SCRIPT type="text/javascript">
	function disableBackButton() {
		window.history.forward();
	}
	setTimeout("disableBackButton()", 0);
</SCRIPT>
<title><s:text name="action.system.error" /></title>
</head>
<body style="background-color: #FFFFFF">
	<h4>
		<s:text name="action.system.error" />
	</h4>
	<h4>
		<s:text name="action.system.error.contact.information" />
	</h4>
	<img src="<s:url value="images/failure.jpg"/>" height="570" width="380" />
	<s:actionerror />
	<s:actionmessage />
	<p>
		<a href="pages/index.jsp"><img
			src="<s:url value="images/home.png"/>" height="48" width="48" /></a>
		<%
		if (session.getAttribute("CurrentMember") != null) {
	%>
		<a href="<s:url action='loginMember.action'/>"><img
			src="<s:url value="images/log_in.png"/>" height="48" width="48" /></a> <a
			href="<s:url action='logoutMember.action'/>"><img
			src="<s:url value="images/log_out.png"/>" height="48" width="48" /></a>
		<% 
		}
	%>
	
</body>
</html>
