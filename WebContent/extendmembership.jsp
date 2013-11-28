<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<!DOCTYPE HTML>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" type="text/css" href="resources/css/layout.css" />
		<link rel="stylesheet" type="text/css" href="resources/css/accordion.css" />
		<script src="resources/jquery/js/jquery-1.9.1.js"></script>
		<script src="resources/js/format.js"></script>
		<title>YouDrive Dashboard</title>
	</head>
	<body>
		<div id="mainbody">
			<%@ include file="resources/html/header.html" %>
			<%@ include file="resources/html/leftnav.html" %>
			<div id="dashboardpanel">
			<!-- TODO: script to automatically calculate cost based on aquired price per month values -->
				<span class="dbpaneltitle">Extend your Membership.</span>
				<br /><br />
				Here you can extend your membership for YouDrive.
				<br /><br />
				Just enter the number of months to renew by and we'll handle the rest.
				<br /><br />
				<form action="MembershipManager" method="POST">
					Renew your membership for
					<input type="number" name="months" value="0">
					, and then you pay $<span id="ppm">${membershipMonthlyPrice}</span> per month.
					<br /><br />
					That comes to a total of $<span id="ptotal">X</span>.
					<br /><br />
					Is this okay?
					<br /><br />
					<input type="submit" name="extend" style="float:left;" value="Yes"> <input type="submit" style="float:right;" value="No" formaction="dashboard.jsp">
				</form>
			</div>
		</div>
		<script type="text/javascript">
			$(window).load(setWidth());
			$(window).resize(function() {setWidth();});
		</script>
	</body>
</html>