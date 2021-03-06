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
			<%@ include file="resources/html/leftnav_admin.html" %>
			<div id="dashboardpanel">
				<span class="dbpaneltitle">Manage ${userName}.</span>
				<br /><br />
				<form action="UserManager" method="POST">
					<input type="hidden" name="userid" value="${userID}">
					<input type="hidden" name="username" value="${userName}">
					<br />
					<input type="text" name="firstname" value="${firstName}" placeholder="First Name" required>
					<br />
					<input type="text" name="lastname" value="${lastName}" placeholder="Last Name" required>
					<br />
					<input type="text" name="email" value="${emailAddress}" placeholder="Email Address" required>
					<br />
					<input type="text" name="addressline1" value="${addrLine1}" placeholder="Address Line 1" required>
					<br />
					<input type="text" name="addressline2" value="${addrLine2}" placeholder="Address Line 2">
					<br />
					<input type="text" name="city" value="${city}" placeholder="City" required>
					<br />
					<input type="text" name="zip" value="${zip}" placeholder="Zip Code" required>
					<br />
					<input type="text" name="state" value="${state}" placeholder="State" required
					><br />
					<input type="text" name="country" value="${country}" placeholder="Country" required>
					<br />
					<input type="text" name="licensenum" value="${licenseNumber}" placeholder="Driver's License #" required>
					<br />
					<input type="text" name="licensestate" value="${licenseState}" placeholder="Driver's License State" required>
					<br />
					<input type="checkbox" name="revokemembership" value="yes">Revoke user's membership?
					<br />
					<input type="checkbox" name="removeprofile" value="yes">Remove Profile from system?
					<br /><br />
					<input type="submit" name="updateProfile" value="Submit">
				</form>
			</div>
		</div>
		<script type="text/javascript">
			$(window).load(setWidth());
			$(window).resize(function() {setWidth();});
		</script>
	</body>
</html>