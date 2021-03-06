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
			<!-- TODO: script to automatically fill in profile information -->
				<span class="dbpaneltitle">Edit your Profile.</span>
				<br /><br />
				<form action="UserManager" method="POST">
					<input type="password" name="currentpassword" placeholder="Current Password" required>
					<br />
					<input type="password" name="newpassword" placeholder="New Password">
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
					<input type="text" name="state" value="${state}" placeholder="State" required>
                    <br />
					<input type="text" name="zip" value="${zip}" placeholder="Zip Code" required>
                    <br />
					<input type="text" name="country" value="${country}" placeholder="Country" required>
					<br />
					<input type="text" name="licensenum" value="${licenseNumber}" placeholder="Driver's License #" required>
					<br />
					<input type="text" name="licensestate" value="${licenseState}" placeholder="Driver's License State" required>
					<br /><br />
					<input type="submit" name="updateprofile" value="Submit">
				</form>
			</div>
		</div>
		<script type="text/javascript">
			$(window).load(setWidth());
			$(window).resize(function() {setWidth();});
		</script>
	</body>
</html>