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
			<!-- TODO: script to automatically fill in payment information -->
				<span class="dbpaneltitle">Edit your Payment Information.</span>
				<br /><br />
				<form action="UserManager" method="POST">
					<input type="password" name="currentpassword" placeholder="Current Password" required>
					<br />
					<input type="text" name="cardnumber" placeholder="Card Number" required>
					<br />
					<input type="text" name="cardverification" placeholder="Card Security Code" required>
					<br />
					<input type="text" name="firstname" placeholder="First Name" required>
					<br />
					<input type="text" name="lastname" placeholder="Last Name" required>
					<br />
					<input type="text" name="addressline1" placeholder="Billing Address Line 1" required>
					<br />
					<input type="text" name="addressline2" placeholder="Billing Address Line 2">
					<br />
					<input type="text" name="city" placeholder="Billing City" required>
					<br />
					<input type="text" name="zip" placeholder="Billing Zip Code" required>
					<br />
					<input type="text" name="state" placeholder="Billing State" required
					><br />
					<input type="text" name="country" placeholder="Billing Country" required>
					<br /><br />
					<input type="submit" name="updatepayment" value="Submit">
				</form>
			</div>
		</div>
		<script type="text/javascript">
			$(window).load(setWidth());
			$(window).resize(function() {setWidth();});
		</script>
	</body>
</html>